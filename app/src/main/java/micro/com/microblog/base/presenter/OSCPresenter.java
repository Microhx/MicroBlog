package micro.com.microblog.base.presenter;

import java.util.List;

import micro.com.microblog.base.presenter.BaseRequestPresenter;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.manager.RxSubscriber;
import micro.com.microblog.manager.RxViewSubscriber;
import micro.com.microblog.utils.Config;
import rx.functions.Action1;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/6 - 16:54
 */
public class OSCPresenter extends BaseRequestPresenter.Presenter {

    @Override
    public void onStart() {
        super.onStart();
        mRxManager.on(Config.SCROLL_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.srollToTop();
            }
        });
    }

    @Override
    public void loadingRequest(int mCurrentPage, final boolean isFirstTime, final boolean isRefresh, String type) {
        mRxManager.add(mModel.getBlogList(mCurrentPage, type).subscribe(new RxViewSubscriber<List<Blog>>(mView, isFirstTime) {
            @Override
            public void _onNext(List<Blog> blogList) {
                mView.returnBlogListData(blogList, isFirstTime, isRefresh);
            }

            @Override
            public void _onError(String msg) {
                mView.showErrorTip(msg);
            }

        }));
    }
}
