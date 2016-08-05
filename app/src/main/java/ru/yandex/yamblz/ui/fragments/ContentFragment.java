package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapter.ContentAdapter;
import ru.yandex.yamblz.ui.decoration.GridOddItemBorderDecoration;
import ru.yandex.yamblz.ui.decoration.SwitchGridOddItemBorderDecoration;
import ru.yandex.yamblz.ui.handler.SimpleItemTouchHelperCallback;

public class ContentFragment extends BaseFragment implements View.OnClickListener {

    private GridLayoutManager layoutManager;
    private ContentAdapter contentAdapter;
    private GridOddItemBorderDecoration borderDecoration;
    private boolean enableBorderDecoration = false;

    @BindView(R.id.rv)
    RecyclerView rv;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutManager = new GridLayoutManager(getContext(),30);
        contentAdapter = new ContentAdapter();
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(contentAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(contentAdapter));
        touchHelper.attachToRecyclerView(rv);
        borderDecoration = new GridOddItemBorderDecoration(8);
        rv.addItemDecoration(new SwitchGridOddItemBorderDecoration());
        rv.setHasFixedSize(true);
        rv.getRecycledViewPool().setMaxRecycledViews(0, 30);
    }

    @OnClick({R.id.plus_column, R.id.minus_column, R.id.decorate_border})
    @Override
    public void onClick(View v) {
        int spanCount = layoutManager.getSpanCount();
        switch (v.getId()) {
            case R.id.plus_column:
                changeSpanCount(++spanCount);
                break;
            case R.id.minus_column:
                if (spanCount > 1) {
                    changeSpanCount(--spanCount);
                } else {
                    Toast.makeText(getContext(), "one column already, enough", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.decorate_border:
                enableBorderDecoration = !enableBorderDecoration;
                if (enableBorderDecoration)
                    rv.addItemDecoration(borderDecoration);
                else
                    rv.removeItemDecoration(borderDecoration);
        }
    }


    /**
     * Set new span count and notify content adapterß
     *
     * @param newSpanCount New count of span
     */
    private void changeSpanCount(int newSpanCount) {
        layoutManager.setSpanCount(newSpanCount);
        //Не видно разницы какой элемент нотифаить при count = 0
        //Видимо процесс просто дает команду аниматору
        contentAdapter.notifyItemChanged(0, 0);
        rv.requestLayout();//Иначе не сработает анимация при setHasFixedSize(true)
    }
}
