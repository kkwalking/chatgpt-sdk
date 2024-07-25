package top.kelton.chatgpt;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;
import top.kelton.chatgpt.interceptor.OpenAiInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ApiTest {
    private OpenAiService openAiService;

    private String apiKey;
    private String host;


    @Before
    public void setup() {
        host = "https://api.openai.com/";
        apiKey = "***";
        // 创建日志拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OpenAiInterceptor openAiInterceptor = new OpenAiInterceptor(apiKey);

        // 1. 创建 OkHttpClient 并添加拦截器
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(openAiInterceptor)
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                .build();

        // 2. 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        // 创建服务
        openAiService = retrofit.create(OpenAiService.class);
    }

    @Test
    public void test_chat_completion() throws IOException {
        ArrayList<ChatCompletionRequest.Message> messages = new ArrayList<>();
        messages.add(ChatCompletionRequest.Message.builder().role("user").content("Java的异常体系").build());
        ChatCompletionRequest request = ChatCompletionRequest.builder().model("gpt-4o").messages(messages).build();
        Call<ChatCompletionResponse> call = openAiService.createChatCompletion(request);
        Response<ChatCompletionResponse> execute = call.execute();
        log.info("返回结果 {}", execute.body());
    }
}
