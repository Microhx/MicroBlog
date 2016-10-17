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
    String INFOQ_PATH = "http://www.infoq.com/cn/mobile/";

    //CSDN网址
    String CSDN_PATH = "http://blog.csdn.net/type/";

    //ITEYE网址
    String ITEYE_PATH = "http://www.iteye.com/";

    //泡在网上的日子[移动版块]
    String JCC_PATH = "http://www.jcodecraeer.com/";

    String OS_CHINA_PATH = "https://www.oschina.net/";

    //CSDN搜索区
    String SEARCH_CSDN = "http://so.csdn.net/so/search/";

    //OSCHINA搜索区
    String SEARCH_OSCHINA = "https://www.oschina.net/";

    //INFOQ搜索区
    String SEARCH_INFOQ = "http://www.infoq.com/";


    /**
     * 获取CSDN文章链接
     *
     * @param page
     * @return
     */
    @GET("index.html")
    Observable<String> getCSDNArticle(@Query("page") int page);

    /**
     * ITeye文章链接
     *
     * @param page
     * @return
     */
    @GET("blogs")
    Observable<String> getITEyeArticle(@Query("page") int page);

    /**
     * Info文章链接
     *
     * @param id
     * @return
     */
    @GET("articles/{id}")
    Observable<String> getInfoQArticle(@Path("id") int id);

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
    Observable<String> searchJCCContent(@Query("q") String keyword,
                                        @Query("kwtype") String type);

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
