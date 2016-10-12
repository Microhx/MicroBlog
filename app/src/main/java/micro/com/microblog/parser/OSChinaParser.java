package micro.com.microblog.parser;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.mvc.presenter.OSChinaPresenter;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.JsoupUtils;
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
            Document doc = Jsoup.parse(htmlStr);
            Element rootElement = doc.getElementById("topsOfRecommend");
            if (null == rootElement) return _blogList;

            Elements listElements = rootElement.getElementsByClass("item");
            if (null == listElements || listElements.isEmpty()) return _blogList;

            Blog b;
            for (Element el : listElements) {
                Element innerElement = el.getElementsByClass("box-aw").get(0);

                String title = innerElement.select("a").get(0).text();
                String link = el.select("a").get(0).attr("href");

                String desc = el.select("section").get(0).text();
                String author = el.select("footer").get(0).text();

                LogUtils.d("title:" + title);
                LogUtils.d("link:" + link);
                LogUtils.d("desc:" + desc);
                LogUtils.d("author:" + author);

                b = new Blog();
                b.title = title;
                b.publishTime = "";
                b.author = author;
                b.description = desc;
                b.link = link;
                b.articleType = ArticleType.OSCHINA;
                b.hasRead = DBDataUtils.userHasReadArticle(title);
                b.hasCollect = DBDataUtils.userHasCollection(title);


                _blogList.add(b);
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

                        blogList.add(blog);
                    }
                }
            }
        }

        return blogList;
    }

    @Override
    public String getBlogContent(int type, String strHtml) {
        Document document = Jsoup.parse(strHtml);
        Element bodyElement = document.getElementsByClass("blog-content").get(0);

        Element headElement = bodyElement.getElementsByClass("blog-heading").get(0);
        headElement.getElementsByClass("layout-right").remove();
        headElement.getElementsByClass("layout-column").remove();

        bodyElement.getElementsByClass("blog-opr").remove();
        bodyElement.getElementsByClass("operate").remove();

        Elements elements = bodyElement.select("pre");
        for (Element codeNode : elements) {
            codeNode.tagName("pre");
            codeNode.attr("name", "code");
            codeNode.html(codeNode.text());
        }

        Elements codeElements = bodyElement.select("pre[name=code]");
        for (Element codeNode : codeElements) {
            codeNode.attr("class", "brush:java;gutter:false");
        }

        Elements imgElements = bodyElement.getElementsByTag("img");
        for (Element img : imgElements) {
            img.attr("width", "auto");
            img.attr("style", "max-width:100%");
        }
        return JsoupUtils.sHtmlFormat.replace(JsoupUtils.CONTENT_HOLDER, bodyElement.html());
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
