package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.base.presenter.BaseRequestPresenter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.parser.InfoQParser;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/31 - 19:13
 */
public class InfoQImplModel implements BaseModel<Blog> {

    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, String type) {
        return RetrofitUtils.
                getInstance(BaseURL.INFOQ_PATH, BaseURL.class).
                getInfoQArticle(type, 12 * (mCurrentPage - 1)).
                flatMap(new Func1<String, Observable<List<Blog>>>() {
                    @Override
                    public Observable<List<Blog>> call(String s) {
                        return Observable.just(InfoQParser.getInstance().getBlogList(0, s));
                    }
                }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
