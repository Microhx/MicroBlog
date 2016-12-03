package micro.com.microblog.base.view;

import java.util.List;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/11/6 - 12:40
 */
public interface BaseRequestView<D> extends BaseView {

    //返回获取的数据
    void returnBlogListData(List<D> blogList, boolean isFirstTime, boolean isRefresh);

    //返回到顶部
    void srollToTop();

    /**
     * 加载页面显示为空
     */
    void showContentEmpty();

    /**
     * 加载页面错误
     */
    void showContentError();

    /**
     * 加载页面正在加载
     */
    void showContentLoading();

    /**
     * 恢复到目标页面
     */
    void restoreContentView();
}
