package br.com.ifsolucoescontabeis.danfe.rest;

import br.com.ifsolucoescontabeis.danfe.service.PackageUploadService;
import br.com.ifsolucoescontabeis.danfe.type.DanfePackageUploadOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadPackageRest {
    private final PackageUploadService uploadService;

    @Autowired
    public UploadPackageRest(PackageUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @CrossOrigin
    @PostMapping("/upload-package")
    public DanfePackageUploadOutput uploadDanfePackage(@RequestParam("danfePackage") MultipartFile danfePackage) {
        uploadService.setDanfePackage(danfePackage);
        uploadService.execute();
        return uploadService.getOutput();
    }
}
