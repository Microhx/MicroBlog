package micro.com.microblog.parser;

import android.text.TextUtils;

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
 * Created by guoli on 2016/9/13.
 */
public class InfoQParser implements IBlogParser {

    private static final String TAG = InfoQParser.class.getSimpleName();

    private static InfoQParser mInfoParser;

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);

        return parseData(htmlStr);
    }

    private List<Blog> parseData(String htmlStr) {
        List<Blog> _listBlog = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document doc = Jsoup.parse(htmlStr);

            Elements type1Els = doc.getElementsByClass("news_type2");
            Blog blog;
            if (null != type1Els && !type1Els.isEmpty()) {
                for (Element t1 : type1Els) {
                    blog = new Blog();
                    String title = t1.select("h2").text();
                    String desc = t1.getElementsByTag("p").text();
                    String auAndTime = t1.getElementsByClass("author").text();
                    String link = "http://www.infoq.com" + t1.select("h2").select("a").attr("href");

                    Element picElement = t1.getElementsByClass("pic").first();
                    String img = null;
                    if (null != picElement) {
                        img = picElement.select("a").first().select("img").attr("src");
                    }

                    LogUtils.d("title:" + title);
                    LogUtils.d("link:" + link);
                    LogUtils.d("auAndTime:" + auAndTime);
                    LogUtils.d("desc:" + desc);
                    LogUtils.d("img:" + img);

                    blog.title = title;
                    blog.description = ComUtils.cutOffString(desc, 120);
                    blog.author = auAndTime;
                    blog.publishTime = "";
                    blog.link = link;
                    blog.articleType = ArticleType.INFOQ;
                    blog.hasRead = DBDataUtils.userHasReadArticle(title);
                    blog.hasCollect = DBDataUtils.userHasCollection(title);
                    blog.photo = img;

                    _listBlog.add(blog);
                }
            }
        }

        return _listBlog;
    }

    @Override
    public HtmlContent getBlogContent(int type, String strHtml) {
        HtmlContent htmlContent = new HtmlContent();

        //获取文档内容
        Document doc = Jsoup.parse(strHtml);
        Element detail = doc.getElementById("content");
        detail.getElementsByTag("h1").tagName("h2");

        Element titleElement = detail.getElementsByClass("title_canvas").get(0);
        titleElement.getElementsByIndexEquals(1).remove();

        detail.getElementsByClass("author_general").remove();
        detail.getElementsByClass("qcon_notice").remove();
        detail.getElementsByClass("sh_t").remove();
        detail.getElementsByClass("random_links").remove();
        detail.getElementsByClass("comment_here").remove();
        detail.getElementsByClass("comments").remove();
        detail.getElementsByClass("all_comments").remove();

        detail.getElementsByClass("article_page_right").remove();
        detail.getElementsByClass("related_sponsors").remove();
        detail.getElementsByClass("newsletter").remove();

        detail.getElementById("overlay_comments").remove();
        detail.getElementById("replyPopup").remove();
        detail.getElementById("editCommentPopup").remove();
        detail.getElementById("messagePopup").remove();
        detail.getElementById("contentRatingWidget").remove();
        detail.getElementById("noOfComments").remove();

       /* //处理代码块 -markdown
        Elements markdownElement = detail.select("pre");
        for (Element markdown : markdownElement) {
            Elements childs = markdown.getAllElements();
            for (Element child : childs) {
                if ("code".equals(child.tagName())) {
                    //添加属性 似的markdown的代码与原始代码一致
                    markdown.tagName("pre");
                    markdown.attr("name", "code");
                    markdown.html(child.text());
                }
            }
        }

        //处理代码块
        Elements codeElements = detail.select("pre[name=code]");
        for (Element codeNode : codeElements) {
            codeNode.attr("class", "brush:java;gutter : false ;");
        }
*/
        //缩放图片
        Elements imgNodes = detail.getElementsByTag("img");
        for (Element img : imgNodes) {
            img.attr("width", "auto");
            img.attr("style", "max-width:80%");
            img.attr("href", "#");
            String imgSrc = img.attr("src");
            img.attr("onclick", "javascript:photo.showImg('" + imgSrc + "')");
            htmlContent.addPhoto(imgSrc);
        }

        htmlContent.mContent = FileUtils.getWebKitCssStyle(detail.html());

        return htmlContent;
    }

    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {
        List<Blog> blogList = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element wrapperElement = document.getElementById("wrapper");
            Element contentElement = wrapperElement.getElementById("site").getElementById("content");

            Elements targetElements = contentElement.getElementsByClass("one_result");
            if (null == targetElements || targetElements.isEmpty()) {
                LogUtils.d("parse empty ");
                return blogList;
            }

            Blog blog;
            for (Element e : targetElements) {
                blog = new Blog();
                String link = e.select("h2").get(0).select("a").attr("href");
                String title = e.select("h2").get(0).select("a").text();
                String desc = e.select("p").get(0).text();

                LogUtils.d("link : " + link);
                LogUtils.d("title : " + title);
                LogUtils.d("desc :　" + desc);

                blog.link = link;
                blog.title = title;
                blog.description = desc;
                blog.publishTime = "";
                blog.author = "";
                blog.articleType = ArticleType.INFOQ;

                blogList.add(blog);
            }
        }

        return blogList;
    }

    /**
     * 获取搜索验证信息SST
     *
     * @param htmlStr
     * @return
     */
    public String getSSTFormHTML(String htmlStr) {
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element headerElement = document.getElementById("wrapper").getElementById("site").getElementById("header");
            if (null != headerElement) {
                return headerElement.getElementsByClass("search_cls").get(0).getElementById("sst").attr("value");
            }
        }
        return null;
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

    public static InfoQParser getInstance() {
        if (null == mInfoParser) {
            synchronized (InfoQParser.class) {
                if (null == mInfoParser) {
                    mInfoParser = new InfoQParser();
                }
            }
        }

        return mInfoParser;
    }
}
