package micro.com.microblog.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import micro.com.microblog.R;
import micro.com.microblog.base.model.BaseModel;
import micro.com.microblog.base.presenter.BasePresenter;
import micro.com.microblog.controller.MultiLayerController;
import micro.com.microblog.manager.RxManager;
import micro.com.microblog.utils.ClassUtils;

/**
 * author : micro_hx
 * desc : 新的基类activity 使用MVP模式
 * email: javainstalling@163.com
 * date : 2016/10/28 - 13:18
 */
public abstract class NewBaseActivity<T extends BasePresenter, E extends BaseModel> extends SwipeBackActivity {

    public T mBasePresenter ;
    public E mBaseModel ;
    public Context mContext ;
    public RxManager mRxManager ;

    private MultiLayerController multiLayerController ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager() ;
        dobeforeSetContentView() ;

        setContentView(getLayoutResId());
        ButterKnife.bind(this);

        mContext = this ;
        mBasePresenter = ClassUtils.getT(this,0) ;
        mBaseModel = ClassUtils.getT(this,1) ;
        if(null != mBasePresenter) {
            mBasePresenter.mContext = this ;
        }

        initIntent(getIntent());

        initMultiLayer();

        initOtherElements();

        initPresenter() ;
        
        initViews();
    }

    protected void initIntent(Intent intent) {}

    private void initMultiLayer() {
        multiLayerController = new MultiLayerController(getTargetView()) ;
    }

    protected void initOtherElements() {}

    protected abstract void initViews();

    protected abstract void initPresenter();

    protected abstract View getTargetView() ;


    /**
     * 设置contentView之前所做的事
     */
    protected void dobeforeSetContentView(){}

    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getLayoutResId() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

        mRxManager.clear();
    }


    public void showContentLoading() {
        View loadingView = View.inflate(this,R.layout.layout_loading,null) ;
        multiLayerController.changeCurrentView(loadingView);
    }


    public void showContentEmpty() {
        View empty = View.inflate(this, R.layout.layout_empty,null) ;
        multiLayerController.changeCurrentView(empty);
    }

    public void showContentError() {
        View error = View.inflate(this, R.layout.layout_error,null) ;
        TextView tvError = (TextView) error.findViewById(R.id.tv_error);
        tvError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiLayerController.restoreView();
                retryLoading();
            }
        });

        multiLayerController.changeCurrentView(error);
    }

    public void restoreContentView() {
        multiLayerController.restoreView();
    }


    protected void retryLoading() {

    }

    protected void enterActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this,activity));
    }

    protected void enterActivity(Intent _intent) {
        startActivity(_intent);
    }

}
