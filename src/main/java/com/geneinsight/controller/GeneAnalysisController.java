package com.geneinsight.controller;

import com.geneinsight.model.GeneAnalysisRequest;
import com.geneinsight.model.GeneAnalysisResult;
import com.geneinsight.service.BioinformaticsService;
import com.geneinsight.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GeneAnalysisController {

    @Autowired
    private BioinformaticsService bioinformaticsService;

    @Autowired
    private LLMService llmService;

    /**
     * 显示首页 - 基因分析表单
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("geneAnalysisRequest", new GeneAnalysisRequest());
        model.addAttribute("apiAvailable", llmService.isApiAvailable());

        // 添加预定义数据到模型，供前端使用
        model.addAttribute("predefinedGenes", GeneAnalysisRequest.PREDEFINED_GENES);
        model.addAttribute("predefinedSpecies", GeneAnalysisRequest.PREDEFINED_SPECIES);

        return "index";
    }

    /**
     * 处理基因分析请求
     */
    @PostMapping("/analyze")
    public String analyzeGene(@ModelAttribute GeneAnalysisRequest request,
                              @RequestParam(required = false) String customGene,
                              Model model) {
        try {
            // 处理自定义基因名称
            if (customGene != null && !customGene.trim().isEmpty()) {
                request.setGeneName(customGene.trim());
            }

            // 验证输入
            String validationError = validateInput(request);
            if (validationError != null) {
                model.addAttribute("error", validationError);
                return home(model);
            }

            // 清理输入数据
            String geneSequence = request.getGeneSequence().trim().toUpperCase();
            String geneName = request.getGeneName().trim();
            String species = request.getSpecies().trim();

            // 验证基因序列只包含有效字符
            if (!isValidDNASequence(geneSequence)) {
                model.addAttribute("error", "基因序列只能包含 A, T, G, C 字符");
                return home(model);
            }

            // 验证序列长度
            if (geneSequence.length() < 10) {
                model.addAttribute("error", "基因序列太短，请输入至少10个碱基的序列");
                return home(model);
            }

            if (geneSequence.length() > 10000) {
                model.addAttribute("error", "基因序列太长，请输入不超过10000个碱基的序列");
                return home(model);
            }

            System.out.println("🔬 开始基因分析...");
            System.out.println("📝 基因名称: " + geneName);
            System.out.println("🐾 物种: " + species);
            System.out.println("📏 序列长度: " + geneSequence.length());

            // 执行生物信息学分析
            GeneAnalysisResult result = bioinformaticsService.analyzeGeneSequence(
                    geneSequence,
                    geneName,
                    species
            );

            System.out.println("✅ 生物信息学分析完成");
            System.out.println("🧬 GC含量: " + result.getGcContent() + "%");
            System.out.println("🔢 预测蛋白质数量: " + result.getPotentialProteins().size());

            // 使用大语言模型进行解释
            System.out.println("🤖 开始AI解释...");
            String llmInterpretation = llmService.interpretGeneAnalysis(
                    result.getGeneName(),
                    result.getSpecies(),
                    result.getFunctionalAnalysis(),
                    result.getDiseaseAssociations()
            );

            result.setLlmInterpretation(llmInterpretation);

            // 添加结果到模型
            model.addAttribute("result", result);
            model.addAttribute("apiAvailable", llmService.isApiAvailable());

            // 重新添加预定义数据，确保返回页面时也能显示
            model.addAttribute("predefinedGenes", GeneAnalysisRequest.PREDEFINED_GENES);
            model.addAttribute("predefinedSpecies", GeneAnalysisRequest.PREDEFINED_SPECIES);

            System.out.println("🎉 基因分析完成: " + geneName);
            System.out.println("📊 序列长度: " + geneSequence.length());
            System.out.println("💬 AI解释长度: " + llmInterpretation.length());
            System.out.println("=" .repeat(50));

        } catch (Exception e) {
            System.err.println("❌ 分析过程中出现错误: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "分析过程中出现错误: " + e.getMessage());

            // 错误时也要重新添加预定义数据
            model.addAttribute("predefinedGenes", GeneAnalysisRequest.PREDEFINED_GENES);
            model.addAttribute("predefinedSpecies", GeneAnalysisRequest.PREDEFINED_SPECIES);

            return home(model);
        }

        return "result";
    }

    /**
     * 验证输入数据
     */
    private String validateInput(GeneAnalysisRequest request) {
        if (request.getGeneSequence() == null || request.getGeneSequence().trim().isEmpty()) {
            return "基因序列不能为空";
        }

        if (request.getGeneName() == null || request.getGeneName().trim().isEmpty()) {
            return "请选择或输入基因名称";
        }

        if (request.getSpecies() == null || request.getSpecies().trim().isEmpty()) {
            return "请选择物种";
        }

        // 检查基因名称是否有效
        String geneName = request.getGeneName().trim();
        if (!isValidGeneName(geneName)) {
            return "基因名称格式不正确，请使用字母、数字和连字符";
        }

        return null; // 验证通过
    }

    /**
     * 验证DNA序列是否只包含有效字符
     */
    private boolean isValidDNASequence(String sequence) {
        if (sequence == null || sequence.isEmpty()) {
            return false;
        }

        // 只允许包含 A, T, G, C 字符（不区分大小写）
        return sequence.toUpperCase().matches("[ATGC]+");
    }

    /**
     * 验证基因名称格式
     */
    private boolean isValidGeneName(String geneName) {
        if (geneName == null || geneName.trim().isEmpty()) {
            return false;
        }

        // 允许字母、数字、连字符、下划线，长度在2-50之间
        return geneName.matches("^[a-zA-Z0-9\\-_]{2,50}$");
    }

    /**
     * 示例数据接口 - 用于前端获取示例序列
     */
    @GetMapping("/api/example-sequence")
    public String getExampleSequence(@RequestParam String gene, Model model) {
        // 这里可以返回示例序列数据
        // 实际实现可以根据需要返回JSON数据
        return "redirect:/";
    }

    /**
     * 错误处理页面
     */
    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("error", "页面加载出现错误，请返回首页重试");
        model.addAttribute("geneAnalysisRequest", new GeneAnalysisRequest());
        model.addAttribute("apiAvailable", llmService.isApiAvailable());
        model.addAttribute("predefinedGenes", GeneAnalysisRequest.PREDEFINED_GENES);
        model.addAttribute("predefinedSpecies", GeneAnalysisRequest.PREDEFINED_SPECIES);
        return "index";
    }
}