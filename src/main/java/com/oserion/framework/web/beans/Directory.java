package com.oserion.framework.web.beans;

import java.util.ArrayList;
import java.util.List;

public class Directory {

    private String path;
    private String name;
    private List<Directory> directories = new ArrayList();
    private List<String> files = new ArrayList();

    public Directory(String path){
        this.path = path;
        String[] subDirectories = path.split("/");
        this.name = subDirectories[subDirectories.length-1];
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void addDirectory(Directory d) {
        this.directories.add(d);
    }

    public List<String> getFiles() {
        return files;
    }

    public void addFile(String f) {
        this.files.add(f);
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
