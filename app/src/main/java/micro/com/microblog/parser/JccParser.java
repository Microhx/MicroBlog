package micro.com.microblog.parser;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.entity.ArticleType;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.HtmlContent;
import micro.com.microblog.http.url.BaseURL;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.DBDataUtils;
import micro.com.microblog.utils.FileUtils;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/9/14.
 */
public class JccParser implements IBlogParser {

    private static final String TAG = JccParser.class.getSimpleName();

    private static JccParser mJccParser;

    @Override
    public List<Blog> getBlogList(int type, String htmlStr) {
        FileUtils.saveFile(TAG + FILE_SUFFIX, htmlStr);

        return parseData(htmlStr);
    }

    @Override
    public List<Blog> getSearchBlogList(int type, String htmlStr) {
        return  parseData(htmlStr);
    }

    private List<Blog> parseData(String htmlStr) {
        List<Blog> _BlogList = new ArrayList<>();

        if (!TextUtils.isEmpty(htmlStr)) {
            Document doc = Jsoup.parse(htmlStr);
            Element rootList = doc.getElementsByClass("archive-list").get(0);
            if (null == rootList) return _BlogList;

            Elements blogList = rootList.getElementsByClass("archive-item");
            if (null == blogList || blogList.isEmpty()) return _BlogList;

            Blog b;
            for (Element indexElement : blogList) {
                b = new Blog();

                Elements conversonElement = indexElement.getElementsByClass("covercon");
                if (null != conversonElement && null != conversonElement.first()) {
                    b.photo = "http://www.jcodecraeer.com" + conversonElement.first().select("img").attr("src");
                    LogUtils.d("photo:" + b.photo);
                }

                indexElement = indexElement.getElementsByClass("archive-text").first();

                String title = indexElement.select("h3").get(0).select("a").text();
                LogUtils.d("title:" + title);

                String link = indexElement.select("h3").get(0).select("a").attr("href");
                LogUtils.d("link:" + link);

                String desc = indexElement.select("p").get(0).text();
                LogUtils.d("desc:" + desc);

                String author = indexElement.getElementsByClass("list-user").select("a").select("strong").text();
                LogUtils.d("author:" + author);

                String publishTime = indexElement.getElementsByClass("archive-data").get(0).getElementsByClass("glyphicon-class").text();
                LogUtils.d("publishTime:" + publishTime);

                b.title = title;
                b.link = BaseURL.JCC_PATH.substring(0, BaseURL.JCC_PATH.length() - 1) + link;
                b.description = ComUtils.cutOffString(desc, 120);
                b.author = author;
                b.publishTime = publishTime;
                b.articleType = ArticleType.PAOWANG;
                b.hasRead = DBDataUtils.userHasReadArticle(title);
                b.hasCollect = DBDataUtils.userHasCollection(title);

                _BlogList.add(b);
            }
        }

        return _BlogList;
    }

    @Override
    public HtmlContent getBlogContent(int type, String strHtml) {
        //strHtml = convertHtml(strHtml);
        HtmlContent htmlContent = new HtmlContent();

        Document document = Jsoup.parse(strHtml);
        Element contentElement = document.getElementsByClass("arc_body").get(0);
        contentElement.getElementsByClass("runtimead").remove();

        //图片缩放
        Elements imgElements = contentElement.getElementsByTag("img");
        for (Element img : imgElements) {
            img.attr("width", "auto%");
            img.attr("style", "max-width:100%");
            img.attr("src", BaseURL.JCC_PATH + img.attr("src"));

            String imgSrc = img.attr("src");
            img.attr("onclick", "javascript:photo.showImg('" + imgSrc + "')");
            htmlContent.addPhoto(imgSrc);
        }
        htmlContent.mContent = FileUtils.getWebKitCssStyle(contentElement.html());
        return htmlContent;
    }

    private String convertHtml(String strHtml) {
        try {
            return new String(strHtml.getBytes("UTF-8"), "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
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
        if (null == mJccParser) {
            synchronized (JccParser.class) {
                if (null == mJccParser) {
                    mJccParser = new JccParser();
                }
            }
        }
        return mJccParser;
    }
}
