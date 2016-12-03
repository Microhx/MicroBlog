package micro.com.microblog.base.presenter;

import android.util.Log;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.manager.RxSubscriber;
import micro.com.microblog.utils.LogUtils;
import rx.Subscription;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/29 - 14:54
 * interface :
 */
public class SearchPresenter extends BaseRequestPresenter.SearchPresenter {

    @Override
    public void toSearchKeyWords(int mCurrentPage, final boolean isFirstTime, String keyWords, int type) {
        if (type == 0) {  //搜索全部
            mRxManager.add(getInfoQSubscribe(mCurrentPage, isFirstTime, keyWords, type));
            mRxManager.add(getITEyeSubscribe(mCurrentPage,isFirstTime,keyWords,type));
            mRxManager.add(getCSDNSubscribe(mCurrentPage,isFirstTime,keyWords,type));
            mRxManager.add(getJCCSubscribe(mCurrentPage,isFirstTime,keyWords,type));
            mRxManager.add(getOSCSubscribe(mCurrentPage,isFirstTime,keyWords,type));
        }else if(type == 1) { //CSDN
            mRxManager.add(getCSDNSubscribe(mCurrentPage,isFirstTime,keyWords,type));
        }else if (type == 2) { //JCC
            mRxManager.add(getJCCSubscribe(mCurrentPage,isFirstTime,keyWords,type));
        }else if(type == 3) { //OSChina
            mRxManager.add(getOSCSubscribe(mCurrentPage,isFirstTime,keyWords,type));
        }else if(type == 4) { //ITEYE
            mRxManager.add(getITEyeSubscribe(mCurrentPage,isFirstTime,keyWords,type));
        }else if(type == 5) {
            mRxManager.add(getInfoQSubscribe(mCurrentPage, isFirstTime, keyWords, type));
        }
    }

    private Subscription getInfoQSubscribe(int mCurrentPage, final boolean isFirstTime, String keyWords, int type) {
        return mModel.getInfoQSearchResultInfo(keyWords, type, mCurrentPage).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> o) {
                        mView.returnBlogListData(o, isFirstTime, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showErrorTip(msg);
                    }
                });
    }

    private Subscription getITEyeSubscribe(int mCurrentPage , final boolean isFirstTime , String keyWords , int type) {
        return mModel.getITEyeSearchResultInfo(keyWords, type, mCurrentPage).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> o) {
                        mView.returnBlogListData(o, isFirstTime, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showErrorTip(msg);
                    }
                });
    }

    private Subscription getCSDNSubscribe(int mCurrentPage , final boolean isFirstTime , String keyWords , int type) {
        return mModel.getCSDNSearchResultInfo(keyWords, type, mCurrentPage).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> o) {
                        mView.returnBlogListData(o, isFirstTime, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showErrorTip(msg);
                    }
                });
    }


    private Subscription getJCCSubscribe(int mCurrentPage , final boolean isFirstTime , String keyWords , int type) {
        return mModel.getJccSearchResultInfo(keyWords, type, mCurrentPage).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> o) {
                        mView.returnBlogListData(o, isFirstTime, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        LogUtils.d("the error message : " + msg) ;
                        mView.showErrorTip(msg);
                    }
                });
    }

    private Subscription getOSCSubscribe(int mCurrentPage , final boolean isFirstTime , String keyWords , int type) {
        return mModel.getOSCSearchResultInfo(keyWords, type, mCurrentPage).
                subscribe(new RxSubscriber<List<Blog>>(mContext, false) {
                    @Override
                    public void _onNext(List<Blog> o) {
                        mView.returnBlogListData(o, isFirstTime, true);
                    }

                    @Override
                    public void _onError(String msg) {
                        mView.showErrorTip(msg);
                    }
                });
    }
}

