package top.kelton.chatgpt.session;

import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
import top.kelton.chatgpt.client.OpenAiClient;

/**
 * @author zzk
 * @description
 * @created 2024/7/26
 */

@Data
@NoArgsConstructor
public class Configuration {

    private String apiHost;

    private String apiKey;

    private OpenAiClient openAiClient;

    private OkHttpClient okHttpClient;

    public Configuration(String apiHost, String apiKey) {
        this.apiHost = apiHost;
        this.apiKey = apiKey;
    }

    public EventSource.Factory createEventSourceFactory() {
        return EventSources.createFactory(okHttpClient);
    }
}
