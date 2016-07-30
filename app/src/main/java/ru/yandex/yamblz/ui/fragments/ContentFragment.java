package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;

public class ContentFragment extends BaseFragment implements View.OnClickListener {

    private GridLayoutManager layoutManager;
    private ContentAdapter contentAdapter;

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
        layoutManager = new GridLayoutManager(getContext(), 1);
        contentAdapter = new ContentAdapter();
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(contentAdapter);
    }

    @OnClick({R.id.plus_column, R.id.minus_column})
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
    }
}
