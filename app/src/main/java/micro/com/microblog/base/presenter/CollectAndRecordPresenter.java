package micro.com.microblog.base.presenter;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.manager.RxSubscriber;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/30 - 11:46
 * interface :
 */
public class CollectAndRecordPresenter extends BaseRequestPresenter.CollectAndRecordPresenter {

    @Override
    public void toGetCollectAndRecordList(int type, final int mCurrentPage, String otherInfo) {
        mRxManager.add(mModel.
                getCollectAndRecordList(type, mCurrentPage, otherInfo).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> blogList) {
                        mView.returnBlogListData(blogList, mCurrentPage == 0, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showErrorTip(msg);
                    }
                }));
    }
}
