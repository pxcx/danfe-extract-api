package com.example.danfe.service;

import com.example.danfe.error.InvalidUsageException;
import com.example.danfe.error.OperationFailed;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.nio.file.Path;


@Service
public class ZipFileService {
    private String zipFilename;
    private String packageID;
    private IStorageService storageService;

    @Autowired
    public ZipFileService(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }
    public void setZipFilename(String packageID, String zipFilename) {
        this.packageID = packageID;
        this.zipFilename =  packageID + "/" + zipFilename;
    }

    private void validate() {
        if (zipFilename.isEmpty()) {
            throw new InvalidUsageException("Name of .zip file is mandatory for unzip package step.");
        }
    }

    public void execute() {
        validate();
        Path zipFilePath = storageService.load(zipFilename);
        Path packageDir = storageService.load(packageID);
        try {
            ZipFile zipFile = new ZipFile(zipFilePath.toFile());
            zipFile.extractAll(packageDir.toString());
        } catch (ZipException e) {
            throw new OperationFailed("Unzip step failed: " + e.getMessage(), e);
        }
    }
}
