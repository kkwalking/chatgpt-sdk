package top.kelton.chatgpt.session.defaults;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.kelton.chatgpt.client.OpenAiService;
import top.kelton.chatgpt.interceptor.HttpInterceptor;
import top.kelton.chatgpt.session.Configuration;
import top.kelton.chatgpt.session.Factory;
import top.kelton.chatgpt.session.OpenAiSession;

import java.util.concurrent.TimeUnit;

/**
 * @author zzk
 * @description
 * @created 2024/7/26
 */
public class DefaultFactory implements Factory {

    private Configuration configuration;


    public DefaultFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public OpenAiSession openSession() {
        // 创建日志拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpInterceptor httpInterceptor = new HttpInterceptor(configuration.getApiKey());

        // 1. 创建 OkHttpClient 并添加拦截器
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpClient
                .addInterceptor(logging)
                .addInterceptor(httpInterceptor)
                .connectTimeout(450, TimeUnit.SECONDS)
                .writeTimeout(450, TimeUnit.SECONDS)
                .readTimeout(450, TimeUnit.SECONDS)
                .build();
        configuration.setOkHttpClient(okHttpClient);

        // 2. 创建 Retrofit 实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configuration.getApiHost())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        // 创建服务
        configuration.setOpenAiService(retrofit.create(OpenAiService.class));
        return new DefaultOpenAiSession(configuration);
    }
}
