package com.ryu.dev.likeitgithub.view.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.model.Github.Items;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final String TAG = Adapter.class.getSimpleName();

    private List<Pair<String, Items>> mTotalList;
    private List<Items> mGithubList;
    private Context mContext;
    private SearchInterface mInterface;

    public void setGithubList(List<Items> items) {
        mGithubList = items;
    }

    public Adapter(Context context, List<Items> githubList, SearchInterface interfaces) {
        mContext = context;
        mGithubList = githubList;
        mTotalList = new ArrayList<>();

        setTotalList();
        mInterface = interfaces;
    }

    private int mGithubListPreSize = 0;
    public void setTotalList() {
        Items prevItem = null;

        for (int i = mGithubListPreSize; i < mGithubList.size(); i++) {
            if (prevItem == null || !prevItem.getLogin().substring(0,1).equals(mGithubList.get(i).getLogin().substring(0,1))) {
                mTotalList.add(new Pair(mGithubList.get(i).getLogin().substring(0, 1), null));
            }

            mTotalList.add(new Pair(mGithubList.get(i).getLogin().substring(0, 1), mGithubList.get(i)));
            prevItem = mGithubList.get(i);
        }

        mGithubListPreSize = mGithubList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;

        if (viewType == TYPE_HEADER) {
            view = layoutInflater.inflate(R.layout.list_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindLayout(mTotalList.get(position).second);
        } else {
            ((HeaderViewHolder) holder).bindLayout(mTotalList.get(position).first);
        }
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mTotalList.get(position).second == null) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.github_img)
        ImageView img;
        @BindView(R.id.github_login)
        TextView tvLogin;
        @BindView(R.id.github_like)
        ImageView imgLike;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            imgLike.setOnClickListener(this);
        }

        public void bindLayout(final Items item) {
            Picasso.with(mContext).load(item.getAvatarUrl()).into(img);
            tvLogin.setText(item.getLogin());
            if (item.getLike()) {
                imgLike.setSelected(true);
            } else {
                imgLike.setSelected(false);
            }
        }

        public void changeLike() {
            if (mGithubList.get(getAdapterPosition()).getLike()) {
                img.setSelected(true);
            } else {
                img.setSelected(false);
            }

            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            boolean isLike = !mTotalList.get(getAdapterPosition()).second.getLike();

            mTotalList.get(getAdapterPosition()).second.setLike(isLike);
            Log.d(">>>>", "Position : " + getAdapterPosition() + ", " + isLike);
            mInterface.onClick(isLike, mTotalList.get(getAdapterPosition()).second);
            changeLike();
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView mTvHeader;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindLayout(String title) {
            mTvHeader.setText(title);
        }
    }

    public interface SearchInterface {

        public void onClick(boolean isLike, Items items);
    }
}
