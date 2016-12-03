package micro.com.microblog.manager;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * author : micro_hx
 * desc :  EventBus 封装
 * email: javainstalling@163.com
 * date : 2016/10/27 - 17:33
 */
public class RxBus {
    private static RxBus mRxBus;
    private Map<Object, List<Subject>> subjectMap = new HashMap<>();

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (null == mRxBus) {
            synchronized (RxBus.class) {
                if (null == mRxBus)
                    mRxBus = new RxBus();
            }
        }
        return mRxBus;
    }

    public <T> Observable<T> register(@NonNull Object object) {
        List<Subject> mList = subjectMap.get(object);
        if (null == mList) {
            mList = new ArrayList<>();
            subjectMap.put(object, mList);
        }

        Subject<T, T> subject = PublishSubject.create();
        mList.add(subject);

        return subject;
    }

    public void unRegister(@NonNull Object object) {
        if (null != object) {
            subjectMap.remove(object);
        }
    }

    public RxBus unRegister(@NonNull Object tag, @NonNull Observable<?> observable) {
        if (null == observable) return getInstance();

        List<Subject> subjects = subjectMap.get(tag);
        if (null != subjects && !subjects.isEmpty()) {
            subjectMap.remove(tag);
        }

        return getInstance();
    }


    public void post(@NonNull Object tag) {
        post(tag.getClass().getSimpleName(), tag);
    }


    public void post(@NonNull Object tag, @NonNull Object o) {
        List<Subject> subjects = subjectMap.get(tag);
        if (null != subjects && !subjects.isEmpty()) {
            for (Subject s : subjects) {
                s.onNext(o);
            }
        }
    }

}
