package micro.com.microblog.http.url;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by guoli on 2016/9/5.
 */
public interface BaseURL {

    //INFOQ网址[目前全为移动端]
    String INFOQ_PATH = "http://www.infoq.com/cn/";

    //CSDN网址
    String CSDN_PATH = "http://geek.csdn.net/";

    //CSDN ajax请求算法
    String GEEK_CSDN_PATH = "http://geek.csdn.net/service/news/";

    String ITEYE_PATH_SHORT = "http://www.iteye.com";

    //ITEYE网址
    String ITEYE_PATH = ITEYE_PATH_SHORT + "/";

    //泡在网上的日子[移动版块]
    String JCC_PATH = "http://www.jcodecraeer.com/";

    //开源中国 [使用ajax请求]
    String OS_CHINA_PATH = "https://www.oschina.net/";

    //开源中国 [使用ajax请求]
    String OS_CHINA_PATH_AJAX = "https://www.oschina.net/action/ajax/";

    //CSDN搜索区
    String SEARCH_CSDN = "http://so.csdn.net/so/search/";

    //OSCHINA搜索区
    String SEARCH_OSCHINA = "https://www.oschina.net/";

    //INFOQ搜索区
    String SEARCH_INFOQ = "http://www.infoq.com/";


    /**
     * 获取CSDN文章链接
     *
     * @return
     * @
     */
    @GET("get_category_news_list")
    Observable<String> getCSDNArticle(@Query("category_id") String category_id,
                                      @Query("jsonpcallback") String jsonpcallback,
                                      @Query("username") String username,
                                      @Query("from") String from,
                                      @Query("size") String size,
                                      @Query("type") String type,
                                      @Query("_") String other);

    /**
     * ITeye文章链接
     *
     * @param page
     * @return
     */
    @GET("{type}")
    Observable<String> getITEyeArticle(@Path("type") String path, @Query("page") int page);

    /**
     * ITEye 专栏笔记 位于blog之下
     *
     * @param path
     * @return
     */
    @GET("{path}")
    Observable<String> getITEyeSubject(@Path("path") String path);


    /**
     * Info文章链接
     *
     * @param id
     * @return
     */
    @GET("{type}/articles/{id}")
    Observable<String> getInfoQArticle(@Path("type") String type, @Path("id") int id);

    /**
     * 泡在网上的日子 链接
     *
     * @param tid
     * @param pageNo
     * @return
     */
    @GET("plus/list.php")
    Observable<String> getJccArticle(@Query("tid") int tid, @Query("PageNo") int pageNo);

    /**
     * OSChina 链接  ?type=0&p=5#catalogs
     *
     * @return
     */
    @GET("get_more_recommend_blog")
    Observable<String> getOSChinaArticle(@Query("classification") String classification, @Query("p") int p);

    /**
     * 获取网页详细内容
     *
     * @return
     */
    @GET("{path}")
    Observable<String> getWebContent(@Path("path") String path);

    //http://so.csdn.net/so/search/s.do?q=Android&t=blog&o=&s=&l=null
    @GET("s.do")
    Observable<String> searchCSDNContent(@Query("q") String keyWords,
                                         @Query("t") String type,
                                         @Query("o") String o,
                                         @Query("s") String s,
                                         @Query("l") String l);

    //https://www.oschina.net/search?scope=blog&q=Android&fromerr=Nigvshhe
    @GET("search")
    Observable<String> searchOSChinaContent(@Query("q") String keyWords,
                                            @Query("scope") String type,
                                            @Query("fromerr") String formerr);


    //http://www.iteye.com/search?query=Android&type=blog
    @GET("search")
    Observable<String> searchItEyeContent(@Query("query") String keyWords,
                                          @Query("type") String type);


    //http://www.jcodecraeer.com/plus/search.php?kwtype=0&q=Java
    @GET("plus/search.php")
    Observable<String> searchJCCContent(@Query("keyword") String keyWord,
                                        @Query("searchtype") String searchType,
                                        @Query("orderby") String orderby,
                                        @Query("kwtype") String type,
                                        @Query("pagesize") String pagesize,
                                        @Query("typeid") String typeid,
                                        @Query("pageNo") String pageNo);

    //http://www.infoq.com/cn/search.action?queryString=java&page=1&searchOrder=&sst=o9OURhPD52ER0BUp
    //sst 在infoQ中为搜索验证时使用 不对的话 将会有HTTP 404 异常
    //o9OURhPD52ER0BUp
    @GET("search.action")
    Observable<String> searchInfoQContent(@Query("queryString") String keyWords,
                                          @Query("page") int page,
                                          @Query("searchOrder") String order,
                                          @Query("sst") String sst);

    //获取INFOQ的sst用于搜索
    @GET("mobile")
    Observable<String> searchInfoQSST();

    //文件下载
    @GET("{photoPath}")
    Observable<ResponseBody> downloadFile(@Path(value = "photoPath") String photoPath);


}
