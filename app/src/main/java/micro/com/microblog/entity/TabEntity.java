package micro.com.microblog.entity;

import android.text.TextUtils;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * author : micro_hx
 * desc :
 * email: javainstalling@163.com
 * date : 2016/10/28 - 15:16
 */
public class TabEntity implements CustomTabEntity {

    private int selectIcon;
    private int unSelectIcon;
    private String title;

    public TabEntity(int selectIcon, int unSelectIcon, String title) {
        this.selectIcon = selectIcon;
        this.title = title;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
