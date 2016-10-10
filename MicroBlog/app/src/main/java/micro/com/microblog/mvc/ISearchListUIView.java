package micro.com.microblog.mvc;

import java.util.List;

import micro.com.microblog.entity.Blog;

/**
 * Created by guoli on 2016/10/9.
 */
public interface ISearchListUIView  {

    void addSearchData(List<Blog> blogList);

    void showDialog() ;

    void hideDialog() ;
}
