package br.com.ifsolucoescontabeis.danfe.service;

import br.com.ifsolucoescontabeis.danfe.type.DanfeExtractItem;
import br.com.ifsolucoescontabeis.danfe.type.DanfeExtractOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class ExtractDanfeItemsService {
    private String packageID;
    private DanfeExtractOutput output;
    private IStorageService storageService;
    private ProcessXMLService xmlService;
    private Logger logger = LoggerFactory.getLogger(ExtractDanfeItemsService.class);

    @Autowired
    public ExtractDanfeItemsService(IStorageService storageService, ProcessXMLService xmlService) {
        this.storageService = storageService;
        this.xmlService = xmlService;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public void  execute() {
        output = new DanfeExtractOutput(packageID);
        for (Path xmlPath: storageService.loadDir(packageID)) {
            if (xmlPath.getFileName().toString().contains(".xml")) {
                xmlService.setXMLFile(xmlPath.toFile());
                xmlService.execute();
                List<DanfeExtractItem> out = xmlService.getOutput();
                logger.debug("Processing: " + xmlPath.toString() + ", " + out.size() + " items extracted.");
                output.getExtract().addAll(xmlService.getOutput());
            }
        }
    }

    public DanfeExtractOutput getOutput() {
        return output;
    }
}
