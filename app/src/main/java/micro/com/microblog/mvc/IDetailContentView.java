package micro.com.microblog.mvc;

/**
 * Created by guoli on 2016/9/16.
 */
public interface IDetailContentView {

    /**
     * 正在加载页面[也可以为广告页面]
     */
    void showLoadingPage() ;

    /**
     * 显示空页面
     */
    void showEmptyPage() ;

    /**
     * 显示错误页面
     */
    void showErrorPage() ;

    /**
     * 获取数据成功
     * @param msg
     */
    void getDataSuccess(String msg) ;
}
