package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.parser.ITeyeParser;
import micro.com.microblog.parser.InfoQParser;
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
 * date : 2016/11/6 - 13:27
 */
public class ITEyeImplModel implements BaseModel<Blog> {

    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, final String type) {
        LogUtils.d("----type----:" + type + "----" + mCurrentPage);
        String baseUrl = BaseURL.ITEYE_PATH;
        String strType = type;
        if (ComUtils.getITeyeType(type) == 3) {
            baseUrl += "blogs/";
            strType = "subjects";
        }

        LogUtils.d("baseUrl:" + baseUrl);

        return RetrofitUtils.getInstance(baseUrl, BaseURL.class).
                getITEyeArticle(strType, mCurrentPage).
                flatMap(new Func1<String, Observable<List<Blog>>>() {
                    @Override
                    public Observable<List<Blog>> call(String s) {
                        return Observable.just(ITeyeParser.getInstance().getBlogList(ComUtils.getITeyeType(type), s));
                    }
                }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
