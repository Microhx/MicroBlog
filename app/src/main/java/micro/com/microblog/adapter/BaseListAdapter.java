package micro.com.microblog.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.R;
import micro.com.microblog.mvc.view.CustomerViewHolder;
import micro.com.microblog.mvc.view.FooterView;

/**
 * Created by guoli on 2016/8/27.
 * 多头 多尾的 adapter
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<CustomerViewHolder> {

    protected List<T> mDatas ;
    //footer
    private FooterView mFooterView ;

    private static final int ITEM_NORMAL = 0x01 ;
    private static final int ITEM_FOOTER = 0x02 ;

    private FooterView.State mState = FooterView.State.NO_NULL ;

    public BaseListAdapter(){
        mDatas = new ArrayList<>() ;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_FOOTER) {
            return new CustomerViewHolder(mFooterView,ITEM_FOOTER) ;
        }

        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item_layout,parent,false) ;
        return new CustomerViewHolder(rootView,ITEM_NORMAL);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        if(isFooter(position)) {
            holder.setFooterState(mState);
        }else{
            setData(holder,mDatas.get(position),position) ;
        }
    }

    protected abstract void setData(CustomerViewHolder holder, T t, int position);

    private boolean isFooter(int position) {
        return position == mDatas.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        if(isFooter(position)) return ITEM_FOOTER ;
        return ITEM_NORMAL ;
    }

    @Override
    public int getItemCount() {
        return null == mFooterView ? mDatas.size() : 1 + mDatas.size();
    }

    public void setFooterView(FooterView mFooterView) {
        this.mFooterView = mFooterView;
    }

    public void setState(FooterView.State mState) {
        this.mState = mState;
    }

    public void updateData(boolean isFresh, List<T> newDatas, FooterView.State state) {
        mState = state ;
        if(isFresh) {
            mDatas.clear();
            mDatas.addAll(newDatas) ;
        }else{
            mDatas.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    public void clearAllData() {
        mDatas.clear();
        notifyDataSetChanged();
    }
}
