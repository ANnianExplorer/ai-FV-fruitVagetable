package com.yzh.fv.service;

import java.util.List;
import java.util.Map;

/**
 * 通义千问模型服务接口
 */
public interface QwenService {

    /**
     * 调用通义千问模型生成回答
     * @param prompt 用户提问内容
     * @return 模型生成的回答
     */
    String generateAnswer(String prompt);

    /**
     * 调用通义千问模型进行对话
     * @param messages 对话历史消息
     * @return 模型生成的回答
     */
    String chat(String messages);

    /**
     * 带上下文的对话（支持连续对话）
     * @param currentMessage 当前用户消息
     * @param contextMessages 上下文历史消息
     * @return 模型生成的回答
     */
    String chatWithContext(String currentMessage, List<Map<String, String>> contextMessages);
}
