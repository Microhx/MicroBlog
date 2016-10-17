package micro.com.microblog.parser;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.HtmlContent;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.JsoupUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/9/2.
 * <p/>
 * CSDN 网页解析器
 */
public class CSDNParser implements IBlogParser {
    private static final String TAG = CSDNParser.class.getSimpleName();

    private static CSDNParser mCSDNParser = new CSDNParser();

    private CSDNParser(){}

    public static CSDNParser getInstance() {
        return mCSDNParser ;
    }

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);
        return parserBlog(htmlStr);
    }

    private List<Blog> parserBlog(String htmlStr) {
        List<Blog> mListBlog = new ArrayList<>() ;
        if(!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Elements rootElements = document.getElementsByClass("blog_list") ;
            if(null == rootElements || rootElements.isEmpty()) return  mListBlog;

            Blog _blog;
            for(Element element : rootElements) {
                _blog = new Blog();
                String title = element.select("h3").text() ;  //获取标题
                LogUtils.d("title:"+title);

                String description = element.getElementsByClass("blog_list_c").get(0).text() ;
                LogUtils.d("description:"+description);

                String author = element.getElementsByClass("nickname").get(0).text() ;
                LogUtils.d("author:" + author);

                String publishTime = element.getElementsByClass("blog_list_b_r").get(0).select("label").text();
                LogUtils.d("publishTime:"+publishTime);

                String link = element.select("h3").select("a").attr("href");
                LogUtils.d("link:"+link);

                _blog.title = title ;
                _blog.description = description;
                _blog.author = author;
                _blog.publishTime = publishTime;
                _blog.link = link ;
                _blog.articleType = ArticleType.CSDN ;
                _blog.hasCollect = DBDataUtils.userHasCollection(title) ;
                _blog.hasRead = DBDataUtils.userHasReadArticle(title) ;

                mListBlog.add(_blog) ;
            }
        }
        return  mListBlog ;
    }

    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {
        List<Blog> blogList = new ArrayList<>() ;
        if(!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr) ;
            Element rootElement = document.getElementsByClass("search-list-con").get(0) ;
            if(null == rootElement) return blogList ;
            Elements parseList = rootElement.getElementsByClass("search-list");
            if(null == parseList || parseList.isEmpty()) return blogList ;

            Blog blog ;
            for(Element e : parseList) {
                blog = new Blog() ;

                String title = e.select("a").get(0).text() ;
                String publishTime = e.getElementsByClass("author-time").get(0).text() ;
                String desc = e.getElementsByClass("search-detail").get(0).text() ;
                String link = e.getElementsByClass("search-link").get(0).select("a").attr("href") ;

                LogUtils.d("title:" + title);
                LogUtils.d("publishTime :" + publishTime);
                LogUtils.d("desc:" + desc);
                LogUtils.d("link:" + link);

                blog.title = title;
                blog.publishTime = publishTime ;
                blog.author = "" ;
                blog.description = desc ;
                blog.link = link ;
                blog.articleType = ArticleType.CSDN ;

                blogList.add(blog);
            }
        }

        return blogList;
    }


    @Override
    public HtmlContent getBlogContent(int type, String strHtml) {
        HtmlContent htmlContent = new HtmlContent() ;

        Document doc = Jsoup.parse(strHtml) ;

        Element rootElement = doc.getElementById("container") ;
        Element mainElement = rootElement.getElementById("body").getElementById("main") ;
        Element detailElement = mainElement.getElementById("article_details") ;
        detailElement.getElementsByClass("article_manage").remove() ;
        detailElement.getElementsByClass("link_categories").remove() ;
        detailElement.getElementsByClass("article_r").remove() ;
        detailElement.getElementsByClass("embody").remove() ;
        detailElement.getElementsByClass("category").remove() ;
        detailElement.getElementsByClass("bog_copyright").remove() ;
        detailElement.getElementsByClass("bdsharebuttonbox").remove() ;
        detailElement.getElementById("digg").remove() ;
        detailElement.getElementsByClass("tracking-ad").remove() ;
        detailElement.getElementsByClass("article_next_prev").remove() ;
        detailElement.getElementsByClass("similar_article").remove() ;

        //获取代码块-markdown
        Elements elements = detailElement.select("pre[class=prettyprint]") ;
        for(Element nodeElement : elements) {
            Elements childElements = nodeElement.getAllElements() ;
            for(Element child : childElements) {
                if("code".equals(child.tagName())) {
                    //添加属性
                    child.tagName("pre");
                    child.attr("name","code");
                    child.html(child.text());
                }
            }
        }

        //代码块 markdown
        Elements codeElements = detailElement.getElementsByClass("codeText") ;
        for(Element code : codeElements) {
            code.tagName("pre");
            code.attr("name","code");
            code.html(code.text());
        }

        //代码块
        Elements javaElements = detailElement.select("pre[name=code]") ;
        for(Element codeElement : javaElements) {
            codeElement.attr("class", "brush: java; gutter: false;");
        }

        //图片缩放
        Elements imgElements = detailElement.getElementsByTag("img") ;
        for(Element img : imgElements) {
            img.attr("width","auto%") ;
            img.attr("style","max-width:100%") ;
            String imgSrc = img.attr("src");
            img.attr("onclick" , "javascript:photo.showImg('"+imgSrc+"')") ;
            htmlContent.addPhoto(imgSrc);
        }

        htmlContent.mContent = JsoupUtils.sHtmlFormat.replace(JsoupUtils.CONTENT_HOLDER,detailElement.html()) ;

        return htmlContent;
    }

    @Override
    public String getBlogContentUrl(String... strs) {
        return null;
    }

    @Override
    public String getUrlByType(int type, int page) {
        return null;
    }

    @Override
    public String getBlogBaseUrl() {
        return null;
    }
}

/**
 rootElement.getElementById("header").remove() ;
 rootElement.getElementById("navigator").remove() ;

 Element bodyElement = rootElement.getElementById("body");
 bodyElement.getElementById("main").getElementsByClass("ad_class").remove();
 bodyElement.getElementById("main").getElementsByClass("J_adv").remove();
 bodyElement.getElementById("main").getElementById("relate").remove();
 bodyElement.getElementById("main").getElementsByClass("relate_t").remove();
 bodyElement.getElementById("main").getElementsByClass("blog-ass-articl").remove();
 bodyElement.getElementById("main").getElementById("ad_cen").remove();
 bodyElement.getElementById("main").getElementsByClass("comment_class").remove();
 bodyElement.getElementById("main").getElementsByClass("tag_list").remove();

 */