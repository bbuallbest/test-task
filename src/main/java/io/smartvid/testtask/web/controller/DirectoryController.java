package io.smartvid.testtask.web.controller;

import io.smartvid.testtask.model.Directory;
import io.smartvid.testtask.web.response.FilesResponse;
import io.smartvid.testtask.web.response.SubDirectoriesResponse;
import io.smartvid.testtask.service.FileSystemExplorer;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

@RestController
public class DirectoryController {

    private FileSystemExplorer explorer;

    @Autowired
    public DirectoryController(FileSystemExplorer explorer) {
        this.explorer = explorer;
    }

    @GetMapping(value = "/dir/subdirectories")
    public SubDirectoriesResponse getSubDirectories(@ApiParam(value = "Name of the directory") @RequestParam String dir) {
        dir = decode(dir);
        Directory directory = explorer.searchDirectoryByName(dir);
        List<Directory> subDirectories = explorer.getSubDirectories(directory);
        return new SubDirectoriesResponse(directory, subDirectories);
    }

    @GetMapping(value = "/dir/files")
    public FilesResponse getFiles(@ApiParam(value = "Path to the directory") @RequestParam String path) {
        path = decode(path);
        List<String> files = explorer.getFileNamesByPath(path);
        return new FilesResponse(path, files);
    }

    @GetMapping(value = "/file/attributes")
    public BasicFileAttributes getFileAttributes(@ApiParam(value = "Path to the file") @RequestParam String path) {
        path = decode(path);
        return explorer.getFileAttributes(path);
    }

    private String decode(String path) {
        try {
            return URLDecoder.decode(path, StandardCharsets.UTF_8.displayName());
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
