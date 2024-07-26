package top.kelton.chatgpt.session.defaults;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import top.kelton.chatgpt.client.OpenAiClient;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;
import top.kelton.chatgpt.session.Configuration;
import top.kelton.chatgpt.session.OpenAiSession;

/**
 * @author zzk
 * @description
 * @created 2024/7/26
 */
public class DefaultOpenAiSession implements OpenAiSession {

    final static String NULL = "NULL";

    private Configuration configuration;

    private OpenAiClient openAiClient;

    private EventSource.Factory factory;


    public DefaultOpenAiSession(Configuration configuration) {

        this.configuration = configuration;
        this.openAiClient = configuration.getOpenAiClient();
        this.factory = configuration.createEventSourceFactory();
    }


    /**
     * 同步 chat
     * @param question 问题
     * @return
     * @throws Exception
     */
    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest question) throws Exception {
        return openAiClient.chatCompletions(question).execute().body();
    }

    @Override
    public EventSource completions(ChatCompletionRequest question, EventSourceListener eventSourceListener) {
        return completions(NULL, NULL, question, eventSourceListener);

    }

    @Override
    public EventSource completions(String apiHost, String apiKey, ChatCompletionRequest question, EventSourceListener eventSourceListener) {
        if (!question.isStream()) {
            throw new RuntimeException("illegal parameter: stream is false!");
        }
        apiHost = NULL.equals(apiHost) ? configuration.getApiHost() : apiHost;
        apiKey = NULL.equals(apiKey) ? configuration.getApiKey() : apiKey;
        // 构建请求信息
        Request request = new Request.Builder()
                .url(apiHost.concat(OpenAiClient.V1_COMPLETIONS))
                .header("apiKey", apiKey)
                .post(RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(question)))
                .build();
        // 返回事件结果
        return factory.newEventSource(request, eventSourceListener);
    }
}
