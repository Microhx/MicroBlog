package micro.com.microblog.base;

import android.view.View;

import micro.com.microblog.R;
import micro.com.microblog.controller.MultiLayerController;

/**
 * Created by guoli on 2016/8/28.
 */
public abstract class BaseMultiLayerFragment extends BaseFragment{

    private MultiLayerController multiLayerController ;

    @Override
    protected void initViewAndEvent() {
        multiLayerController = new MultiLayerController(getTargetView()) ;
    }

    public void showEmpty() {
        View empty = View.inflate(getContext(), R.layout.layout_empty,null) ;
        multiLayerController.changeCurrentView(empty);
    }

    public void showError() {
        View empty = View.inflate(getContext(), R.layout.layout_error,null) ;
        multiLayerController.changeCurrentView(empty);
    }


    protected abstract View getTargetView();
}
