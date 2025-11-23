package com.geneinsight.model;

import java.util.List;
import java.util.Map;

public class GeneAnalysisResult {
    private String geneName;
    private String species;
    private int sequenceLength;
    private double gcContent;
    private Map<String, Integer> nucleotideComposition;
    private List<String> potentialProteins;
    private String functionalAnalysis;
    private String diseaseAssociations;
    private String llmInterpretation;

    // 默认构造函数
    public GeneAnalysisResult() {}

    // 带参数构造函数
    public GeneAnalysisResult(String geneName, String species, int sequenceLength,
                              double gcContent, Map<String, Integer> nucleotideComposition,
                              List<String> potentialProteins, String functionalAnalysis,
                              String diseaseAssociations, String llmInterpretation) {
        this.geneName = geneName;
        this.species = species;
        this.sequenceLength = sequenceLength;
        this.gcContent = gcContent;
        this.nucleotideComposition = nucleotideComposition;
        this.potentialProteins = potentialProteins;
        this.functionalAnalysis = functionalAnalysis;
        this.diseaseAssociations = diseaseAssociations;
        this.llmInterpretation = llmInterpretation;
    }

    // Getter 和 Setter 方法
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

    public int getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(int sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    public double getGcContent() {
        return gcContent;
    }

    public void setGcContent(double gcContent) {
        this.gcContent = gcContent;
    }

    public Map<String, Integer> getNucleotideComposition() {
        return nucleotideComposition;
    }

    public void setNucleotideComposition(Map<String, Integer> nucleotideComposition) {
        this.nucleotideComposition = nucleotideComposition;
    }

    public List<String> getPotentialProteins() {
        return potentialProteins;
    }

    public void setPotentialProteins(List<String> potentialProteins) {
        this.potentialProteins = potentialProteins;
    }

    public String getFunctionalAnalysis() {
        return functionalAnalysis;
    }

    public void setFunctionalAnalysis(String functionalAnalysis) {
        this.functionalAnalysis = functionalAnalysis;
    }

    public String getDiseaseAssociations() {
        return diseaseAssociations;
    }

    public void setDiseaseAssociations(String diseaseAssociations) {
        this.diseaseAssociations = diseaseAssociations;
    }

    public String getLlmInterpretation() {
        return llmInterpretation;
    }

    public void setLlmInterpretation(String llmInterpretation) {
        this.llmInterpretation = llmInterpretation;
    }

    @Override
    public String toString() {
        return "GeneAnalysisResult{" +
                "geneName='" + geneName + '\'' +
                ", species='" + species + '\'' +
                ", sequenceLength=" + sequenceLength +
                ", gcContent=" + gcContent +
                ", nucleotideComposition=" + nucleotideComposition +
                ", potentialProteins=" + potentialProteins +
                ", functionalAnalysis='" + functionalAnalysis + '\'' +
                ", diseaseAssociations='" + diseaseAssociations + '\'' +
                ", llmInterpretation='" + llmInterpretation + '\'' +
                '}';
    }
}