package com.yzh.fv.controller.user;

import com.yzh.fv.common.R;
import com.yzh.fv.service.QwenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 通义千问模型接口控制器
 */
@RestController
@RequestMapping("/user/qwen")
@Slf4j
public class QwenController {

    @Autowired
    private QwenService qwenService;

    /**
     * 智能问答接口（支持上下文）
     * @param requestMap 请求参数，包含message字段和context字段
     * @return 模型回答结果
     */
    @PostMapping("/chat")
    public R<String> chat(@RequestBody Map<String, Object> requestMap) {
        try {
            String message = (String) requestMap.get("message");
            if (message == null || message.isEmpty()) {
                return R.error("提问内容不能为空");
            }
            
            // 获取上下文消息（可选）
            @SuppressWarnings("unchecked")
            List<Map<String, String>> context = (List<Map<String, String>>) requestMap.get("context");
            
            log.info("用户提问: {}, 上下文消息数: {}", message, context != null ? context.size() : 0);
            
            // 如果有上下文，使用上下文对话
            String answer;
            if (context != null && !context.isEmpty()) {
                answer = qwenService.chatWithContext(message, context);
            } else {
                answer = qwenService.chat(message);
            }
            
            log.info("模型回答长度: {} 字符", answer.length());
            
            return R.success(answer);
        } catch (Exception e) {
            log.error("智能问答失败", e);
            return R.error("智能问答失败，请稍后重试");
        }
    }

    /**
     * 生成回答接口
     * @param map 请求参数，包含prompt字段
     * @return 模型回答结果
     */
    @PostMapping("/generate")
    public R<String> generate(@RequestBody Map<String, String> map) {
        try {
            String prompt = map.get("prompt");
            if (prompt == null || prompt.isEmpty()) {
                return R.error("提示内容不能为空");
            }
            
            log.info("生成请求: {}", prompt);
            String answer = qwenService.generateAnswer(prompt);
            log.info("生成结果: {}", answer);
            
            return R.success(answer);
        } catch (Exception e) {
            log.error("生成回答失败", e);
            return R.error("生成回答失败，请稍后重试");
        }
    }
}
