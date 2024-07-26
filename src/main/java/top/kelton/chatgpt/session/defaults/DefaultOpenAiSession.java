package top.kelton.chatgpt.session.defaults;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.kelton.chatgpt.client.OpenAiService;
import top.kelton.chatgpt.domain.ChatCompletionRequest;
import top.kelton.chatgpt.domain.ChatCompletionResponse;
import top.kelton.chatgpt.interceptor.HttpInterceptor;
import top.kelton.chatgpt.session.Configuration;
import top.kelton.chatgpt.session.OpenAiSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zzk
 * @description
 * @created 2024/7/26
 */
public class DefaultOpenAiSession implements OpenAiSession {

    private Configuration configuration;

    private OpenAiService openAiService;

    private EventSource.Factory factory;


    public DefaultOpenAiSession(Configuration configuration) {

        this.configuration = configuration;
        this.openAiService = configuration.getOpenAiService();
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
        return openAiService.createChatCompletion(question).execute().body();
    }
}
