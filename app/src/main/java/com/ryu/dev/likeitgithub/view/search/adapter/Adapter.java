package com.ryu.dev.likeitgithub.view.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Items> mGithubList;
    private Context mContext;
    private SearchInterface mInterface;

    public Adapter(Context context, List<Items> githubList, SearchInterface interfaces) {
        mContext = context;
        mGithubList = githubList;
        mInterface = interfaces;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindLayout(mGithubList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGithubList.size();
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
            }
            else {
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
            boolean isLike = !mGithubList.get(getAdapterPosition()).getLike();

            mGithubList.get(getAdapterPosition()).setLike(isLike);
            Log.d(">>>>", "Position : " + getAdapterPosition() + ", " + isLike);
            mInterface.onClick(isLike, mGithubList.get(getAdapterPosition()));
            changeLike();
        }
    }

    public interface SearchInterface {
        public void onClick(boolean isLike, Items items);
    }
}
