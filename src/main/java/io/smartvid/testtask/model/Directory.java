package io.smartvid.testtask.model;

public class Directory {
    public final String path;
    public final int filesCount;

    public Directory(String path, int filesCount) {
        this.path = path;
        this.filesCount = filesCount;
    }
}
