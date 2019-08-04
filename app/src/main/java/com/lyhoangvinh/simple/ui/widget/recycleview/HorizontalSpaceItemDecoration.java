package com.lyhoangvinh.simple.ui.widget.recycleview;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpaceHeight;

    public HorizontalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition,@NonNull RecyclerView parent) {
        if (parent.getAdapter() != null && itemPosition != parent.getAdapter().getItemCount() - 1) {
            outRect.right = mVerticalSpaceHeight;
        }
    }
}
