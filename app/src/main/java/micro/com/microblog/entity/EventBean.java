package micro.com.microblog.entity;

/**
 * Created by guoli on 2016/10/8.
 */
public class EventBean implements java.io.Serializable{

    /**
     * 文章类型
     */
    public ArticleType type ;

    /**
     * 类型信息
     */
    public String msg ;

    /**
     * 更新的位置
     */
    public int position ;

    /**
     * 是否收藏
     */
    public boolean isCollect ;

    /**
     * 是否已经阅读完成
     */
    public boolean isRead ;

}
