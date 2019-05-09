package io.smartvid.testtask.service;

import io.smartvid.testtask.model.Directory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileSystemExplorer {

    @Value("${sandbox.root}")
    private String sandboxPath;

    public Directory searchDirectoryByName(String name) {
        Path path = searchDirectoryPathByName(name);
        return convertPathToDirectory(path);
    }

    public List<Directory> getSubDirectories(Directory directory) {
        File[] subDirectories = new File(directory.path).listFiles(File::isDirectory);

        if (subDirectories == null) {
            return Collections.emptyList();
        }

        return Stream.of(subDirectories)
                .map(dir -> convertPathToDirectory(dir.toPath()))
                .collect(Collectors.toList());
    }

    public List<String> getFileNamesByPath(String path) {
        String realPath = resolvePath(path);
        File[] files = new File(realPath).listFiles(File::isFile);
        if (files == null) {
            files = new File[0];
        }
        return Stream.of(files)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public BasicFileAttributes getFileAttributes(String path) {
        try {
            String realPath = resolvePath(path);
            Path filePath = Paths.get(realPath);
            return Files.readAttributes(filePath, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String resolvePath(String path) {
        return sandboxPath + File.separator + path;
    }

    private Directory convertPathToDirectory(Path path) {
        File[] files = new File(path.toString()).listFiles(File::isFile);
        if (files == null) {
            files = new File[0];
        }
        return new Directory(path.toString(), files.length);
    }

    private Path searchDirectoryPathByName(String name) {
        try {
            return Files.walk(Paths.get(sandboxPath))
                    .filter(Files::isDirectory)
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase(name.trim()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
