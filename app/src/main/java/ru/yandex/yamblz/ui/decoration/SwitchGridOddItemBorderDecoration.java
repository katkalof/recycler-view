

package ru.yandex.yamblz.ui.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.yandex.yamblz.ui.adapter.ContentAdapter;

public class SwitchGridOddItemBorderDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private RectF rectF;

    public SwitchGridOddItemBorderDecoration() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.rgb(255, 221, 96));
        rectF = new RectF();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            ContentAdapter.ContentHolder holder = (ContentAdapter.ContentHolder) parent.getChildViewHolder(child);
            if (holder.isMoved()) {
                int center = child.getLeft() + child.getWidth()/2;
                int top = child.getTop();
                rectF.set(center - 5, top + 10, center + 5, top + 20);
                c.drawOval(rectF, mPaint);
            }
        }
    }
}