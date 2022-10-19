package com.example.danfe.type;

public class DanfeExtractItem {
    private String description;
    private String ncm;
    private String cst;
    private String cfop;
    private String aliqIcms;

    public DanfeExtractItem(String description, String ncm, String cst, String cfop, String aliqIcms) {
        this.description = description;
        this.ncm = ncm;
        this.cst = cst;
        this.cfop = cfop;
        this.aliqIcms = aliqIcms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getAliqIcms() {
        return aliqIcms;
    }

    public void setAliqIcms(String aliqIcms) {
        this.aliqIcms = aliqIcms;
    }
}
