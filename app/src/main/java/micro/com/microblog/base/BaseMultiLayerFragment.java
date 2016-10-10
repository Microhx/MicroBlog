package micro.com.microblog.base;

import android.view.View;
import android.widget.TextView;

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
        View error = View.inflate(getContext(), R.layout.layout_error,null) ;
        TextView tvError = (TextView) error.findViewById(R.id.tv_error);
        tvError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiLayerController.restoreView();

                onUserFirstTimeRequest();
            }
        });

        multiLayerController.changeCurrentView(error);
    }

    protected abstract View getTargetView();

}
