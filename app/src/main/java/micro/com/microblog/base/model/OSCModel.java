package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.parser.JccParser;
import micro.com.microblog.parser.OSChinaParser;
import micro.com.microblog.utils.ComUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/6 - 16:53
 */
public class OSCModel implements BaseModel<Blog> {

    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, String type) {
        return RetrofitUtils.
                getInstance(BaseURL.OS_CHINA_PATH_AJAX, BaseURL.class).
                getOSChinaArticle(type, mCurrentPage).
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return OSChinaParser.getInstance().getBlogList(0, s);
                    }
                }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
