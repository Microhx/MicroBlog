package micro.com.microblog.parser;

import java.util.List;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.HtmlContent;

/**
 * Created by guoli on 2016/9/2.
 * HTML网页解析接口
 */
public interface IBlogParser {

    static final String FILE_SUFFIX = ".data";

    /***
     * 获取博客实体
     * @param type
     * @param htmlStr
     * @return
     */
    List<Blog> getBlogList(int type , String htmlStr) ;

    /**
     * 获取搜索中返回的实体
     * @param type
     * @param htmlStr
     * @return
     */
    List<Blog> getSearchBlogList(int type , String htmlStr) ;

    /**
     * 从网页源码中解析出博客正文
     * @param strHtml
     * @return
     */
    HtmlContent getBlogContent(int type, String strHtml);

    String getBlogContentUrl(String... strs);

    /**
     * 根据类型来获取请求链接，
     * @param type 网站类型，以及技术类型
     * @param page 页码
     */
    String getUrlByType(int type, int page);

    String getBlogBaseUrl();

}
