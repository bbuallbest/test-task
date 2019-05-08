package io.smartvid.testtask.web.controller;

import io.smartvid.testtask.model.Directory;
import io.smartvid.testtask.web.response.FilesResponse;
import io.smartvid.testtask.web.response.SubDirectoriesResponse;
import io.smartvid.testtask.service.FileSystemExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "dir")
public class DirectoryController {

    private FileSystemExplorer explorer;

    @Autowired
    public DirectoryController(FileSystemExplorer explorer) {
        this.explorer = explorer;
    }

    @GetMapping(value = "/{dir}/sub")
    public SubDirectoriesResponse getSubDirectories(@PathVariable String dir) {
        Directory directory = explorer.searchDirectoryByName(dir);
        List<Directory> subDirectories = explorer.getSubDirectories(directory);
        return new SubDirectoriesResponse(directory, subDirectories);
    }

    @GetMapping(value = "/files")
    public FilesResponse getFiles(@RequestParam String path) throws UnsupportedEncodingException {
        path = decode(path);
        List<String> files = explorer.getFileNamesByPath(path);
        return new FilesResponse(path, files);
    }

    private String decode(String path) {
        try {
            return URLDecoder.decode(path, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
