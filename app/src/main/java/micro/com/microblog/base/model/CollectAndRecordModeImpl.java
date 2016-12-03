package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.Config;
import micro.com.microblog.utils.DBDataUtils;
import rx.Observable;
import rx.Subscriber;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/30 - 11:48
 * interface :
 */
public class CollectAndRecordModeImpl implements CollectAndRecordModel<Blog> {

    @Override
    public Observable<List<Blog>> getBlogList(int mCurrentPage, String type) {
        return null;
    }

    @Override
    public Observable<List<Blog>> getCollectAndRecordList(final int type, final int mCurrentPage, String otherInfos) {
        return Observable.create(new Observable.OnSubscribe<List<Blog>>() {
            @Override
            public void call(Subscriber<? super List<Blog>> subscriber) {
                List<Blog> _listBlog ;
                if(type == Blog.READ_TYPE) {
                    _listBlog = DBDataUtils.getUserReadBlog(Config.DB_PAGE_COUNT , mCurrentPage * Config.DB_PAGE_COUNT) ;
                }else {
                    _listBlog = DBDataUtils.getUserCollectBlog(Config.DB_PAGE_COUNT , mCurrentPage * Config.DB_PAGE_COUNT) ;
                }
                subscriber.onNext(_listBlog);
            }
        });
    }
}
