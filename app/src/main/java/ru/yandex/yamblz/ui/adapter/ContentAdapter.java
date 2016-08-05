package ru.yandex.yamblz.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> implements ItemTouchHelperAdapter {

    private final Random rnd = new Random();
    private final List<Integer> colors = new ArrayList<>();
    private int fromPos = RecyclerView.NO_POSITION;
    private int toPos = RecyclerView.NO_POSITION;

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);
        ContentHolder h = new ContentHolder(v);
        v.setOnClickListener(it -> {
            int adapterPosition = h.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                colors.set(adapterPosition, Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
                notifyItemChanged(adapterPosition);
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(createColorForPosition(position));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer createColorForPosition(int position) {
        if (position >= colors.size())
            colors.add(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
        return colors.get(position);
    }

    @Override
    public void onItemDismiss(int position) {
        colors.remove(position);
        if (position == toPos)
            toPos = RecyclerView.NO_POSITION;
        if (position == fromPos)
            fromPos = RecyclerView.NO_POSITION;
        notifyItemRemoved(position);
    }

    @Override
    public boolean onFailedToRecycleView(ContentHolder holder) {
        return true;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(colors, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        toPos = toPosition;
        fromPos = fromPosition < toPosition ? toPos - 1 : toPos + 1;
    }

    public class ContentHolder extends RecyclerView.ViewHolder {
        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
        }

        public boolean isMoved() {
            return getAdapterPosition() == fromPos || getAdapterPosition() == toPos;
        }
    }
}
