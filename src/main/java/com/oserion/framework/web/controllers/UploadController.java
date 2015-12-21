package com.oserion.framework.web.controllers;

import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.web.beans.Directory;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.util.JsonResponseMessage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oserion/upload")
public class UploadController extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(UploadController.class.getName());
    private static final String RESSOURCES_PATH_PREFIX = "/WEB-INF";

    @RequestMapping(value="/resources", method = RequestMethod.POST)
    public Object upload(@RequestParam("file") MultipartFile file,
                       @RequestParam("path") String filePath,
                       MultipartHttpServletRequest req,
                       HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        byte[] bytes;
        try {
            if(!filePath.startsWith("/resources")) throw new InternalErrorException();
            String path = getContext(req).getRealPath(RESSOURCES_PATH_PREFIX +filePath+"/"+file.getOriginalFilename());
            Path pathToFile = Paths.get(path);
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            if (!file.isEmpty()) {
                bytes = file.getBytes();
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(bytes);
                fos.close();
            }
            LOG.log(Level.INFO, String.format("receive %s from %s", file.getOriginalFilename(), path));
            return new JsonResponseMessage("OK","/resources/"+filePath+file.getOriginalFilename());

        }catch (Exception e){
            throw new InternalErrorException();
        }

    }


    @RequestMapping(value="/templates", method = RequestMethod.POST)
    public Object addTemplate(@RequestParam("file") MultipartFile file,
                         MultipartHttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);

        try {
            ByteArrayInputStream stream = new   ByteArrayInputStream(file.getBytes());
            String strTemplate = IOUtils.toString(stream, "UTF-8");
            new Api418Facade().uploadTemplateFromHtml(strTemplate,
                    FilenameUtils.removeExtension(file.getOriginalFilename()));
            LOG.log(Level.INFO, file.getOriginalFilename() + "was successfully inserted as a template");
            return new JsonResponseMessage("OK",file.getOriginalFilename() + "was successfully inserted as a template");

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @RequestMapping(value="/resources", method = RequestMethod.PUT)
    public Object rename(@RequestParam("value") String fileName,
                         @RequestParam("origin") String origin,
                         @RequestParam("path") String filePath,
                         MultipartHttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            if(!filePath.startsWith("/resources")) throw new InternalErrorException();
            String path = getContext(req).getRealPath(RESSOURCES_PATH_PREFIX +filePath+"/");
            File f = new File(path+origin);
            f.renameTo(new File(path+fileName));
            return new JsonResponseMessage("OK",origin+" => "+fileName+" updated");

        }catch (Exception e){
            throw new InternalErrorException();
        }

    }

    @RequestMapping(value="/resources/**", method = RequestMethod.GET)
    public Object upload(HttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try{
            String[] splittedUrl = req.getRequestURL().toString().split("/oserion/upload/resources");
            String endOfPath = splittedUrl.length > 1 ? splittedUrl[1] : "";
            endOfPath = "/resources"+endOfPath;
            Directory d = new Directory(endOfPath);
            try {
                DirectoryStream<Path> paths = Files.newDirectoryStream(
                        Paths.get(getContext(req).getRealPath(RESSOURCES_PATH_PREFIX + endOfPath)));

                for(Path p : paths){
                    if(Files.isDirectory(p)){
                        d.addDirectory(new Directory(endOfPath + "/" + p.getFileName().toString()));
                    }else if(Files.isRegularFile(p)){
                        d.addFile(p.getFileName().toString());
                    }
                }
                return d;

            }catch (NoSuchFileException e){
                return d;
            }
        }catch (Exception e){
            throw new InternalErrorException();
        }
    }




}