package com.example.danfe.service;

import com.example.danfe.error.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileSystemStorageService implements IStorageService{
    private final String uploadDir = "upload-dir";
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService() {
        this.rootLocation = Paths.get(uploadDir);
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Error to initialize storage", e);
        }
    }

    private String createPackageDir() {
        try {
            UUID packageID = UUID.randomUUID();
            Files.createDirectory(rootLocation.resolve(packageID.toString()));
            return packageID.toString();
        } catch (IOException e) {
            throw new StorageException("Error to initialize package directory", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            String packageID = createPackageDir();
            String destinationFilename = packageID + "/" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.rootLocation.resolve(destinationFilename));
            return packageID;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public List<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
