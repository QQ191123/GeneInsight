package com.geneinsight.service;

import com.geneinsight.model.GeneAnalysisResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BioinformaticsService {

    /**
     * 分析基因序列
     */
    public GeneAnalysisResult analyzeGeneSequence(String sequence, String geneName, String species) {
        GeneAnalysisResult result = new GeneAnalysisResult();
        result.setGeneName(geneName);
        result.setSpecies(species);

        // 基本序列分析
        result.setSequenceLength(sequence.length());
        result.setGcContent(calculateGCContent(sequence));
        result.setNucleotideComposition(calculateNucleotideComposition(sequence));

        // 蛋白质预测
        result.setPotentialProteins(predictProteins(sequence));

        // 功能分析
        result.setFunctionalAnalysis(performFunctionalAnalysis(sequence));

        // 疾病关联分析
        result.setDiseaseAssociations(analyzeDiseaseAssociations(geneName, sequence));

        return result;
    }

    /**
     * 计算GC含量
     */
    private double calculateGCContent(String sequence) {
        if (sequence == null || sequence.isEmpty()) {
            return 0.0;
        }

        long gcCount = sequence.toUpperCase().chars()
                .filter(ch -> ch == 'G' || ch == 'C')
                .count();

        return (double) gcCount / sequence.length() * 100;
    }

    /**
     * 计算核苷酸组成
     */
    private Map<String, Integer> calculateNucleotideComposition(String sequence) {
        Map<String, Integer> composition = new HashMap<>();
        if (sequence == null || sequence.isEmpty()) {
            return composition;
        }

        String upperSequence = sequence.toUpperCase();
        composition.put("A", countOccurrences(upperSequence, 'A'));
        composition.put("T", countOccurrences(upperSequence, 'T'));
        composition.put("G", countOccurrences(upperSequence, 'G'));
        composition.put("C", countOccurrences(upperSequence, 'C'));

        return composition;
    }

    private int countOccurrences(String str, char ch) {
        return (int) str.chars().filter(c -> c == ch).count();
    }

    /**
     * 预测可能的蛋白质序列
     */
    private List<String> predictProteins(String sequence) {
        List<String> proteins = new ArrayList<>();
        if (sequence == null || sequence.length() < 3) {
            return proteins;
        }

        // 简单的开放阅读框预测
        String upperSequence = sequence.toUpperCase();

        // 寻找起始密码子ATG
        Pattern startCodon = Pattern.compile("ATG");
        Matcher matcher = startCodon.matcher(upperSequence);

        while (matcher.find()) {
            int start = matcher.start();
            // 从起始密码子开始，寻找终止密码子
            for (int i = start + 3; i <= upperSequence.length() - 3; i += 3) {
                String codon = upperSequence.substring(i, i + 3);
                if (isStopCodon(codon)) {
                    String orf = upperSequence.substring(start, i + 3);
                    if (orf.length() >= 30) { // 最小蛋白质长度
                        proteins.add(orf);
                    }
                    break;
                }
            }
        }

        return proteins.subList(0, Math.min(proteins.size(), 5)); // 返回最多5个预测
    }

    private boolean isStopCodon(String codon) {
        return codon.equals("TAA") || codon.equals("TAG") || codon.equals("TGA");
    }

    /**
     * 执行功能分析
     */
    private String performFunctionalAnalysis(String sequence) {
        StringBuilder analysis = new StringBuilder();

        if (sequence.length() > 1000) {
            analysis.append("这是一个较长的基因序列，可能编码一个复杂的蛋白质。");
        } else {
            analysis.append("这是一个相对较短的基因序列。");
        }

        double gcContent = calculateGCContent(sequence);
        if (gcContent > 60) {
            analysis.append(" GC含量较高，表明该基因可能位于基因组的GC富集区域。");
        } else if (gcContent < 40) {
            analysis.append(" GC含量较低，可能影响基因的表达调控。");
        }

        // 添加更多功能分析逻辑...
        analysis.append(" 序列分析显示可能的编码区域和调控元件。");

        return analysis.toString();
    }

    /**
     * 分析疾病关联
     */
    private String analyzeDiseaseAssociations(String geneName, String sequence) {
        // 简化的疾病关联分析
        // 在实际应用中，这里会查询生物医学数据库

        StringBuilder associations = new StringBuilder();

        // 基于基因名称的简单推断
        if (geneName.toLowerCase().contains("brca")) {
            associations.append("该基因与乳腺癌和卵巢癌风险相关。");
        } else if (geneName.toLowerCase().contains("cf")) {
            associations.append("该基因与囊性纤维化相关。");
        } else if (geneName.toLowerCase().contains("huntingtin")) {
            associations.append("该基因与亨廷顿病相关。");
        } else {
            associations.append("需要进一步研究来确定该基因的疾病关联。");
        }

        // 基于序列特征的推断
        if (containsRepeats(sequence, "CAG", 40)) {
            associations.append(" 检测到CAG重复扩展，可能与神经退行性疾病相关。");
        }

        return associations.toString();
    }

    private boolean containsRepeats(String sequence, String repeat, int threshold) {
        String pattern = "(" + repeat + "){" + threshold + ",}";
        return Pattern.compile(pattern).matcher(sequence).find();
    }
}