package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.entity.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.manager.RxSchedulers;
import micro.com.microblog.parser.ParserFactory;
import micro.com.microblog.utils.LogUtils;
import rx.Observable;
import rx.functions.Func1;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/29 - 16:01
 * interface :
 */
public class SearchBlogModeImpl implements SearchBlogModel<Blog> {

    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, String type) {
        return null;
    }

    //---------------------------------------------search content----------------------------------------

    /**
     *
     */
    @Override
    public Observable<List<Blog>> getInfoQSearchResultInfo(final String keyWords, int type, final int mCurrentPage) {
       /* return RetrofitUtils.
                getSearchInstance("http://www.infoq.com/cn/", BaseURL.class).
                searchInfoQSST().
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String sst) {
                        return RetrofitUtils.
                                getSearchInstance(BaseURL.SEARCH_INFOQ, BaseURL.class).
                                searchInfoQContent(keyWords, mCurrentPage, "date", sst).
                                map(new Func1<String, List<Blog>>() {
                                    @Override
                                    public List<Blog> call(String s) {
                                        return ParserFactory.getParserInstance(ArticleType.INFOQ).getSearchBlogList(0, s);
                                    }
                                });
                    }
                }) ;*/

        return Observable.empty();
    }

    @Override
    public Observable<List<Blog>> getITEyeSearchResultInfo(String keyWords, int type, int mCurrentPage) {
        return RetrofitUtils.
                getInstance(BaseURL.ITEYE_PATH, BaseURL.class).
                searchItEyeContent(keyWords, "blog").
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.ITEYE).getSearchBlogList(0, s);
                    }
                }).compose(RxSchedulers.<List<Blog>>io_main());
    }

    @Override
    public Observable<List<Blog>> getCSDNSearchResultInfo(String keyWords, int type, int mCurrentPage) {

        return RetrofitUtils.
                getInstance(BaseURL.SEARCH_CSDN, BaseURL.class).
                searchCSDNContent(keyWords, "blog", "", "", null).
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.CSDN).getSearchBlogList(0, s);
                    }
                }).compose(RxSchedulers.<List<Blog>>io_main());
    }

    @Override
    public Observable<List<Blog>> getJccSearchResultInfo(String keyWords, int type, int mCurrentPage) {

        return RetrofitUtils.
                getInstance(BaseURL.JCC_PATH, BaseURL.class).
                searchJCCContent(keyWords, "titlekeyword", "", "", "10", "0", String.valueOf(mCurrentPage)).
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        LogUtils.d("jcc search type value : " + s);

                        return ParserFactory.getParserInstance(ArticleType.PAOWANG).getSearchBlogList(0, s);
                    }
                }).compose(RxSchedulers.<List<Blog>>io_main());
    }

    @Override
    public Observable<List<Blog>> getOSCSearchResultInfo(String keyWords, int type, int mCurrentPage) {
        return RetrofitUtils.
                getInstance(BaseURL.SEARCH_OSCHINA, BaseURL.class).
                searchOSChinaContent(keyWords, "blog", "pjukATYB").
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.OSCHINA).getSearchBlogList(0, s);
                    }
                }).compose(RxSchedulers.<List<Blog>>io_main());
    }
}
