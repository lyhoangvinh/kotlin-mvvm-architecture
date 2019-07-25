package com.lyhoangvinh.simple.ui.widget.recycleview;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lyhoangvinh.simple.utils.NetworkUtils;


public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int lastVisibleItem;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;
    private Context mContext;

    public EndlessRecyclerOnScrollListener(Context context, LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.mContext = context;
    }

    public void resetEndLess() {
        previousTotal = 0;
        loading = true;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (NetworkUtils.INSTANCE.netWorkCheck(mContext)) {
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
            lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();


            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            /*if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {*/
            else if (/*!loading && */(totalItemCount <= lastVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }
        } else {
            //Toast.makeText(mContext,mContext.getString(R.string.msg_network_error),Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void onLoadMore(int current_page);
}
