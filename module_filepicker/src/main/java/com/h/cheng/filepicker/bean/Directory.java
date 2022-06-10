package com.h.cheng.filepicker.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2020/8/21-15:15
 * @desc
 */
public class Directory<T> {
    private String id;
    private String name;
    private String path;
    private List<T> files = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Directory)) {
            return false;
        }
        Directory directory = (Directory) o;
        return this.path.equals(directory.path);
    }

    public String getId() {
        return id;
    }

    public void addFile(T file) {
        files.add(file);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<T> getFiles() {
        return files;
    }

    public void setFiles(List<T> files) {
        this.files = files;
    }
}
