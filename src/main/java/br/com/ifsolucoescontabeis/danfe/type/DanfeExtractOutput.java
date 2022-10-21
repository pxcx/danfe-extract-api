package br.com.ifsolucoescontabeis.danfe.type;

import java.util.ArrayList;
import java.util.List;

public class DanfeExtractOutput {
    private String packageID;
    private List<DanfeExtractItem> extract;

    public DanfeExtractOutput(String packageID) {
        this.packageID = packageID;
        this.extract = extract = new ArrayList<>();
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public List<DanfeExtractItem> getExtract() {
        return extract;
    }

    public void setExtract(List<DanfeExtractItem> extract) {
        this.extract = extract;
    }

    public void addExtractedList(List<DanfeExtractItem> extract) {
        extract.addAll(extract);
    }
}
