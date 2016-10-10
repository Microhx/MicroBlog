package micro.com.microblog;

import android.view.View;

import micro.com.microblog.controller.MultiLayerController;
import micro.com.microblog.mvc.IDetailContentView;

/**
 * Created by guoli on 2016/9/16.
 */
public abstract class BaseMultiLayerRequestActivity extends BaseActivity implements IDetailContentView {

    private MultiLayerController mMultiLayerController ;

    @Override
    protected void initViewsAndData() {
        mMultiLayerController = new MultiLayerController(getTargetView()) ;
    }

    @Override
    protected abstract int getContentLayoutId() ;

    protected abstract View getTargetView() ;

    @Override
    public void showLoadingPage() {
        View loadingPage = View.inflate(this,R.layout.layout_loading,null) ;
        mMultiLayerController.changeCurrentView(loadingPage);
    }

    @Override
    public void showEmptyPage() {
        View emptyPage = View.inflate(this,R.layout.layout_empty,null) ;
        mMultiLayerController.changeCurrentView(emptyPage);
    }

    @Override
    public void showErrorPage() {
        View errorPage = View.inflate(this,R.layout.layout_error,null) ;
        mMultiLayerController.changeCurrentView(errorPage);
    }

    @Override
    public void getDataSuccess(String msg) {}

    /**
     * 显示目标View
     */
    public void showTheTargetPage() {
        mMultiLayerController.restoreView();
    }
}
