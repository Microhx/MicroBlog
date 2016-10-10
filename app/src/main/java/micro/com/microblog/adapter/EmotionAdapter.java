package micro.com.microblog.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.entity.EmotionEntity;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.EmotionUtils;
import micro.com.microblog.utils.UIUtils;

/**
 * Created by guoli on 2016/9/26.
 */
public class EmotionAdapter extends BaseAdapter {

    private List<EmotionEntity> mDatas;

    private int itemWidth;

    public EmotionAdapter(int index, int itemWidth) {
        super();
        mDatas = EmotionUtils.getEmotionEntityByIndex(index);
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        return ComUtils.CollectionIsNull(mDatas) ? 0 : mDatas.size() + 1;
    }

    @Override
    public EmotionEntity getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(itemWidth, itemWidth);
        imageView.setLayoutParams(params);

        if (position == mDatas.size()) { //是最后一个
            imageView.setImageResource(R.drawable.compose_emotion_delete);
        } else { //获取
            EmotionEntity entity = mDatas.get(position);
            imageView.setImageResource(entity.topSrc);

        }
        return imageView;
    }
}
