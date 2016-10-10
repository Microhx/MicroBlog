package micro.com.microblog.entity;

import android.graphics.Color;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import micro.com.microblog.adapter.ArticleType;

/**
 * Created by guoli on 2016/9/2.
 *
 * 博客实体类
 */

@DatabaseTable(tableName = "_blog")
public class Blog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 阅读记录
     */
    public static final int READ_TYPE = 0 ;
    /**
     * 收藏记录
     */
    public static final int COLLECT_TYPE = 1 ;


    @DatabaseField(generatedId = true,columnName = "_id")
    public int id; // id

    @DatabaseField(columnName = "title")
    public String title; // 标题

    @DatabaseField(columnName = "link")
    public String link; // 文章链接

    @DatabaseField(columnName = "publish_time")
    public String publishTime; // 博客发布时间

    @DatabaseField(columnName = "author")
    public String author ;

    @DatabaseField(columnName = "desc")
    public String description;// 文章摘要

    @DatabaseField(columnName = "content")
    public String content; // 文章内容

    @DatabaseField(columnName = "msg")
    public String msg; // 消息

    @DatabaseField(columnName = "article_type")
    public ArticleType articleType; // 博客类型，原创，翻译，转载

    /**
     * 收藏 1
     * 阅读 0
     */
    @DatabaseField(columnName = "type")
    public int type;

    @DatabaseField(columnName = "add_time")  //收藏时间
    public long addTime ;

    @DatabaseField(columnName = "read_time")  // 阅读时间
    public long readTime ;

    /**
     * 是否被阅读
     */
    public boolean hasRead ;

    /**
     * 是否被收藏
     */
    public boolean hasCollect ;


    public long getReadTime() {
        return readTime;
    }

    public void setReadTime(long readTime) {
        this.readTime = readTime;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "articleType=" + articleType +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
