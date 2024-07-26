package top.kelton.chatgpt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatCompletionRequest {
    /**
     * 模型
     */
    private String model;
    /**
     * 消息
     */
    private List<Message> messages;

    private boolean stream;

    /**
     *  消息类
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static  class Message {
        private String role;
        private String content;
    }
}
