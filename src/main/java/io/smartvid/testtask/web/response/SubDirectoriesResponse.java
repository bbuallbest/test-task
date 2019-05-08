package io.smartvid.testtask.web.response;

import io.smartvid.testtask.model.Directory;

import java.util.Collections;
import java.util.List;

public class SubDirectoriesResponse {
    public final Directory directory;
    public final List<Directory> subDirectories;

    public SubDirectoriesResponse(Directory directory, List<Directory> subDirectories) {
        this.directory = directory;
        this.subDirectories = Collections.unmodifiableList(subDirectories);
    }
}
