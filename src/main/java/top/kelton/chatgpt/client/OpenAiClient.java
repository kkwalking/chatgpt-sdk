package top.kelton.chatgpt.client;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;

/**
 * 只能实现同步，流式响应需要使用EventSources.Factory来实现
 */
public interface OpenAiClient {
    String V1_COMPLETIONS = "v1/chat/completions";

    @POST(V1_COMPLETIONS)
    Call<ChatCompletionResponse> chatCompletions(@Body ChatCompletionRequest body);

}