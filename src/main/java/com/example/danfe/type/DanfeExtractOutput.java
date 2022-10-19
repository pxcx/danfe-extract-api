package com.example.danfe.type;

public class DanfeExtractOutput {
    private String packageID;
    private DanfeExtractItem[] extract;

    public DanfeExtractOutput(String packageID, DanfeExtractItem[] extract) {
        this.packageID = packageID;
        this.extract = extract;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public DanfeExtractItem[] getExtract() {
        return extract;
    }

    public void setExtract(DanfeExtractItem[] extract) {
        this.extract = extract;
    }
}
