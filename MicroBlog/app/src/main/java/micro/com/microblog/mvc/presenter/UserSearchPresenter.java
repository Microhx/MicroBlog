package micro.com.microblog.mvc.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.util.List;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.http.RetrofitUtils;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.mvc.IBaseListUIView;
import micro.com.microblog.mvc.ISearchListUIView;
import micro.com.microblog.parser.InfoQParser;
import micro.com.microblog.parser.ParserFactory;
import micro.com.microblog.utils.LogUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by guoli on 2016/10/8.
 */
public class UserSearchPresenter extends BasePresenter<ISearchListUIView> {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (UserSearchPresenter.class) {
                if(msg.what > 0) {
                    List<Blog> blogList = (List<Blog>) msg.obj;
                    //添加搜索的数据
                    getCurrentView().addSearchData(blogList) ;
                }else{
                    LogUtils.e("there are request errors");
                }
            }
        }
    };


    /**
     * //TODO　<a href='http://www.jcodecraeer.com'>泡在网上的日子</a> 搜索基本上进不去 此时打个tag
     * @param keyWords
     */
   /* public void searchTest(final String keyWords) {
        RetrofitUtils.
                getInstance(BaseURL.JCC_PATH, BaseURL.class).
                searchJCCContent(keyWords,"0").
                subscribeOn(Schedulers.io()).
                *//*observeOn(AndroidSchedulers.mainThread()).*//*
                subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                LogUtils.d("s length : " + s);
                LogUtils.d("s : " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                LogUtils.d("error : " + throwable);
            }
        }) ;
    }*/


    /**
     * 开始搜索
     *
     * @param keyWords
     */
    public void startToSearch(final String keyWords) {
        System.out.println("---keywords---" + keyWords);

        RetrofitUtils.
                getInstance(BaseURL.SEARCH_CSDN, BaseURL.class).
                searchCSDNContent(keyWords, "blog", "", "", null).
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.CSDN).getSearchBlogList(0, s);
                    }
                }).subscribeOn(Schedulers.io()).subscribe(new MyAction1(), new MyAction2());

        RetrofitUtils.
                getInstance(BaseURL.SEARCH_OSCHINA, BaseURL.class).
                searchOSChinaContent(keyWords, "blog", "pjukATYB").
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.OSCHINA).getSearchBlogList(0, s);
                    }
                }).subscribeOn(Schedulers.io()).subscribe(new MyAction1(), new MyAction2()) ;

        RetrofitUtils.
                getInstance(BaseURL.ITEYE_PATH, BaseURL.class).
                searchItEyeContent(keyWords, "blog").
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.ITEYE).getSearchBlogList(0, s);
                    }
                }).subscribeOn(Schedulers.io()).subscribe(new MyAction1(), new MyAction2()) ;

        //INFOQ search
        //step1: get the sst :
        RetrofitUtils.
                getSearchInstance("http://www.infoq.com/cn/", BaseURL.class).
                searchInfoQSST().
                subscribeOn(Schedulers.io()).
                subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        String sst = InfoQParser.getInstance().getSSTFormHTML(s);
                        LogUtils.d("sst:" + sst);
                        if(!TextUtils.isEmpty(sst)) {
                            searchInfoQ(keyWords,sst);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.d("error:" + throwable);
                        mHandler.sendEmptyMessage(-1) ;
                    }
                });
    }

    private void searchInfoQ(String keyWords, String sst) {
        RetrofitUtils.
                getSearchInstance(BaseURL.SEARCH_INFOQ, BaseURL.class).
                searchInfoQContent(keyWords, 1, "date", sst).
                map(new Func1<String, List<Blog>>() {
                    @Override
                    public List<Blog> call(String s) {
                        return ParserFactory.getParserInstance(ArticleType.INFOQ).getSearchBlogList(0, s);
                    }
                }).
                subscribeOn(Schedulers.io()).subscribe(new MyAction1(),new MyAction2());
    }

    private class MyAction1 implements Action1<List<Blog>> {
        @Override
        public void call(List<Blog> blogs) {
            Message msg = Message.obtain();
            msg.obj = blogs;
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }

    private class MyAction2 implements Action1<Throwable> {
        @Override
        public void call(Throwable throwable) {
            LogUtils.e("request error:" + throwable);
            mHandler.sendEmptyMessage(-1);
        }
    }

}
