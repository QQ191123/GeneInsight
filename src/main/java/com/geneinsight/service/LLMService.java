package com.geneinsight.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LLMService {

    @Value("${deepseek.api.key:}")
    private String deepseekApiKey;

    @Value("${deepseek.api.url:https://api.deepseek.com/v1}")
    private String deepseekApiUrl;

    private final WebClient webClient;
    private boolean apiAvailable = false;

    public LLMService() {
        this.webClient = WebClient.builder()
                .baseUrl(deepseekApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void init() {
        // 检查API密钥
        if (deepseekApiKey != null && !deepseekApiKey.trim().isEmpty()) {
            this.apiAvailable = true;
            System.out.println("✅ DeepSeek API 配置成功");
            System.out.println("✅ API URL: " + deepseekApiUrl);
        } else {
            System.out.println("⚠️ 未配置DeepSeek API密钥，将使用模拟响应");
            this.apiAvailable = false;
        }
    }

    /**
     * 使用DeepSeek大语言模型解释基因分析结果
     */
    public String interpretGeneAnalysis(String geneName, String species,
                                        String functionalAnalysis,
                                        String diseaseAssociations) {
        if (!apiAvailable) {
            return generateMockInterpretation(geneName, species, functionalAnalysis, diseaseAssociations);
        }

        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek-chat");  // DeepSeek 模型

            // 构建消息
            Map<String, String> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", buildInterpretationPrompt(geneName, species, functionalAnalysis, diseaseAssociations));

            requestBody.put("messages", new Map[]{message});
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);
            requestBody.put("stream", false);

            // 调用DeepSeek API
            Map<String, Object> response = webClient.post()
                    .uri("/chat/completions")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + deepseekApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            // 解析响应
            if (response != null && response.containsKey("choices")) {
                java.util.List<Map<String, Object>> choices = (java.util.List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> messageContent = (Map<String, Object>) firstChoice.get("message");
                    return (String) messageContent.get("content");
                }
            }

            return generateMockInterpretation(geneName, species, functionalAnalysis, diseaseAssociations);

        } catch (Exception e) {
            System.err.println("❌ 调用DeepSeek API失败: " + e.getMessage());
            e.printStackTrace();
            return generateMockInterpretation(geneName, species, functionalAnalysis, diseaseAssociations);
        }
    }

    /**
     * 构建提示词
     */
    private String buildInterpretationPrompt(String geneName, String species,
                                             String functionalAnalysis,
                                             String diseaseAssociations) {
        return "你是一个专业的生物信息学专家。请用通俗易懂的语言解释以下基因分析结果：\n\n" +
                "## 基因信息\n" +
                "- **基因名称**: " + geneName + "\n" +
                "- **物种**: " + species + "\n\n" +
                "## 分析结果\n" +
                "- **功能分析**: " + functionalAnalysis + "\n" +
                "- **疾病关联**: " + diseaseAssociations + "\n\n" +
                "## 请解释以下内容：\n" +
                "1. 这个基因可能的主要功能是什么？\n" +
                "2. 它在生物体中扮演什么重要角色？\n" +
                "3. 相关的疾病关联意味着什么？\n" +
                "4. 这个基因的研究有什么重要意义？\n\n" +
                "请用中文回答，语言要专业但易懂，适合生物学背景的学生理解。";
    }

    /**
     * 生成模拟响应（当API不可用时使用）
     */
    private String generateMockInterpretation(String geneName, String species,
                                              String functionalAnalysis,
                                              String diseaseAssociations) {
        return "## " + geneName + " 基因分析解读 (" + species + ")\n\n" +
                "### 🧬 基因功能概述\n" +
                "基于生物信息学分析，" + geneName + " 基因" + functionalAnalysis + "。\n\n" +
                "### 🏥 临床意义\n" +
                diseaseAssociations + "\n\n" +
                "### 🔬 科学研究价值\n" +
                "该基因的研究对于理解相关生物学过程和疾病机制具有重要意义，可能为未来的基因治疗和药物开发提供靶点。\n\n" ;


    }

    public boolean isApiAvailable() {
        return apiAvailable;
    }
}