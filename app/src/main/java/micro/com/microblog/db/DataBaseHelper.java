package micro.com.microblog.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import micro.com.microblog.entity.Blog;
import micro.com.microblog.utils.LogUtils;

/**
 * Created by guoli on 2016/9/21.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "db_micro.db";
    private static final int DB_VERSION = 6;

    private static DataBaseHelper instance;

    private Dao<Blog, Integer> mBlogDao;


    public DataBaseHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public DataBaseHelper(Context context,
                          String databaseName,
                          SQLiteDatabase.CursorFactory factory,
                          int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Blog.class);

            LogUtils.d("create database success....");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.e("create database error,please check it now");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Blog.class, true);

            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtils.e("update database error...");
        }
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (DataBaseHelper.class) {
                if (null == instance) {
                    instance = new DataBaseHelper(context);
                }
            }
        }

        return instance;
    }

    public Dao<Blog, Integer> getBlogDao() {
        if (null == mBlogDao) {
            try {
                mBlogDao = getDao(Blog.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mBlogDao;
    }


}
