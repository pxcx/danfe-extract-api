package br.com.pxcx.danfe.rest;

import br.com.pxcx.danfe.service.FileSystemStorageService;
import br.com.pxcx.danfe.type.StatusOutput;
import br.com.pxcx.danfe.type.SuccesStatusOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CleanPackagesRest {
    private final FileSystemStorageService storageService;

    @Autowired
    public CleanPackagesRest(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @CrossOrigin
    @PostMapping("/clean-packages")
    public StatusOutput cleanPackages() {
        storageService.init();
        storageService.deleteAll();
        return new SuccesStatusOutput();
    }
}
