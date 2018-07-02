package com.ryu.dev.likeitgithub.adapter;

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
import com.ryu.dev.likeitgithub.view.GithubSearchFragment;
import com.ryu.dev.likeitgithub.view.LikeFragment;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Items> githubList;
    private Context context;
    private String fragmentDes;

    public RecyclerViewAdapter(Context context, List<Items> githubList, String fragmentDes) {
        this.context = context;
        if (fragmentDes.equals(LikeFragment.LIKE_LIST_FRAGMENT)) {
            this.githubList = new ArrayList<>();

            for (Items item : githubList) {
                if (item.getLike()) {
                    this.githubList.add(item);
                }
            }
        } else {
            this.githubList = githubList;
        }
        this.fragmentDes = fragmentDes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindLayout(githubList.get(position));
    }


    @Override
    public int getItemCount() {
        return githubList.size();
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            tvLike.setOnClickListener(this);
        }

        public void bindLayout(final Items item) {
            Picasso.with(context).load(item.getAvatar_url()).into(img);
            tvLogin.setText(item.getLogin());
            makeDrawable(R.drawable.thumb_up_like);
            changeLike();
        }

        public void changeLike() {
            if (githubList.get(getAdapterPosition()).getLike()) {
                makeDrawable(R.drawable.thumb_up_like);
            } else {
                makeDrawable(R.drawable.thumb_up_unlike);
            }
        }

        private void makeDrawable(int drawableValue) {
            drawable = ContextCompat.getDrawable(context, drawableValue);
            drawable.setBounds(0, 0, 40, 40);
            tvLike.setCompoundDrawables(null, null, drawable, null);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() < 0) {
                return;
            }

            if (fragmentDes.equals(LikeFragment.LIKE_LIST_FRAGMENT)
                    && githubList.get(getAdapterPosition()).getLike()) {
                githubList.get(getAdapterPosition()).setLike(false);
                changeLike();
                githubList.remove(githubList.get(getAdapterPosition()));
                pushToast(R.string.unlike);
            } else if (fragmentDes.equals(GithubSearchFragment.SEARCH_FRAGMENT)
                    && !githubList.get(getAdapterPosition()).getLike()) {
                githubList.get(getAdapterPosition()).setLike(true);
                changeLike();
                pushToast(R.string.like);
            }

            notifyDataSetChanged();
        }

        public void pushToast(int id) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
