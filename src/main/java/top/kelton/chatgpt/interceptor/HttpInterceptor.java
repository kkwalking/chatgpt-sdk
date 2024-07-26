package top.kelton.chatgpt.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HttpInterceptor implements Interceptor {

    /** OpenAi apiKey */
    private final String apiKeyDefault;

    public HttpInterceptor(String apiKeyDefault) {
        this.apiKeyDefault = apiKeyDefault;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 1. 获取原始 Request
        Request original = chain.request();

        // 2. 读取 apiKey；优先使用传递的 apiKey
        String apiKeyByUser = original.header("apiKey");
        String apiKey = null == apiKeyByUser ? apiKeyDefault : apiKeyByUser;

        // 3. 构建 Request
        Request request = original.newBuilder()
                .url(original.url())
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .method(original.method(), original.body())
                .build();

        // 4. 返回执行结果
        return chain.proceed(request);
    }

}