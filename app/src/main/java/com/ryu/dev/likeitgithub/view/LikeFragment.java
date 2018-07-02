package com.ryu.dev.likeitgithub.view;

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
import com.ryu.dev.likeitgithub.adapter.RecyclerViewAdapter;

public class LikeFragment extends Fragment {

    public static final String LIKE_LIST_FRAGMENT = "LIKE_LIST_FRAGMENT";
    public static LikeFragment mLikeFragment;
    @BindView(R.id.recyclerview_like)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GithubSearchFragment.githubList.size() > 0) {
            adapter = new RecyclerViewAdapter(
                    getActivity(), GithubSearchFragment.githubList, LIKE_LIST_FRAGMENT);
            recyclerView.setAdapter(adapter);
        }
    }
}
