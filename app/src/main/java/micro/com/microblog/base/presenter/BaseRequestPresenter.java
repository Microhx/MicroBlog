package micro.com.microblog.base.presenter;

import micro.com.microblog.base.view.BaseRequestView;
import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.base.model.CollectAndRecordModel;
import micro.com.microblog.base.model.SearchBlogModel;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 17:20
 */
public class BaseRequestPresenter {

    public static abstract class Presenter extends BasePresenter<BaseModel, BaseRequestView> {

        public abstract void loadingRequest(int mCurrentPage, boolean isFirstTime, boolean isRefresh, String type);
    }


    //用户搜索接口
    public static abstract class SearchPresenter extends BasePresenter<SearchBlogModel, BaseRequestView> {
        public abstract void toSearchKeyWords(int mCurrentPage , boolean isFirstTime , String keyWords , int type) ;
    }

    public static abstract class CollectAndRecordPresenter extends BasePresenter<CollectAndRecordModel,BaseRequestView> {
        public abstract void toGetCollectAndRecordList(int type , int mCurrentPage , String otherInfo) ;
    }




}
