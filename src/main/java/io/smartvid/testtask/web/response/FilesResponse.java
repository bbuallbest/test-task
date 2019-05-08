package io.smartvid.testtask.web.response;

import java.util.Collections;
import java.util.List;

public class FilesResponse {
    public final String path;
    public final List<String> files;

    public FilesResponse(String path, List<String> files) {
        this.path = path;
        this.files = Collections.unmodifiableList(files);
    }
}
