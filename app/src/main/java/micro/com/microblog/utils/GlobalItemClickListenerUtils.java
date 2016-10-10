package micro.com.microblog.utils;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;

import micro.com.microblog.adapter.EmotionAdapter;
import micro.com.microblog.entity.EmotionEntity;

/**
 * Created by guoli on 2016/9/27.
 *
 * 标签点击全局相应类
 */
public class GlobalItemClickListenerUtils {

    private EditText mEditText ;

    private static GlobalItemClickListenerUtils mUtils ;

    private GlobalItemClickListenerUtils(){}

    public synchronized static GlobalItemClickListenerUtils getInstance() {
        if(null == mUtils ) {
            synchronized (GlobalItemClickListenerUtils.class) {
                if(null == mUtils) {
                    mUtils = new GlobalItemClickListenerUtils();
                }
            }
        }
        return mUtils ;
    }

    public void bindEditText(EditText editText) {
        if(null != mEditText) {
            LogUtils.d("the editText has been binded...");
        }
        this.mEditText = editText ;
    }


    public AbsListView.OnItemClickListener getItemClickListener() {
        return new AbsListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Adapter targetAdapter = parent.getAdapter();
            if(targetAdapter instanceof EmotionAdapter) {
                if(position == targetAdapter.getCount() - 1) { //最后一个为删除键
                    if(null != mEditText) {
                        // 如果点击了最后一个回退按钮,则调用删除键事件
                        mEditText.dispatchKeyEvent(new KeyEvent(
                                KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                    }
                }else {
                    EmotionEntity entity = (EmotionEntity) targetAdapter.getItem(position);
                    String topicName = entity.topicName ;

                    System.out.println("---the topicName---" + topicName);

                    int wordsLocation = mEditText.getSelectionStart();
                    StringBuilder sb = new StringBuilder(mEditText.getText().toString()) ;
                    sb.insert(wordsLocation,topicName) ;

                    if(null != mEditText) {
                        mEditText.setText(ComUtils.getSpannableString(EmotionUtils.CLASSIC_EMOTION_TYPE,mEditText,sb.toString()));
                    }


                    System.out.println("---the editText value ---" + mEditText.getText().toString());
                    //最后移动光标至最后
                    mEditText.setSelection(mEditText.getText().length());
                }
            }





            }
        } ;
    }





}
