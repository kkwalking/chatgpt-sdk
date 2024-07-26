package top.kelton.chatgpt;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Before;
import org.junit.Test;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;
import top.kelton.chatgpt.session.Configuration;
import top.kelton.chatgpt.session.Factory;
import top.kelton.chatgpt.session.OpenAiSession;
import top.kelton.chatgpt.session.defaults.DefaultFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


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


    /**
     * chat - 流式应答
     * @throws Exception
     */
    @Test
    public void test_chat_completion_stream() throws Exception {
        ArrayList<ChatCompletionRequest.Message> messages = new ArrayList<>();
        messages.add(ChatCompletionRequest.Message.builder().role("user").content("Java读写文件").build());
        ChatCompletionRequest request = ChatCompletionRequest.builder().stream(true).model("gpt-4o").messages(messages).build();

        openAiSession.completions(request, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                log.info("测试结果 {}", data);
            }
        });

        new CountDownLatch(1).await();
    }

    /**
     * chat - 流式应答 -使用自定义apiHost和apikey
     * @throws Exception
     */
    @Test
    public void test_chat_completion_stream_apiKey() throws Exception {
        ArrayList<ChatCompletionRequest.Message> messages = new ArrayList<>();
        messages.add(ChatCompletionRequest.Message.builder().role("user").content("Java读写文件").build());
        ChatCompletionRequest request = ChatCompletionRequest.builder().stream(true).model("gpt-4o").messages(messages).build();

        String apihost="https://***/";
        String apikey="sk-*****";

        openAiSession.completions(apihost, apikey, request, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                log.info("测试结果 {}", data);
            }
        });

        new CountDownLatch(1).await();
    }

}
