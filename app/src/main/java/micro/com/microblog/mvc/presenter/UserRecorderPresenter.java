package micro.com.microblog.mvc.presenter;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.parser.IBlogParser;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DBDataUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/10/6.
 */
public class UserRecorderPresenter extends BaseListPresenter<IBaseListUIView<Blog>> {


    @Override
    public void getToRequest(final boolean isFirstTime, IBlogParser mBlogParser, final int mCurrentPage, final int currentTotal) {

        Observable.create(new Observable.OnSubscribe<List<Blog>>() {
            @Override
            public void call(Subscriber<? super List<Blog>> subscriber) {
                List<Blog> readBlogList = null;
                try {
                    readBlogList = DBDataUtils.getUserReadBlog(ComUtils.PAGE_SIZE, ComUtils.PAGE_SIZE * (mCurrentPage - 1));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(readBlogList);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<Blog>>() {
                              @Override
                              public void call(List<Blog> blogs) {
                                  getCurrentView().onFinishLoad(blogs, isFirstTime);
                              }
                          },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                getCurrentView().onFinishLoad(null, isFirstTime);
                            }
                        });
    }
}
