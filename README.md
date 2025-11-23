# 🧬 GeneInsight - 基因功能智能分析平台

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)
![License](https://img.shields.io/badge/License-MIT-yellow)
![AI Powered](https://img.shields.io/badge/AI-Powered-orange)

一个创新的生物信息学分析平台，结合传统基因分析算法与现代大语言模型，为科研人员和学生提供智能化的基因功能解读。

## ✨ 核心特性

### 🧪 专业的生物信息学分析
- **序列基础分析**: GC含量计算、核苷酸组成统计
- **开放阅读框预测**: 自动识别蛋白质编码区域
- **功能预测**: 基于序列特征的功能分析和疾病关联推断
- **多维度结果**: 全面的分析报告和可视化展示

### 🤖 AI 智能增强
- **DeepSeek集成**: 使用先进的大语言模型提供专业解读
- **智能解释**: 将复杂的生物信息转化为通俗易懂的语言
- **临床关联**: 自动分析基因的疾病关联和临床意义
- **模拟模式**: 无API密钥时自动使用模拟响应

### 🎯 用户友好设计
- **预定义基因库**: 内置常见癌症相关基因和模式生物
- **智能表单**: 下拉选择 + 自定义输入，操作便捷
- **示例序列**: 一键填充测试数据，快速体验
- **响应式界面**: 基于Bootstrap 5的现代化Web界面

## 🚀 快速开始

### 环境要求
- **Java**: 17 或更高版本
- **Maven**: 3.6 或更高版本
- **DeepSeek API Key** (可选，用于真实AI分析)

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/geneinsight.git
   cd geneinsight
   
2. **配置应用**
   
  配置DeepSeek API密钥以获得真实AI分析
  deepseek.api.key=your_actual_deepseek_api_key_here

3. **编译运行**
   
  使用Maven直接运行
  mvn spring-boot:run

## 🎮 使用演示
#### 选择基因: 从下拉菜单选择 BRCA1 或输入自定义基因

#### 选择物种: 选择 Homo sapiens (人类)

#### 输入序列: 粘贴DNA序列或点击"示例序列"按钮

#### 开始分析: 点击"开始分析"获取详细报告
