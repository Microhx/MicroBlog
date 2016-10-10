package micro.com.microblog.adapter;

import android.graphics.Interpolator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import micro.com.microblog.R;

/**
 * Created by guoli on 2016/9/27.
 */
public class BottomRecyclerAdapter extends RecyclerView.Adapter<BottomRecyclerAdapter.BottomHolder>{

    /**
     * 所有的收藏
     */
    private int mCount ;

    public BottomRecyclerAdapter(int mCount) {
        this.mCount = mCount ;
    }

    public RecyclerViewListener mListener ;


    public void setRecyclerViewListener(RecyclerViewListener listener) {
        this.mListener = listener ;
    }

    @Override
    public BottomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BottomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BottomHolder holder,final int position) {
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    mListener.onAdapterChange(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public class BottomHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;

        public BottomHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);

        }
    }


    public interface  RecyclerViewListener {
        void onAdapterChange(int position) ;

    }
}
