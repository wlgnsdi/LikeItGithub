package com.ryu.dev.likeitgithub.view.like.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.model.Github.Items;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Items> mGithubList;
    private Context mContext;

    public Adapter(Context context, List<Items> githubList) {
        mContext = context;
        mGithubList = new ArrayList<>();

        for (Items item : githubList) {
            if (item.getLike()) {
                mGithubList.add(item);
            }
        }
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
        TextView tvLike;

        private Toast toast;
        private Drawable drawable;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            tvLike.setOnClickListener(this);
        }

        void bindLayout(final Items item) {
            Picasso.with(mContext).load(item.getAvatarUrl()).into(img);
            tvLogin.setText(item.getLogin());
            makeDrawable(R.drawable.thumb_up_like);
        }

        private void makeDrawable(int drawableValue) {
            drawable = ContextCompat.getDrawable(mContext, drawableValue);
            drawable.setBounds(0, 0, 40, 40);
            tvLike.setCompoundDrawables(null, null, drawable, null);
        }

        @Override
        public void onClick(View v) {
            if (mGithubList.get(getAdapterPosition()).getLike()) {
                mGithubList.get(getAdapterPosition()).setLike(false);
                mGithubList.remove(mGithubList.get(getAdapterPosition()));
            }

            notifyDataSetChanged();
        }
    }
}
