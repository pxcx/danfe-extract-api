package br.com.ifsolucoescontabeis.danfe.service;

import br.com.ifsolucoescontabeis.danfe.type.DanfePackageUploadOutput;
import br.com.ifsolucoescontabeis.danfe.error.InvalidUsageException;
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
