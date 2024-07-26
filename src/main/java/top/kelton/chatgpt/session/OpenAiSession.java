package top.kelton.chatgpt.session;

import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
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
     * chat - 同步
     * @param question 问题
     * @return 响应
     */
    ChatCompletionResponse completions(ChatCompletionRequest question) throws Exception;

    /**
     * chat -流式
     *
     * @param question 问题
     * @param eventSourceListener 实现监听；通过监听的 onEvent 方法接收数据
     */
    EventSource completions(ChatCompletionRequest question, EventSourceListener eventSourceListener);


    /**
     * chat -流式
     *
     * @param question 问题
     * @param eventSourceListener 实现监听；通过监听的 onEvent 方法接收数据
     */
    EventSource completions(String apiHost, String apiKey, ChatCompletionRequest question, EventSourceListener eventSourceListener);
}
