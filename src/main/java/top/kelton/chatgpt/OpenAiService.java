package top.kelton.chatgpt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;

public interface OpenAiService {
    @Headers({
        "Content-Type: application/json"

    })
    @POST("v1/chat/completions")
    Call<ChatCompletionResponse> createChatCompletion(@Body ChatCompletionRequest body);
}