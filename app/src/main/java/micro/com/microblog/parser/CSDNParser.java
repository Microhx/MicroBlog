package micro.com.microblog.parser;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.entity.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.HtmlContent;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/9/2.
 * <p/>
 * CSDN 网页解析器
 */
public class CSDNParser implements IBlogParser {
    private static final String TAG = CSDNParser.class.getSimpleName();

    private static CSDNParser mCSDNParser = new CSDNParser();

    private CSDNParser() {
    }

    public static CSDNParser getInstance() {
        return mCSDNParser;
    }

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);
        return parserBlog(htmlStr);
    }

    private List<Blog> parserBlog(String htmlStr) {
        List<Blog> mListBlog = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr) && htmlStr.contains("(")) {
            String jsonHtml = htmlStr.substring(htmlStr.indexOf("(") + 1, htmlStr.lastIndexOf(")") + 1);
            LogUtils.d("jsonHtml:" + jsonHtml);

            String newParseHtml = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonHtml);
                newParseHtml = jsonObject.getString("html");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(newParseHtml)) {
                LogUtils.d("enter the parseHtml");
                Document document = Jsoup.parse(newParseHtml);

                Elements geekList = document.getElementsByClass("geek_list");
                Blog _blog;
                if (null != geekList && !geekList.isEmpty()) {

                    for (Element element : geekList) {
                        _blog = new Blog();

                        String voteCount = element.getElementsByClass("left").first().getElementsByClass("count").first().text();
                        LogUtils.d("voteCount:" + voteCount);

                        String title = element.getElementsByClass("tracking-ad").get(2).select("a").text();  //获取标题
                        LogUtils.d("title:" + title);

                        String description = /*element.getElementsByClass("blog_list_c").get(0).text()*/ "";
                        //LogUtils.d("description:" + description);

                        String link = element.getElementsByClass("tracking-ad").select("a").attr("href");
                        LogUtils.d("link:" + link);

                        Element inLineElement = element.getElementsByClass("list-inline").get(0);

                        String publishTime = inLineElement.select("li").get(1).text();
                        LogUtils.d("publishTime:" + publishTime);

                        String author = inLineElement.select("li").get(2).select("a").text();
                        LogUtils.d("author:" + author);

                        String photo = element.getElementsByClass("right").first().
                                select("a").first().
                                select("img").
                                attr("src");

                        LogUtils.d("photo:" + photo);

                        _blog.title = title;
                        _blog.description = ComUtils.cutOffString(description, 120);
                        _blog.author = author;
                        _blog.publishTime = publishTime;
                        _blog.link = link;
                        _blog.voteCount = voteCount;
                        _blog.photo = photo;
                        _blog.articleType = ArticleType.CSDN;
                        _blog.hasCollect = DBDataUtils.userHasCollection(title);
                        _blog.hasRead = DBDataUtils.userHasReadArticle(title);

                        mListBlog.add(_blog);
                    }
                }
            }
        }

        return mListBlog;
    }


    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {
        List<Blog> blogList = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element rootElement = document.getElementsByClass("search-list-con").get(0);
            if (null == rootElement) return blogList;
            Elements parseList = rootElement.getElementsByClass("search-list");
            if (null == parseList || parseList.isEmpty()) return blogList;

            Blog blog;
            for (Element e : parseList) {
                blog = new Blog();

                String title = e.select("a").get(0).text();
                String publishTime = e.getElementsByClass("author-time").get(0).text();
                String desc = e.getElementsByClass("search-detail").get(0).text();
                String link = e.getElementsByClass("search-link").get(0).select("a").attr("href");

                LogUtils.d("title:" + title);
                LogUtils.d("publishTime :" + publishTime);
                LogUtils.d("desc:" + desc);
                LogUtils.d("link:" + link);

                blog.title = title;
                blog.publishTime = publishTime;
                blog.author = "";
                blog.description = desc;
                blog.link = link;
                blog.articleType = ArticleType.CSDN;
                blog.hasRead = DBDataUtils.userHasReadArticle(title);
                blog.hasCollect = DBDataUtils.userHasCollection(title);

                blogList.add(blog);
            }
        }

        return blogList;
    }


    @Override
    public HtmlContent getBlogContent(int type, String strHtml) {
        HtmlContent htmlContent = new HtmlContent();

        Document doc = Jsoup.parse(strHtml);

        Element rootElement = doc.getElementById("container");
        Element mainElement = rootElement.getElementById("body").getElementById("main");
        Element detailElement = mainElement.getElementById("article_details");
        detailElement.getElementsByClass("article_manage").remove();
        detailElement.getElementsByClass("link_categories").remove();
        detailElement.getElementsByClass("article_r").remove();
        detailElement.getElementsByClass("embody").remove();
        detailElement.getElementsByClass("category").remove();
        detailElement.getElementsByClass("bog_copyright").remove();
        detailElement.getElementsByClass("bdsharebuttonbox").remove();
        detailElement.getElementById("digg").remove();
        detailElement.getElementsByClass("tracking-ad").remove();
        detailElement.getElementsByClass("article_next_prev").remove();
        detailElement.getElementsByClass("similar_article").remove();

        //获取代码块-markdown
        Elements elements = detailElement.select("pre[class=prettyprint]");
        for (Element nodeElement : elements) {
            Elements childElements = nodeElement.getAllElements();
            for (Element child : childElements) {
                if ("code".equals(child.tagName())) {
                    //添加属性
                    child.tagName("pre");
                    child.attr("name", "code");
                    child.html(child.text());
                }
            }
        }

        //代码块 markdown
        Elements codeElements = detailElement.getElementsByClass("codeText");
        for (Element code : codeElements) {
            code.tagName("pre");
            code.attr("name", "code");
            code.html(code.text());
        }

        //代码块
        Elements javaElements = detailElement.select("pre[name=code]");
        for (Element codeElement : javaElements) {
            codeElement.attr("class", "brush: java; gutter: false;");
        }

        //图片缩放
        Elements imgElements = detailElement.getElementsByTag("img");
        for (Element img : imgElements) {
            img.attr("width", "auto%");
            img.attr("style", "max-width:100%");
            String imgSrc = img.attr("src");
            img.attr("onclick", "javascript:photo.showImg('" + imgSrc + "')");
            htmlContent.addPhoto(imgSrc);
        }

        htmlContent.mContent = FileUtils.getWebKitCssStyle(detailElement.html());

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
 * rootElement.getElementById("header").remove() ;
 * rootElement.getElementById("navigator").remove() ;
 * <p/>
 * Element bodyElement = rootElement.getElementById("body");
 * bodyElement.getElementById("main").getElementsByClass("ad_class").remove();
 * bodyElement.getElementById("main").getElementsByClass("J_adv").remove();
 * bodyElement.getElementById("main").getElementById("relate").remove();
 * bodyElement.getElementById("main").getElementsByClass("relate_t").remove();
 * bodyElement.getElementById("main").getElementsByClass("blog-ass-articl").remove();
 * bodyElement.getElementById("main").getElementById("ad_cen").remove();
 * bodyElement.getElementById("main").getElementsByClass("comment_class").remove();
 * bodyElement.getElementById("main").getElementsByClass("tag_list").remove();
 */