package micro.com.microblog.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoli on 2016/10/13.
 * <p/>
 * Html内容实体类
 */
public class HtmlContent {

    //文章内容
    public String mContent;

    //文中所有的图片地址集合
    public ArrayList<String> photoList = new ArrayList<>();

    public void addPhoto(String photo) {
        photoList.add(photo);
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public int checkLocation(String imgPath) {
        int index = 0;
        int len = photoList.size();
        for (int i = 0; i < len; i++) {
            if (imgPath.equals(photoList.get(i))) {
                index = i;
                break;
            }
        }

        return index;
    }

}
