package micro.com.microblog.parser;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import micro.com.microblog.adapter.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.JsoupUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/9/13.
 */
public class ITeyeParser implements IBlogParser {

    private static final String TAG = ITeyeParser.class.getSimpleName();

    private static ITeyeParser mITeyeParser;

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);

        return parseData(htmlStr);
    }

    private List<Blog> parseData(String htmlStr) {
        List<Blog> _listBlog = new ArrayList<>();
        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element blogs = document.getElementById("index_main");
            if (null == blogs) return _listBlog;

            Elements elements = blogs.getElementsByClass("content");
            if (null == elements || elements.isEmpty()) {
                LogUtils.d("parse elements is empty");
                return _listBlog;
            }

            Blog blog;
            for (Element element : elements) {
                blog = new Blog();

                String title = element.select("h3").select("a").text();
                String link = element.select("h3").select("a").get(1).attr("href");
                String desc = element.getElementsByIndexEquals(1).text();
                String author = element.getElementsByClass("blog_info").select("a").get(0).text();
                String publishTime = element.getElementsByClass("date").text();

                LogUtils.d("title:" + title);
                LogUtils.d("link:" + link);
                LogUtils.d("desc :" + desc);
                LogUtils.d("author : " + author);
                LogUtils.d("publishTime : " + publishTime);

                blog.publishTime = publishTime;
                blog.title = title;
                blog.link = link;
                blog.description = desc;
                blog.author = author;
                blog.articleType = ArticleType.ITEYE;
                blog.hasRead = DBDataUtils.userHasReadArticle(title);
                blog.hasCollect = DBDataUtils.userHasCollection(title);

                _listBlog.add(blog);
            }
        }
        return _listBlog;
    }


    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {
        List<Blog> blogList = new ArrayList<>();

        if (!TextUtils.isEmpty(htmlStr)) {
            Document document = Jsoup.parse(htmlStr);
            Element rootElement = document.getElementById("search_result");

            Elements targetElements = rootElement.getElementsByClass("topic");
            if (null == targetElements || targetElements.isEmpty()) {
                LogUtils.d("parser empty");
                return blogList;
            }

            Blog blog;
            for (Element e : targetElements) {
                blog = new Blog();

                Element contentElement = e.getElementsByClass("content").get(0);
                String link = contentElement.select("h4").get(0).select("a").get(0).attr("href");
                String title = contentElement.select("h4").get(0).select("a").get(0).text();

                String desc = contentElement.select("div").get(0).text();
                String author = contentElement.getElementsByClass("topic_info").get(0).select("a").text();
                String publishTime = ComUtils.getStandardTime(contentElement.getElementsByClass("topic_info").text());

                LogUtils.d("link : " + link);
                LogUtils.d("title : " + title);
                LogUtils.d("desc :　" + desc);
                LogUtils.d("author : " + author);
                LogUtils.d("publishTime : " + publishTime);

                blog.title = title;
                blog.link = link;
                blog.description = desc;
                blog.author = author;
                blog.publishTime = publishTime;
                blog.articleType = ArticleType.ITEYE ;

                blogList.add(blog);
            }
        }
        return blogList;
    }

    @Override
    public String getBlogContent(int type, String strHtml) {
        Document doc = Jsoup.parse(strHtml);

        Element mainElements = doc.getElementsByClass("blog_main").get(0);
        mainElements.getElementsByClass("blog_categories").remove();
        mainElements.getElementsByClass("news_tag").remove();
        mainElements.getElementsByClass("J_adv").remove();

        mainElements.getElementById("bottoms").remove();

        mainElements.getElementsByClass("blog_nav").remove();
        mainElements.getElementsByClass("blog_bottom").remove();
        mainElements.getElementsByClass("boutique-curr-box blog_comment").remove();
        mainElements.getElementsByClass("blog_comment").remove();

        // 处理代码块-markdown
        Elements elements = mainElements.getElementsByClass("dp-highlighter");
        for (Element codeNode : elements) {
            codeNode.tagName("pre");
            codeNode.attr("name", "code");
            codeNode.html(codeNode.text());//原始的源代码标签中，html直接就是源代码text
        }
        // 处理代码块
        Elements codeElements = mainElements.select("pre[name=code]");
        for (Element codeNode : codeElements) {
            codeNode.attr("class", "brush: java; gutter: false;");
        }

        // 缩放图片
        Elements elementImgs = mainElements.getElementsByTag("img");
        for (Element img : elementImgs) {
            img.attr("width", "auto");
            img.attr("style", "max-width:100%;");
        }


        return JsoupUtils.sHtmlFormat.replace(JsoupUtils.CONTENT_HOLDER, mainElements.html());
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
        if (null == mITeyeParser) {
            synchronized (ITeyeParser.class) {
                if (null == mITeyeParser) {
                    mITeyeParser = new ITeyeParser();
                }
            }
        }

        return mITeyeParser;
    }
}
