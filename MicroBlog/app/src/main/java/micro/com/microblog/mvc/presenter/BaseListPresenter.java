package micro.com.microblog.mvc.presenter;

import micro.com.microblog.parser.IBlogParser;

/**
 * Created by guoli on 2016/8/27.
 */
public abstract class BaseListPresenter<T> extends BasePresenter<T> {

    public abstract void getToRequest(boolean isFirstTime, IBlogParser mBlogParser, int mCurrentPageSize) ;

}
