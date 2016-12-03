package micro.com.microblog.base.model;

import java.util.List;

import rx.Observable;

/**
 * author : micro_hx
 * desc :  全局搜索
 * email: javainstalling@163.com
 * date : 2016/11/29 - 15:15
 * interface :
 */
public interface SearchBlogModel<D> extends BaseModel<D> {

    /**
     * 获取InfoQ搜索接口
     *
     * @param keyWords     关键字
     * @param type         类型
     * @param mCurrentPage 当前页数
     * @return
     */
    Observable<List<D>> getInfoQSearchResultInfo(String keyWords, int type, int mCurrentPage);

    /**
     * ITEYE搜索接口
     *
     * @param keyWords
     * @param type
     * @param mCurrentPage
     * @return
     */
    Observable<List<D>> getITEyeSearchResultInfo(String keyWords, int type, int mCurrentPage);

    /**
     * CSDN搜缩接口
     *
     * @param keyWords
     * @param type
     * @param mCurrentPage
     * @return
     */
    Observable<List<D>> getCSDNSearchResultInfo(String keyWords, int type, int mCurrentPage);

    /**
     * 泡在网上的日子 搜索接口
     *
     * @param keyWords
     * @param type
     * @param mCurrentPage
     * @return
     */
    Observable<List<D>> getJccSearchResultInfo(String keyWords, int type, int mCurrentPage);

    /**
     * 开源中国 搜索接口
     *
     * @param keyWords
     * @param type
     * @param mCurrentPage
     * @return
     */
    Observable<List<D>> getOSCSearchResultInfo(String keyWords, int type, int mCurrentPage);


}
