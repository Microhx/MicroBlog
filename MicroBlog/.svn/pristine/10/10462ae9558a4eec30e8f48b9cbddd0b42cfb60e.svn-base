package micro.com.microblog.mvc;

import java.util.List;

/**
 * Created by guoli on 2016/8/24.
 * 上下拉滑动 接口
 */
public interface IBaseListUIView<T> extends IBaseUIView<T>{

    /**刷新数据*/
    void onRefresh() ;

    /**数据加载完成*/
    void onFinishLoad(List<T> mDatas,boolean isRefresh) ;

    /**显示等待框*/
    void showDialog() ;

    /**隐藏等待框*/
    void dismissDialog() ;
}
