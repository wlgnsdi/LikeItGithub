package com.ryu.dev.likeitgithub.network;

import static com.ryu.dev.likeitgithub.view.MainActivity.githubList;

import com.ryu.dev.likeitgithub.model.Github;
import com.ryu.dev.likeitgithub.model.Github.Items;
import com.ryu.dev.likeitgithub.view.MainActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCall {

    private NetworkInterface networkInterface;
    private Call<Github> mCall;
    private List<Github.Items> netWorkGithubLists;

    public NetworkCall(Call call, NetworkInterface networkInterface) {
        this.mCall = call;
        this.networkInterface = networkInterface;
    }

    public void proceed() {
        mCall.enqueue(new Callback<Github>() {
            @Override
            public void onResponse(Call<Github> call, Response<Github> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        netWorkGithubLists = response.body().getItemsList();
                        result();
                    } else {
                        networkInterface.noResult();
                    }
                }
            }

            @Override
            public void onFailure(Call<Github> call, Throwable t) {
                networkInterface.noResult();
            }
        });
    }

    public void result() {
        Collections.sort(netWorkGithubLists, new Comparator<Items>() {
            @Override
            public int compare(Items o1, Items o2) {
                return o1.getLogin().toUpperCase().compareTo(o2.getLogin().toUpperCase());
            }
        });

        if (MainActivity.NUM_PAGE > 1) {
            for (Items item : netWorkGithubLists) {
                githubList.add(item);
            }
        } else {
            githubList = this.netWorkGithubLists;
        }

        networkInterface.resultNetwork();
    }

    public interface NetworkInterface {
        void resultNetwork();
        void noResult();
    }
}
