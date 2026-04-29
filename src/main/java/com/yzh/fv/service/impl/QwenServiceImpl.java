package com.yzh.fv.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.yzh.fv.service.QwenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通义千问模型服务实现类 - 营养分析大师
 */
@Service
@Slf4j
public class QwenServiceImpl implements QwenService {

    // 从配置文件读取API密钥
    @Value("${qwen.api.api-key}")
    private String apiKey;

    // 从配置文件读取模型版本
    @Value("${qwen.api.model}")
    private String model;
    
    // 从配置文件读取最大生成长度
    @Value("${qwen.api.max-tokens}")
    private int maxTokens;
    
    // 从配置文件读取温度参数
    @Value("${qwen.api.temperature}")
    private double temperature;

    /**
     * 营养分析大师的系统提示词 - 定义AI的身份和职责（简洁版）
     */
/*    private static final String NUTRITION_EXPERT_PROMPT =
            "你是一位专业的果蔬营养分析大师，精通各种水果、蔬菜的营养价值、功效、购买建议和食用搭配。回答要求：" +
            "1.简洁直接，不超过100字" +
            "2.只说核心要点，不写开场白" +
            "3.用数字列表（如：1.2.3.）" +
            "4.避免客套话和重复内容"+
                    "5. 提供各类果蔬的详细营养价值和健康功效信息\n" +
                    "6. 给出合理的果蔬购买建议和挑选技巧（如何辨别新鲜度、成熟度等）\n" +
                    "7. 提供果蔬的最佳食用方式、搭配建议和烹饪方法\n" +
                    "8. 根据不同人群的需求（如儿童、孕妇、老人、病人等）提供定制化的饮食建议\n" +
                    "9. 解答常见的营养误区，传播科学的饮食知识\n" +
                    "10. 结合现代营养学理论，提供平衡膳食的指导方案\n" +
                    "11. 针对特定健康问题（如高血压、糖尿病、减肥等）推荐合适的果蔬";*/
    private static final String NUTRITION_EXPERT_PROMPT =
            "你是一位专业的果蔬营养分析大师，精通各种水果、蔬菜的营养价值、功效、购买建议和食用搭配。\n" +
                    "\n你的职责包括：\n" +
                    "1. 提供各类果蔬的详细营养价值和健康功效信息\n" +
                    "2. 给出合理的果蔬购买建议和挑选技巧（如何辨别新鲜度、成熟度等）\n" +
                    "3. 提供果蔬的最佳食用方式、搭配建议和烹饪方法\n" +
                    "4. 根据不同人群的需求（如儿童、孕妇、老人、病人等）提供定制化的饮食建议\n" +
                    "5. 解答常见的营养误区，传播科学的饮食知识\n" +
                    "6. 结合现代营养学理论，提供平衡膳食的指导方案\n" +
                    "7. 针对特定健康问题（如高血压、糖尿病、减肥等）推荐合适的果蔬\n" +
                    "\n请确保你的回答：\n" +
                    "- 专业、准确，基于科学的营养学知识\n" +
                    "- 实用、具体，提供可操作的建议\n" +
                    "- 友好、易懂，避免过于学术化的术语\n" +
                    "- 始终以用户健康为首要考虑因素";
    /**
     * 生成回答
     * @param prompt 提示词
     * @return 回答
     */
    @Override
    public String generateAnswer(String prompt) {
        return callQwenAPI(prompt, null);
    }

    /**
     * 对话
     * @param message 用户消息
     * @return 回答
     */
    @Override
    public String chat(String message) {
        return callQwenAPI(message, null);
    }

    /**
     * 带上下文的对话
     * @param currentMessage 当前用户消息
     * @param contextMessages 上下文历史消息
     * @return 回答
     */
    @Override
    public String chatWithContext(String currentMessage, List<Map<String, String>> contextMessages) {
        return callQwenAPI(currentMessage, contextMessages);
    }

    /**
     * 调用通义千问API的核心方法（统一处理）
     * @param userMessage 用户消息
     * @param contextMessages 上下文消息（可为 null）
     * @return AI回答
     */
    private String callQwenAPI(String userMessage, List<Map<String, String>> contextMessages) {
        // 输入验证
        if (userMessage == null || userMessage.trim().isEmpty()) {
            log.warn("用户消息为空");
            return "请输入您的问题哦~";
        }

        try {
            log.info("收到用户问题: {}", userMessage.substring(0, Math.min(50, userMessage.length())));
            
            // 构建消息列表
            List<Message> messages = new ArrayList<>();
            
            // 添加系统角色提示词（定义AI身份）
            messages.add(Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content(NUTRITION_EXPERT_PROMPT)
                    .build());
            
            // 如果有上下文消息，添加到消息列表
            if (contextMessages != null && !contextMessages.isEmpty()) {
                log.info("添加 {} 条上下文消息", contextMessages.size());
                for (Map<String, String> ctx : contextMessages) {
                    String role = ctx.get("role");
                    String content = ctx.get("content");
                    if (role != null && content != null) {
                        messages.add(Message.builder()
                                .role("user".equals(role) ? Role.USER.getValue() : Role.ASSISTANT.getValue())
                                .content(content)
                                .build());
                    }
                }
            }
            
            // 添加当前用户消息
            messages.add(Message.builder()
                    .role(Role.USER.getValue())
                    .content(userMessage.trim())
                    .build());
            
            // 构建请求参数
            GenerationParam param = GenerationParam.builder()
                    .model(model)
                    .messages(messages)
                    .apiKey(apiKey)
                    .maxTokens(150)  // 限制最大回答长度（约100字）
                    .temperature(0.7f)  // 降低温度，减少随机性
                    .topP(0.8)  // 控制采样范围
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            // 调用通义千问API
            Generation generation = new Generation();
            GenerationResult result = generation.call(param);

            // 解析并返回结果
            String answer = extractAnswer(result);
            log.info("AI回答生成成功，长度: {} 字符", answer.length());
            return answer;
            
        } catch (NoApiKeyException e) {
            log.error("API密钥未配置或无效", e);
            return "⚠️ 系统提示：请先在配置文件中设置有效的通义千问API密钥（qwen.api.api-key）";
        } catch (InputRequiredException e) {
            log.error("请求参数不完整", e);
            return "输入参数有误，请重新提问";
        } catch (ApiException e) {
            log.error("API调用异常: {}", e.getMessage(), e);
            return "很抱歉，服务暂时繁忙，请稍后再试";
        } catch (Exception e) {
            log.error("调用通义千问API失败", e);
            return "很抱歉，我暂时无法回答这个问题。请稍后重试或换个方式提问。";
        }
    }

    /**
     * 从API返回结果中提取回答内容
     * @param result API返回结果
     * @return 提取的回答文本
     */
    private String extractAnswer(GenerationResult result) {
        if (result == null || result.getOutput() == null) {
            log.warn("API返回结果为空");
            return "很抱歉，我暂时无法回答这个问题，请稍后重试。";
        }

        // 优先从choices中获取
        if (result.getOutput().getChoices() != null && !result.getOutput().getChoices().isEmpty()) {
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();
            if (content != null && !content.trim().isEmpty()) {
                return content.trim();
            }
        }

        // 备用：从text字段获取
        if (result.getOutput().getText() != null && !result.getOutput().getText().trim().isEmpty()) {
            return result.getOutput().getText().trim();
        }

        log.warn("API返回结果中没有有效内容");
        return "很抱歉，我暂时无法回答这个问题，请稍后重试。";
    }
}
