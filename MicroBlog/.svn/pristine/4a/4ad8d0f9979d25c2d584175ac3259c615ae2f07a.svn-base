package micro.com.microblog.mvc.presenter;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.mvc.IDetailContentView;
import micro.com.microblog.utils.LogUtils;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/9/16.
 */
public class DetailContentPresenter extends BasePresenter<IDetailContentView> {

    /**
     * 请求网页数据详细内容
     *
     * @param link
     * @param articleType
     */
    public void loadData(String link, ArticleType articleType) {
        LogUtils.d("request link :" + link);
        getCurrentView().showLoadingPage();

        String base = link.substring(0, link.lastIndexOf("/") + 1);
        String path = link.substring(link.lastIndexOf("/") + 1);

        BaseURL _baseUrl;
        if (articleType == ArticleType.PAOWANG) {
            _baseUrl = RetrofitUtils.getGB2312Instance(base, BaseURL.class);
        } else {
            _baseUrl = RetrofitUtils.getInstance(base, BaseURL.class);
        }

        Subscription sub =
                _baseUrl.getWebContent(path).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("request error : " + e);
                        getCurrentView().showErrorPage();
                    }

                    @Override
                    public void onNext(String s) {
                        // LogUtils.d("the request data length : " + s.length());
                        // LogUtils.d("data :" + s);
                        getCurrentView().getDataSuccess(s);
                    }
                });

        addSubscription(sub);
    }
}
