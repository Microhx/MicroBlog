package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.parser.CSDNParser;
import micro.com.microblog.parser.ITeyeParser;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/6 - 15:52
 */
public class CSDNImplModel implements BaseModel<Blog> {
    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, final String type) {
        LogUtils.d("----type----:" + type + "----" + mCurrentPage);

        return RetrofitUtils.
                getInstance(BaseURL.GEEK_CSDN_PATH, BaseURL.class).
                getCSDNArticle(type,
                        "jQuery20307511375457787082_1479717658917",
                        "",
                        mCurrentPage == 1 ? "-" : mCurrentPage * 20 + "",
                        "20",
                        "category",
                        "1479717786437").
                flatMap(new Func1<String, Observable<List<Blog>>>() {
                    @Override
                    public Observable<List<Blog>> call(String s) {
                        return Observable.just(CSDNParser.getInstance().getBlogList(0, s));
                    }
                }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
