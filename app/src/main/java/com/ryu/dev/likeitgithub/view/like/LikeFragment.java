package com.ryu.dev.likeitgithub.view.like;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.db.DatabaseHelper;
import com.ryu.dev.likeitgithub.model.Github.Items;
import com.ryu.dev.likeitgithub.view.like.adapter.Adapter;
import com.ryu.dev.likeitgithub.view.like.adapter.Adapter.LikeInterface;

public class LikeFragment extends Fragment implements LikeInterface {

    public static LikeFragment mLikeFragment;
    @BindView(R.id.recyclerview_like)
    RecyclerView recyclerView;

    private Adapter adapter;
    private DatabaseHelper mHelper;

    public static LikeFragment newInstance() {
        if (mLikeFragment == null) {
            mLikeFragment = new LikeFragment();
        }

        return mLikeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        ButterKnife.bind(this, view);

        // Database
        mHelper = DatabaseHelper.getInstance(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mHelper.getAll().size() > 0) {
            adapter = new Adapter(getActivity(), mHelper.getAll(), this);

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(Items item) {
        mHelper.delete(item);
    }
}
