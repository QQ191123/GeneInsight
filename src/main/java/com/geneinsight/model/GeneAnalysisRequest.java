package com.geneinsight.model;

import java.util.Arrays;
import java.util.List;

public class GeneAnalysisRequest {
    private String geneSequence;
    private String geneName;
    private String species;

    // 预定义的基因列表
    public static final List<String> PREDEFINED_GENES = Arrays.asList(
            "BRCA1", "BRCA2", "TP53", "EGFR", "KRAS", "BRAF",
            "ALK", "MYC", "HER2", "PTEN", "APC", "RB1",
            "NF1", "VHL", "WT1", "MEN1", "RET", "MET"
    );

    // 预定义的物种列表
    public static final List<String> PREDEFINED_SPECIES = Arrays.asList(
            "Homo sapiens (人类)",
            "Mus musculus (小鼠)",
            "Rattus norvegicus (大鼠)",
            "Drosophila melanogaster (果蝇)",
            "Caenorhabditis elegans (线虫)",
            "Danio rerio (斑马鱼)",
            "Arabidopsis thaliana (拟南芥)",
            "Saccharomyces cerevisiae (酵母)",
            "Escherichia coli (大肠杆菌)",
            "其他物种"
    );

    // 构造函数
    public GeneAnalysisRequest() {}

    public GeneAnalysisRequest(String geneSequence, String geneName, String species) {
        this.geneSequence = geneSequence;
        this.geneName = geneName;
        this.species = species;
    }

    // Getter和Setter方法
    public String getGeneSequence() {
        return geneSequence;
    }

    public void setGeneSequence(String geneSequence) {
        this.geneSequence = geneSequence;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // 获取预定义基因列表
    public List<String> getPredefinedGenes() {
        return PREDEFINED_GENES;
    }

    // 获取预定义物种列表
    public List<String> getPredefinedSpecies() {
        return PREDEFINED_SPECIES;
    }
}