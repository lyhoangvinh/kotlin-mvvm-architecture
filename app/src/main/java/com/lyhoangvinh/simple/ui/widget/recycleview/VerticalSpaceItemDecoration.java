package com.lyhoangvinh.simple.ui.widget.recycleview;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpaceHeight;

    public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
        this.mVerticalSpaceHeight = mVerticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
        if (parent.getAdapter() != null && itemPosition != parent.getAdapter().getItemCount() - 1) {
            outRect.set(new Rect(mVerticalSpaceHeight, mVerticalSpaceHeight / 2, mVerticalSpaceHeight, mVerticalSpaceHeight/2));
        }
    }
}
