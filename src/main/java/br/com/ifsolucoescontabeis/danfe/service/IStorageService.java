package br.com.ifsolucoescontabeis.danfe.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface IStorageService {
    void init();
    String store(MultipartFile file);
    void copy(Resource file, String destinationDir);
    List<Path> loadAll();
    List<Path> loadDir(String dir);
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();

}