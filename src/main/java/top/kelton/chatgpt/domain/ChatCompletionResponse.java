package top.kelton.chatgpt.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("object")
    private String object;

    @SerializedName("created")
    private long created;

    @SerializedName("model")
    private String model;

    @SerializedName("system_fingerprint")
    private String systemFingerprint;

    @SerializedName("choices")
    private List<Choice> choices;

    @SerializedName("usage")
    private Usage usage;


    public static class Choice {

        @SerializedName("index")
        private int index;

        @SerializedName("message")
        private Message message;

        @SerializedName("logprobs")
        private Object logprobs; // Assuming logprobs is null or an object

        @SerializedName("finish_reason")
        private String finishReason;

    }

    public static class Message {

        @SerializedName("role")
        private String role;

        @SerializedName("content")
        private String content;
    }

    public static class Usage {

        @SerializedName("prompt_tokens")
        private int promptTokens;

        @SerializedName("completion_tokens")
        private int completionTokens;

        @SerializedName("total_tokens")
        private int totalTokens;

    }

}
