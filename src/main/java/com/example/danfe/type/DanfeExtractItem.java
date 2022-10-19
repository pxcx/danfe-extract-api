package com.example.danfe.type;

public class DanfeExtractItem {
    private String description;
    private String ncm;
    private String cst;
    private String cfop;
    private String aliqIcms;
    private String aliqPis;
    private String aliqCofins;
    private String origem;
    private String cest;

    public DanfeExtractItem() {
    }

    public DanfeExtractItem(String description, String ncm, String cst, String cfop, String aliqIcms, String aliqPis, String aliqCofins, String origem, String cest) {
        this.description = description;
        this.ncm = ncm;
        this.cst = cst;
        this.cfop = cfop;
        this.aliqIcms = aliqIcms;
        this.aliqPis = aliqPis;
        this.aliqCofins = aliqCofins;
        this.origem = origem;
        this.cest = cest;
    }

    public String getAliqPis() {
        return aliqPis;
    }

    public void setAliqPis(String aliqPis) {
        this.aliqPis = aliqPis;
    }

    public String getAliqCofins() {
        return aliqCofins;
    }

    public void setAliqCofins(String aliqCofins) {
        this.aliqCofins = aliqCofins;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getCest() {
        return cest;
    }

    public void setCest(String cest) {
        this.cest = cest;
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
