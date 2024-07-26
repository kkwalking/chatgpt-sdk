package top.kelton.chatgpt;

import com.google.gson.Gson;
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
import top.kelton.chatgpt.client.OpenAiService;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;
import top.kelton.chatgpt.interceptor.HttpInterceptor;
import top.kelton.chatgpt.session.Configuration;
import top.kelton.chatgpt.session.Factory;
import top.kelton.chatgpt.session.OpenAiSession;
import top.kelton.chatgpt.session.defaults.DefaultFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ApiTest {
    private OpenAiSession openAiSession;

    private String apiKey;
    private String host;

    private Gson gson;


    @Before
    public void setup() throws IOException {
        gson = new Gson();
        Properties prop = new Properties();
        prop.load(ApiTest.class.getClassLoader().getResourceAsStream(".env.local"));
        host = prop.getProperty("chatgpt.config.apihost");
        apiKey = prop.getProperty("chatgpt.config.apikey");
        Configuration configuration = new Configuration(host, apiKey);
        Factory defaultFactory = new DefaultFactory(configuration);
        openAiSession = defaultFactory.openSession();
    }

    @Test
    public void test_chat_completion() throws Exception {
        ArrayList<ChatCompletionRequest.Message> messages = new ArrayList<>();
        messages.add(ChatCompletionRequest.Message.builder().role("user").content("Java读写文件").build());
        ChatCompletionRequest request = ChatCompletionRequest.builder().model("gpt-4o").messages(messages).build();
        ChatCompletionResponse completions = openAiSession.completions(request);
        log.info("返回结果 {}", gson.toJson(completions));
    }

}
