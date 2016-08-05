package ru.yandex.yamblz.ui.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridOddItemBorderDecoration extends RecyclerView.ItemDecoration {
    private final int border;
    private Paint mPaint;


    public GridOddItemBorderDecoration(int border) {
        this.border = border;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(border);
        mPaint.setColor(Color.rgb(255, 221, 96));
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childPosition = parent.getChildAdapterPosition(view);
        if ((childPosition & 1) == 0) {
            outRect.left = border;
            outRect.right = border;
            outRect.top = border;
            outRect.bottom = border;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            int childPosition = parent.getChildAdapterPosition(child);
            if ((childPosition & 1) == 0) {
                c.drawRect(
                        layoutManager.getDecoratedLeft(child) + border / 2,
                        layoutManager.getDecoratedTop(child) + border / 2,
                        layoutManager.getDecoratedRight(child) - border / 2,
                        layoutManager.getDecoratedBottom(child) - border / 2,
                        mPaint);
            }
        }
    }

}