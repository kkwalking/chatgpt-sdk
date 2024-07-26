package top.kelton.chatgpt.session;

import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;

import java.io.IOException;

/**
 * @author zzk
 * @description
 * @created 2024/7/26
 */
public interface OpenAiSession {


    /**
     * 问答
     * @param question 问题
     * @return 响应
     */
    ChatCompletionResponse completions(ChatCompletionRequest question) throws Exception;
}
