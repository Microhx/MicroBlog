package micro.com.microblog.base.model;

import java.util.List;

import micro.com.microblog.entity.Blog;
import rx.Observable;

/**
 * author : micro_hx
 * desc :  baseModel
 * email: javainstalling@163.com
 * date : 2016/10/28 - 13:24
 */
public interface BaseModel<D> {

    Observable<List<D>> getBlogList(int mCurrentPage, String type);
}
