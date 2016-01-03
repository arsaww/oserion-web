package com.oserion.framework.web.beans.json;

import java.util.ArrayList;
import java.util.List;

public class JsonDirectory {

    private String path;
    private String name;
    private List<JsonDirectory> directories = new ArrayList();
    private List<String> files = new ArrayList();

    public JsonDirectory(String path){
        this.path = path;
        String[] subDirectories = path.split("/");
        this.name = subDirectories[subDirectories.length-1];
    }

    public List<JsonDirectory> getDirectories() {
        return directories;
    }

    public void addDirectory(JsonDirectory d) {
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
