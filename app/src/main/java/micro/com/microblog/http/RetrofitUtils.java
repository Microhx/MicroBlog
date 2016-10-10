package micro.com.microblog.http;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import micro.com.microblog.utils.FileUtils;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by guoli on 2016/9/5.
 */
public class RetrofitUtils {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static OkHttpClient mSearchHttpClient = new OkHttpClient();

    private static Map<String, Retrofit.Builder> mContainer = new HashMap<>();

    static {
        mOkHttpClient.newBuilder().
                connectTimeout(100000, TimeUnit.SECONDS).
                readTimeout(100000, TimeUnit.SECONDS);

        mSearchHttpClient.newBuilder().
                connectTimeout(200000, TimeUnit.SECONDS).
                readTimeout(200000, TimeUnit.SECONDS).cookieJar(new CookieJar() {

            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<HttpUrl, List<Cookie>>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookieList = cookieStore.get(url);
                return null == cookieList ? new ArrayList<Cookie>() : cookieList;
            }
        });
    }

    /**
     * 普通请求
     * @param baseURL
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getInstance(String baseURL, Class<T> clazz) {
        Retrofit.Builder builder;
        if (null == (builder = mContainer.get(baseURL))) {
            synchronized (RetrofitUtils.class) {
                builder = new Retrofit.Builder();
                builder.client(mOkHttpClient).
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        addConverterFactory(StringConverterFactory.create());

                mContainer.put(baseURL, builder);
            }
        }
        return builder.baseUrl(baseURL).build().create(clazz);
    }


    /**
     * 关于搜索中需要带有cookie
     * @param baseURL
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getSearchInstance(String baseURL, Class<T> clazz) {
        Retrofit.Builder builder;
        if (null == (builder = mContainer.get(baseURL))) {
            synchronized (RetrofitUtils.class) {
                builder = new Retrofit.Builder();
                builder.client(mSearchHttpClient).
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        addConverterFactory(StringConverterFactory.create());

                mContainer.put(baseURL, builder);
            }
        }
        return builder.baseUrl(baseURL).build().create(clazz);
    }

    /**
     * 关于乱码请求
     * @param baseURL
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getGB2312Instance(String baseURL, Class<T> clazz) {
        Retrofit.Builder builder;
        if (null == (builder = mContainer.get(baseURL))) {
            synchronized (RetrofitUtils.class) {
                builder = new Retrofit.Builder();
                builder.client(mOkHttpClient).
                        addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                        addConverterFactory(InputStreamConvertFactory.create());

                mContainer.put(baseURL, builder);
            }
        }
        return builder.baseUrl(baseURL).build().create(clazz);
    }

}
