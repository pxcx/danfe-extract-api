package br.com.ifsolucoescontabeis.danfe.rest;

import br.com.ifsolucoescontabeis.danfe.service.FileSystemStorageService;
import br.com.ifsolucoescontabeis.danfe.type.StatusOutput;
import br.com.ifsolucoescontabeis.danfe.type.SuccesStatusOutput;
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
