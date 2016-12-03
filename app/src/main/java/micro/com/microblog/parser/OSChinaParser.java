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
 * Created by guoli on 2016/9/14.
 */
public class OSChinaParser implements IBlogParser {

    private static final String TAG = OSChinaParser.class.getSimpleName();

    private static OSChinaParser mParser;

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);

        return parseData(htmlStr);
    }

    private List<Blog> parseData(String htmlStr) {
        List<Blog> _blogList = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Elements rootElements = document.getElementsByClass("item");
            if (null != rootElements && !rootElements.isEmpty()) {
                Blog blog;
                for (Element e : rootElements) {
                    blog = new Blog();

                    String photo = e.getElementsByClass("box-fl").get(0).select("img").attr("data-delay");

                    Element target = e.getElementsByClass("box-aw").get(0);
                    String title = target.select("a").get(0).text();
                    String link = target.select("a").get(0).attr("href");
                    String desc = target.select("section").get(0).text();
                    Element footerElement = target.select("footer").get(0);
                    String publishTime = footerElement.select("span").get(0).text();
                    String author = footerElement.select("span").get(2).text();

                    LogUtils.d("title:" + title);
                    LogUtils.d("link:" + link);
                    LogUtils.d("desc:" + desc);
                    LogUtils.d("publishTime:" + publishTime);
                    LogUtils.d("author:" + author);
                    LogUtils.d("photo:" + photo);

                    blog.title = title;
                    blog.link = link;
                    blog.description = ComUtils.cutOffString(desc, 120);
                    blog.publishTime = publishTime;
                    blog.author = author;
                    blog.articleType = ArticleType.OSCHINA;
                    blog.photo = photo;
                    blog.hasRead = DBDataUtils.userHasReadArticle(title);
                    blog.hasCollect = DBDataUtils.userHasCollection(title);

                    _blogList.add(blog);
                }
            }
        }

        return _blogList;
    }

    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {

        List<Blog> blogList = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element rootElement = document.getElementById("SearchResults");
            if (null != rootElement) {
                Element resultElement = rootElement.getElementById("results");
                Elements targetElements = resultElement.getElementsByClass("obj_type_3");

                Blog blog;
                if (null != targetElements && !targetElements.isEmpty()) {
                    for (Element e : targetElements) {
                        blog = new Blog();
                        String title = e.select("h3").get(0).select("a").get(0).text();
                        String link = e.getElementsByClass("url").get(0).text();
                        String desc = e.getElementsByClass("outline").get(0).text();
                        String publishTime = e.getElementsByClass("date").get(0).text();
                        String author = e.getElementsByClass("date").get(0).select("a").get(0).text().replace("@", "");

                        LogUtils.d("title:" + title);
                        LogUtils.d("link:" + link);
                        LogUtils.d("desc:" + desc);
                        LogUtils.d("publishTime:" + publishTime);
                        LogUtils.d("author:" + author);

                        blog.title = title;
                        blog.link = link;
                        blog.description = desc;
                        blog.publishTime = publishTime;
                        blog.author = author;
                        blog.articleType = ArticleType.OSCHINA;

                        //blog.hasRead = DBDataUtils.userHasReadArticle(title);
                        //blog.hasCollect = DBDataUtils.userHasCollection(title);

                        blogList.add(blog);
                    }
                }
            }
        }

        return blogList;
    }


    @Override
    public HtmlContent getBlogContent(int type, String strHtml) {
        HtmlContent htmlContent = new HtmlContent();

        Document document = Jsoup.parse(strHtml);
        Element bodyElement = document.getElementsByClass("blog-content").get(0);

        Element headElement = bodyElement.getElementsByClass("blog-heading").get(0);
        headElement.getElementsByClass("layout-right").remove();
        headElement.getElementsByClass("layout-column").remove();

        bodyElement.getElementsByClass("blog-opr").remove();
        bodyElement.getElementsByClass("operate").remove();
        bodyElement.getElementsByClass("reward-list").remove();

        Elements imgElements = bodyElement.getElementsByTag("img");
        for (Element img : imgElements) {
            img.attr("width", "auto");
            img.attr("style", "max-width:96%");

            String imgSrc = img.attr("src");
            img.attr("onclick", "javascript:photo.showImg('" + imgSrc + "')");
            htmlContent.addPhoto(imgSrc);
        }

        //存在TextArea时，需要将textArea中的打开
        Elements textAreaElements = bodyElement.getElementsByClass("noshow_content");

        if (null != textAreaElements && !textAreaElements.isEmpty()) {
            textAreaElements.get(0).attr("style", "width:100%;height:700px");
        }

        htmlContent.mContent = FileUtils.getWebKitCssStyle(bodyElement.html());
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

    public static IBlogParser getInstance() {
        if (null == mParser) {
            synchronized (OSChinaParser.class) {
                if (null == mParser) {
                    mParser = new OSChinaParser();
                }
            }
        }
        return mParser;
    }
}
