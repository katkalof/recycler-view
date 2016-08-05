package ru.yandex.yamblz.ui.handler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import ru.yandex.yamblz.ui.adapter.ItemTouchHelperAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter adapter;
    private final Paint paint;

    public SimpleItemTouchHelperCallback(
            ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(0);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.ACTION_STATE_DRAG
                | ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.RIGHT | ItemTouchHelper.END
                | ItemTouchHelper.LEFT | ItemTouchHelper.START;

        int swipeFlags = ItemTouchHelper.RIGHT | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        adapter.onItemMove(fromPos, toPos);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View v = viewHolder.itemView;
            int width = v.getWidth();
            int left = v.getLeft();
            int top = v.getTop();
            int alpha = Math.min(Math.round(dX / width * 255), 255);
            paint.setAlpha(alpha);
            c.drawRect(left, top, left + dX, top + v.getHeight(), paint);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
