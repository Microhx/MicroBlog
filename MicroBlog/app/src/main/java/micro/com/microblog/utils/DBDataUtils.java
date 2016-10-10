package micro.com.microblog.utils;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import micro.com.microblog.db.DataBaseHelper;
import micro.com.microblog.entity.Blog;
import micro.com.microblog.entity.MsgBean;
import micro.com.microblog.widget.PublicHeadLayout;

/**
 * Created by guoli on 2016/9/21.
 */
public class DBDataUtils {

    /**
     * 获取
     *
     * @param context
     * @param userId
     * @param maxLine
     * @return
     */
    public static List<MsgBean> getUserMsgBean(Context context, int userId, long maxLine, long offset) {
        List<MsgBean> _msgBeanList = new LinkedList<>();

        Dao<MsgBean, Integer> msgDao = DataBaseHelper.getInstance(context).getMsgDao();
        try {
            if (null != msgDao) {
                Where<MsgBean, Integer> where = msgDao.queryBuilder().limit(maxLine).offset(offset).orderBy("chat_time", false).where();
                where.eq("sender_id", userId);
                where.or();
                where.eq("receive_id", userId);

                _msgBeanList = where.query();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return _msgBeanList;
    }

    public static void addUserMsgBean(Context context, MsgBean msgBean) {
        Dao<MsgBean, Integer> msgDao = DataBaseHelper.getInstance(context).getMsgDao();
        try {
            msgDao.create(msgBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Blog> getUserReadBlog(long maxLine, long offset) {
        return getBlogOrderBy(maxLine, offset, 0);
    }

    public static List<Blog> getUserCollectBlog(long maxLine, long offset) {
        return getBlogOrderBy(maxLine, offset, 1);
    }

    private static List<Blog> getBlogOrderBy(long maxLine, long offset, int type) {
        List<Blog> _msgBeanList = new ArrayList<>();
        Dao<Blog, Integer> msgDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao();
        try {
            Where<Blog, Integer> where = msgDao.
                    queryBuilder().
                    limit(maxLine).
                    offset(offset).
                    orderBy("_id", false).
                    where().eq("type", type);

            if (null != msgDao) {
                _msgBeanList = where.query();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return _msgBeanList;
    }

    /**
     * 用户是否收藏本blog
     *
     * @return
     */
    public static boolean userHasCollection(String title) {
        return blogInDataBase(title, Blog.COLLECT_TYPE);
    }

    public static boolean blogInDataBase(String title, int type) {
        Dao<Blog, Integer> blogDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao();
        try {
            List<Blog> result = blogDao.
                    queryBuilder().
                    where().
                    eq("title", title).
                    and().
                    eq("type", type).
                    query();
            return null != result && !result.isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 添加本博客收藏
     *
     * @param blog
     */
    public static int addUserCollection(Blog blog) {
        int insertId = -1;
        Dao<Blog, Integer> blogDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao();
        try {
            insertId = blogDao.create(blog);
            LogUtils.d("add blog success : " + insertId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertId;
    }

    /**
     * 用户阅读记录
     *
     * @param mCurrentBlog
     */
    public static void addUserReadBlog(Blog mCurrentBlog) {
        int insertId = -1;
        Dao<Blog, Integer> blogDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao();
        try {
            boolean hasRecord = blogInDataBase(mCurrentBlog.getTitle(), Blog.READ_TYPE);
            if (!hasRecord) {
                insertId = blogDao.create(mCurrentBlog);
                LogUtils.d("add blog success : " + insertId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteUserCollection(Blog blog) {
        Dao<Blog, Integer> blogDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao();
        try {
            String str = blogDao.deleteBuilder().where().eq("title", blog.getTitle()).getStatement();
            blogDao.deleteBuilder().delete();

            LogUtils.d("delete success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户是否阅读
     *
     * @param title
     * @return
     */
    public static boolean userHasReadArticle(String title) {
        return blogInDataBase(title, Blog.READ_TYPE);
    }

    public static void deleteAllUserCollection() {
        deleteBlogData(Blog.COLLECT_TYPE);
    }

    public static void deleteAllUserRead() {
        deleteBlogData(Blog.READ_TYPE);
    }

    private static void deleteBlogData(int type) {
        Dao<Blog,Integer> blogDao = DataBaseHelper.getInstance(UIUtils.getAppContext()).getBlogDao() ;
        try {
            blogDao.deleteBuilder().where().gt("_id", 0).and().eq("type" ,type);
            blogDao.deleteBuilder().delete() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
