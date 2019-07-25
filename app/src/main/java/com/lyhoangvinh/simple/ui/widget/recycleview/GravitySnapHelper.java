package com.lyhoangvinh.simple.ui.widget.recycleview;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lyhoangvinh.simple.R;

public class GravitySnapHelper extends MyLinearHelper {

    private MyOrientationHelper verticalHelper;
    private MyOrientationHelper horizontalHelper;
    private int gravity;
    private boolean isSupportRtL;

    @SuppressLint("RtlHardcoded")
    public GravitySnapHelper(int gravity) {
        this.gravity = gravity;
        if (this.gravity == Gravity.LEFT) {
            this.gravity = Gravity.START;
        } else if (this.gravity == Gravity.RIGHT) {
            this.gravity = Gravity.END;
        }
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null) {
            isSupportRtL = recyclerView.getContext().getResources().getBoolean(R.bool.is_rtl);
        }
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            if (gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
            } else { // END
                out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager));
            }
        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager));
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager));
            }
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            switch (gravity) {
                case Gravity.START:
                    return findStartView(layoutManager, getHorizontalHelper(layoutManager));
                case Gravity.TOP:
                    return findStartView(layoutManager, getVerticalHelper(layoutManager));
                case Gravity.END:
                    return findEndView(layoutManager, getHorizontalHelper(layoutManager));
                case Gravity.BOTTOM:
                    return findEndView(layoutManager, getVerticalHelper(layoutManager));
            }
        }

        return super.findSnapView(layoutManager);
    }

    private int distanceToStart(View targetView, MyOrientationHelper helper) {
        if (isSupportRtL) {
            return distanceToEnd(targetView, helper);
        }
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    private int distanceToEnd(View targetView, MyOrientationHelper helper) {
        if (isSupportRtL) {
            return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
        }
        return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
    }

    private View findStartView(RecyclerView.LayoutManager layoutManager, MyOrientationHelper helper) {
//        if (layoutManager instanceof LinearLayoutManager) {
//            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//
//            if (firstChild == RecyclerView.NO_POSITION) {
//                return null;
//            }
//
//            View child = layoutManager.findViewByPosition(firstChild);
//            if (modeScroll == CONST_SNAP.SCROLL_NEXT) {
//                if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 1.3f
//                        && helper.getDecoratedEnd(child) > 0) {
//                    return child;
//                } else {
//                    if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
//                            == layoutManager.getItemCount() - 1) {
//                        return null;
//                    } else {
//                        return layoutManager.findViewByPosition(firstChild + 1);
//                    }
//                }
//            } else if (modeScroll == CONST_SNAP.SCROLL_PREVIOUS) {
//                if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2.7f
//                        && helper.getDecoratedEnd(child) > 0) {
//                    return child;
//                } else {
//                    if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
//                            == layoutManager.getItemCount() - 1) {
//                        return null;
//                    } else {
//                        return layoutManager.findViewByPosition(firstChild + 1);
//                    }
//                }
//            }
//
//        }
//
//        return super.findSnapView(layoutManager, modeScroll);
        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int lastChild = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            if (lastChild == layoutManager.getItemCount() - 1) {
                return null;
            }
            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);
            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1) {
                    return null;
                } else {
                    return layoutManager.findViewByPosition(firstChild + 1);
                }
            }
        }
        return super.findSnapView(layoutManager);
    }

    private View findEndView(RecyclerView.LayoutManager layoutManager,
                             MyOrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            if (lastChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(lastChild);
            if (helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2
                    <= helper.getTotalSpace()) {
                return child;
            } else {
                if (((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition()
                        == 0) {
                    return null;
                } else {
                    return layoutManager.findViewByPosition(lastChild - 1);
                }
            }
        }

        return super.findSnapView(layoutManager);
    }

    private MyOrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (verticalHelper == null) {
            verticalHelper = MyOrientationHelper.createVerticalHelper(layoutManager);
        }
        return verticalHelper;
    }

    private MyOrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (horizontalHelper == null) {
            horizontalHelper = MyOrientationHelper.createHorizontalHelper(layoutManager);
        }
        return horizontalHelper;
    }
}