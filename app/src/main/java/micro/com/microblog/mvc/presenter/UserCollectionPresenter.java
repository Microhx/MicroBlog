package micro.com.microblog.mvc.presenter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.UIUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/9/28.
 * <p>
 * 用户收藏presenter
 */
public class UserCollectionPresenter extends BaseListPresenter<IBaseListUIView<Blog>> {

    @Override
    public void getToRequest(final boolean isFirstTime, IBlogParser mBlogParser, final int mCurrentPageSize) {

        /*Subscription sub = */
        Observable.create(new Observable.OnSubscribe<List<Blog>>() {
            @Override
            public void call(Subscriber<? super List<Blog>> subscriber) {
                List<Blog> blogList = null;
                try {
                    blogList = DBDataUtils.getUserCollectBlog(ComUtils.PAGE_SIZE, ComUtils.PAGE_SIZE * (mCurrentPageSize - 1));

                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(blogList);
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
                        getCurrentView().onFinishLoad(null, isFirstTime);
                    }
                });
    }
}
