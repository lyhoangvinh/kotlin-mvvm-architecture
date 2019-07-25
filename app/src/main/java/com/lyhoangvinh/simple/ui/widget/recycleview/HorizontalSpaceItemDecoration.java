package com.lyhoangvinh.simple.ui.widget.recycleview;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpaceHeight;

    public HorizontalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (itemPosition != parent.getAdapter().getItemCount() - 1) {
            outRect.right = mVerticalSpaceHeight;
        }
    }

}
