package micro.com.microblog.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.entity.MsgBean;
import micro.com.microblog.utils.ComUtils;
import micro.com.microblog.utils.EmotionUtils;

/**
 * Created by guoli on 2016/9/21.
 */
public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.ChattingViewHolder> {
    private static final int SENDER = 0x01;
    private static final int RECEIVER = 0x02;

    private List<MsgBean> mDataList;

    public ChattingAdapter(List<MsgBean> msgBeanList) {
        this.mDataList = msgBeanList;

        //第一次获取的数据 需要反转
        Collections.reverse(mDataList);
    }

    @Override
    public ChattingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SENDER) {
            return new ChattingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_sender_item, parent, false));
        }

        return new ChattingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chatting_receiver_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ChattingViewHolder holder, int position) {
        MsgBean bean = mDataList.get(position);
        holder.tvMsg.setText(ComUtils.getSpannableString(EmotionUtils.CLASSIC_EMOTION_TYPE,null,bean.getContent()));
    }

    public void addMsgBean(MsgBean msgBean) {
        if (null != msgBean && !TextUtils.isEmpty(msgBean.getContent())) {
            mDataList.add(msgBean);
            notifyItemInserted(mDataList.size() - 1);
        }
    }

    public void addMsgBeanList(List<MsgBean> msgBeanList) {
        for (MsgBean msgBean : msgBeanList) {
            if (null != msgBean && !TextUtils.isEmpty(msgBean.getContent())) {
                mDataList.add(0,msgBean);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return null == mDataList ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position).getSenderId() == ComUtils.USER_ID) {
            return SENDER;
        }
        return RECEIVER;
    }

    public class ChattingViewHolder extends RecyclerView.ViewHolder {
        TextView tvMsg;

        public ChattingViewHolder(View itemView) {
            super(itemView);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }
}
