package micro.com.microblog.manager;

import java.io.IOException;

import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guoli on 2016/10/14.
 * 图片下载
 */
public class DownManager {

    public static void downFromOk3(String url, final DownLoadListener listener) {
        Request request = new Request.Builder().url(url).build();
        RetrofitUtils.mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("=====call===error====" + e);
                if (null != listener) listener.onError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileUtils.saveFile(response.body().byteStream(), ".jpg");
                if (null != listener) listener.onSuccess();
            }
        });
    }

    public interface DownLoadListener {
        void onSuccess();

        void onError();

    }

}
