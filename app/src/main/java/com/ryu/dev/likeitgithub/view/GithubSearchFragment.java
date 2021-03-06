package com.ryu.dev.likeitgithub.view;

import static com.ryu.dev.likeitgithub.view.MainActivity.NUM_PAGE;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.adapter.EndlessRecyclerOnScrollListener;
import com.ryu.dev.likeitgithub.adapter.RecyclerViewAdapter;
import com.ryu.dev.likeitgithub.model.Github;
import com.ryu.dev.likeitgithub.model.Github.Items;
import com.ryu.dev.likeitgithub.network.NetworkCall;
import com.ryu.dev.likeitgithub.network.NetworkCall.NetworkInterface;
import com.ryu.dev.likeitgithub.network.RetrofitService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;

public class GithubSearchFragment extends Fragment {

    public static final String SEARCH_FRAGMENT = "SEARCH_FRAGMENT";
    private static final String TAG = GithubSearchFragment.class.getSimpleName();
    public static GithubSearchFragment searchFragment;

    @BindView(R.id.recyclerview_find)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

    public static List<Items> githubList = new ArrayList<>();
    private String searchStr;
    private EndlessRecyclerOnScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;

    public static GithubSearchFragment newInstance() {
        if (searchFragment == null) {
            searchFragment = new GithubSearchFragment();
        }

        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_github_search, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (githubList.size() > 0) {
            adapter.notifyDataSetChanged();
        }
    }

    public void initListener() {
        recyclerView.clearOnScrollListeners();
        scrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                if (!TextUtils.isEmpty(searchStr)) {
                    retrofitNetworking(searchStr);
                }
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void retrofitNetworking(String str) {
        searchStr = str;
        NUM_PAGE++;
        RetrofitService retrofitService = RetrofitService.retrofit.create(RetrofitService.class);
        final Call<Github> call = retrofitService.callUser(searchStr, NUM_PAGE, 30);
        NetworkCall networkCall = new NetworkCall(call, new NetworkInterface() {
            @Override
            public void resultNetwork() {
                if (NUM_PAGE == 1) {
                    Log.d(TAG, "resultNetwork : " + githubList.size());
                    adapter = new RecyclerViewAdapter(getActivity(), githubList, SEARCH_FRAGMENT);
                    recyclerView.setAdapter(adapter);
                    initListener();
                    ((MainActivity) getActivity()).resumeFragment();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void noResult() {
                NUM_PAGE--;

                adapter.notifyDataSetChanged();
            }
        });

        networkCall.proceed();
    }


}
