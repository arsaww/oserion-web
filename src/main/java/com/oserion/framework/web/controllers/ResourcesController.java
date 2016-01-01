package com.oserion.framework.web.controllers;

import com.oserion.framework.web.beans.json.Directory;
import com.oserion.framework.web.beans.json.FileProperties;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.beans.json.ResponseMessage;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oserion/resources")
public class ResourcesController extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(ResourcesController.class.getName());
    private static final String RESSOURCES_PATH_PREFIX = "/WEB-INF";

    @RequestMapping(value="/upload", method = RequestMethod.POST)
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
            return new ResponseMessage("OK","/resources/"+filePath+file.getOriginalFilename());

        }catch (Exception e){
            throw new InternalErrorException();
        }

    }

    @RequestMapping(value="/rename", method = RequestMethod.POST)
    public Object rename(@RequestBody FileProperties fp,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            if(!fp.getPath().startsWith("/resources")) throw  new InternalErrorException();
            String path = getContext(req).getRealPath(RESSOURCES_PATH_PREFIX +fp.getPath());
            File f = new File(path+"/"+fp.getOrigin());
            f.renameTo(new File(path+"/"+fp.getValue()));
            return new ResponseMessage("OK","'"+fp.getOrigin()+"' successfully renamed to '"+fp.getValue()+"'");
        }catch (Exception e){
            throw new InternalErrorException();
        }
    }

    @RequestMapping(value="/mkdir", method = RequestMethod.POST)
    public Object mkdir(@RequestBody FileProperties fp,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            if(!fp.getPath().startsWith("/resources")) throw  new InternalErrorException();
            String path = getContext(req).getRealPath(RESSOURCES_PATH_PREFIX +fp.getPath());
            File f = new File(path+"/"+fp.getValue());
            f.mkdir();
            return new ResponseMessage("OK","directory '"+fp.getValue()+"' successfully created");
        }catch (Exception e){
            throw new InternalErrorException();
        }

    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public Object delete(@RequestBody FileProperties fp,
                         HttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            if(!fp.getPath().startsWith("/resources")) throw  new InternalErrorException();
            String path = getContext(req).getRealPath(RESSOURCES_PATH_PREFIX +fp.getPath());
            File f = new File(path+"/"+fp.getValue());
            if(f.isDirectory())
                FileUtils.deleteDirectory(f);
            else
                f.delete();
            return new ResponseMessage("OK","'"+fp.getValue()+"' successfully deleted");

        }catch (Exception e){
            throw new InternalErrorException();
        }

    }

    @RequestMapping(value="/**", method = RequestMethod.GET)
    public Object getRessource(HttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try{
            String[] splittedUrl = req.getRequestURL().toString().split("/oserion/resources");
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