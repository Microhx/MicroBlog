package micro.com.microblog.mvc.presenter;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.utils.LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/9/14.
 */
public class JccPresenter extends BaseListPresenter<IBaseListUIView<Blog>> {
    /**
     * ///plus/list.php?tid=18&TotalResult=1652&PageNo=2
     *
     * @param isFirstTime
     * @param mBlogParser
     * @param mcurrentPage
     */

    @Override
    public void getToRequest(final boolean isFirstTime, final IBlogParser mBlogParser, int mcurrentPage) {
        Subscription sub = RetrofitUtils.
                getInstance(BaseURL.JCC_PATH, BaseURL.class).
                getJccArticle(18, mcurrentPage).flatMap(new Func1<String, Observable<List<Blog>>>() {
            @Override
            public Observable<List<Blog>> call(String s) {
                return Observable.just(mBlogParser.getBlogList(0, s));
            }
        }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Blog>>() {
                    @Override
                    public void call(List<Blog> blogs) {
                        getCurrentView().onFinishLoad(blogs, isFirstTime);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.d("Jcc error:" + throwable);
                        getCurrentView().onLoadError(isFirstTime);
                    }
                });

        addSubscription(sub);


    }
}
