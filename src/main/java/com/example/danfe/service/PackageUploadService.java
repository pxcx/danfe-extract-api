package com.example.danfe.service;

import com.example.danfe.error.InvalidUsageException;
import com.example.danfe.type.DanfePackageUploadOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PackageUploadService {
    private MultipartFile danfePackage;
    private String packageID;
    private IStorageService storageService;
    private ZipFileService zipfileService;

    @Autowired
    public PackageUploadService(IStorageService storageService, ZipFileService zipfileService) {
        this.storageService = storageService;
        this.zipfileService = zipfileService;
    }

    public void setDanfePackage(MultipartFile danfePackage) {
        this.danfePackage = danfePackage;
    }

    private void validate() {
        if (danfePackage == null || danfePackage.isEmpty()) {
            throw new InvalidUsageException("DANFE Package is empty.");
        }
    }

    public void execute() {
        validate();
        storageService.init();
        packageID = storageService.store(danfePackage);
        zipfileService.setZipFilename(packageID, danfePackage.getOriginalFilename());
        zipfileService.execute();
    }

    public DanfePackageUploadOutput getOutput() {
        DanfePackageUploadOutput output = new DanfePackageUploadOutput(packageID);
        return output;
    }
}
