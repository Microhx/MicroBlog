package micro.com.microblog.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import micro.com.microblog.mvc.view.CustomerViewHolder;

/**
 * Created by guoli on 2016/8/28.
 */
public abstract class RecyclerListAdapter<T> extends RecyclerView.Adapter<CustomerViewHolder> {

    protected List<T> mDatas ;

    public RecyclerListAdapter() {
        mDatas = new ArrayList<>() ;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void updateData(List<T> newDatas) {
        mDatas.clear();
        mDatas.addAll(newDatas) ;
    }

    public void addNewData(List<T> mDatas) {
        mDatas.addAll(mDatas) ;
    }

    @Override
    public abstract CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) ;

    public abstract void onBindViewHolder(CustomerViewHolder holder, int position) ;
}
