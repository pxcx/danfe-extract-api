package com.example.danfe.rest;

import com.example.danfe.service.ExtractDanfeItemsService;
import com.example.danfe.type.DanfeExtractOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtractItemsRest {
    private ExtractDanfeItemsService extractItemsService;

    @Autowired
    public ExtractItemsRest(ExtractDanfeItemsService extractItemsService) {
        this.extractItemsService = extractItemsService;
    }

    @GetMapping("packages/{packageID}/extract-items")
    public DanfeExtractOutput extractItems(@PathVariable String packageID) {
        extractItemsService.setPackageID(packageID);
        extractItemsService.execute();
        return extractItemsService.getOutput();
    }
}
