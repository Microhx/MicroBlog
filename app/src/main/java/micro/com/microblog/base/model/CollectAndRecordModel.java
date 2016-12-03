package micro.com.microblog.base.model;

import java.util.List;

import rx.Observable;

/**
 * author : micro_hx
 * desc : 收藏阅读记录Model
 * email: javainstalling@163.com
 * date : 2016/11/30 - 11:41
 * interface :
 */
public interface CollectAndRecordModel<D> extends BaseModel<D>{

    /**
     * @param type         类型值
     * @param mCurrentPage 当前page值
     * @param otherInfos   其它信息
     * @return
     */
    Observable<List<D>> getCollectAndRecordList(int type, int mCurrentPage, String otherInfos);

}
