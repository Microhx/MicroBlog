package micro.com.microblog.mvc.presenter;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.mvc.presenter.BaseListPresenter;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.parser.ITeyeParser;
import micro.com.microblog.utils.LogUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/9/13.
 */
public class ITeyePresenter extends BaseListPresenter<IBaseListUIView<Blog>> {

    @Override
    public void getToRequest(final boolean isFirstTime, final IBlogParser mBlogParser, int mcurrentPage) {

       Subscription sub =  RetrofitUtils.
                getInstance(BaseURL.ITEYE_PATH,BaseURL.class).
                getITEyeArticle(mcurrentPage).
                flatMap(new Func1<String, Observable<List<Blog>>>(){
                    @Override
                    public Observable<List<Blog>> call(String s) {
                        return Observable.just(mBlogParser.getBlogList(0,s));
                    }
                }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<Blog>>(){
            @Override
            public void call(List<Blog> blogs) {
                getCurrentView().onFinishLoad(blogs,isFirstTime);
            }
        } , new Action1<Throwable>(){
            @Override
            public void call(Throwable throwable) {
                LogUtils.d("itEyeError: " + throwable);
                getCurrentView().onLoadError(isFirstTime);
            }
        }) ;

        addSubscription(sub);
    }
}
