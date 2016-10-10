package micro.com.microblog.mvc.presenter;

import android.text.style.SubscriptSpan;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by guoli on 2016/8/27.
 */
public class BasePresenter<T> {

    T mView ;

    /**
     * 绑定指定的view
     * @param mView
     */
    public void attachView(T mView) {
        this.mView = mView ;
    }

    /**
     * 解除绑定
     */
    public void onDetach(){
        mView = null ;

        clearAllSub();
    }

    private void clearAllSub() {
        for(Subscription sub : subscriptionList) {
            if(!sub.isUnsubscribed()) {
                sub.unsubscribe();
            }
        }
        subscriptionList.clear();
    }

    public T getCurrentView(){
        return mView ;
    }

    private List<Subscription> subscriptionList = new ArrayList<>();

    protected void addSubscription(Subscription sub) {
        subscriptionList.add(sub) ;
    }



}
