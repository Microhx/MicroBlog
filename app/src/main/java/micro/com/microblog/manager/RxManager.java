package micro.com.microblog.manager;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * author : micro_hx
 * desc : 用于管理单个presenter的RxBus的事件和Rx相关的生命周期
 * email: javainstalling@163.com
 * date : 2016/10/27 - 18:03
 */

public class RxManager {

    public RxBus mRxBus = RxBus.getInstance();

    //管理RxBUs订阅
    private Map<String, Observable<?>> mObservableMap = new HashMap<>();

    //管理Observables和Subscribers订阅
    private CompositeSubscription mComposite = new CompositeSubscription();


    public <T> void on(String eventName, Action1<T> action1, Action1<Throwable> action2) {
        Observable<T> observable = mRxBus.register(eventName);
        mObservableMap.put(eventName, observable);
        mComposite.add(observable.
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(action1, action2));
    }


    public <T> void on(String eventName, Action1<T> action1) {
        Observable<T> observable = mRxBus.register(eventName);
        mObservableMap.put(eventName, observable);
        mComposite.add(observable.
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(action1));
    }


    public void add(Subscription sub) {
        mComposite.add(sub);
    }

    public void clear() {
        mComposite.unsubscribe();

        for (Map.Entry<String, Observable<?>> entry : mObservableMap.entrySet()) {
            mRxBus.unRegister(entry.getKey(), entry.getValue());
        }
    }

    public void post(Object tag, Object content) {
        mRxBus.post(tag, content);
    }

    public void unRegister(Object object) {
        mObservableMap.remove(object);
        mRxBus.unRegister(object);

    }


}
