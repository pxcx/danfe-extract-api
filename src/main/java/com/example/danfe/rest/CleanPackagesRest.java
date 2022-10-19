package com.example.danfe.rest;

import com.example.danfe.service.FileSystemStorageService;
import com.example.danfe.type.StatusOutput;
import com.example.danfe.type.SuccesStatusOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CleanPackagesRest {
    private final FileSystemStorageService storageService;

    @Autowired
    public CleanPackagesRest(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/clean-packages")
    public StatusOutput cleanPackages() {
        storageService.init();
        storageService.deleteAll();
        return new SuccesStatusOutput();
    }
}
