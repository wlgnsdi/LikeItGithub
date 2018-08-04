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
import com.ryu.dev.likeitgithub.view.like.LikeFragment;
import com.ryu.dev.likeitgithub.view.search.GithubSearchFragment;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class _RecyclerViewAdapter extends RecyclerView.Adapter<_RecyclerViewAdapter.ViewHolder> {

    private List<Items> mGithubList;
    private Context mContext;
    private String mFragmentDes;
    private AdapterInterface mInterface;

//    String[] projection = {
//            GithubTable.COLUMN_ID,
//            GithubTable.COLUMN_LOGIN,
//            GithubTable.COLUMN_AVATAR_URL,
//            GithubTable.COLUMN_LIKE
//    };

    public _RecyclerViewAdapter(Context context, List<Items> githubList, String fragmentDes, AdapterInterface adapterInterface) {
        mContext = context;

        if (fragmentDes.equals(LikeFragment.LIKE_LIST_FRAGMENT)) {
            mGithubList = new ArrayList<>();

            for (Items item : githubList) {
                if (item.getLike()) {
                    mGithubList.add(item);
                }
            }
        } else {
            mGithubList = githubList;
        }

        mFragmentDes = fragmentDes;
        mInterface = adapterInterface;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
            tvLike.setOnClickListener(this);
        }

        public void bindLayout(final Items item) {
            Picasso.with(mContext).load(item.getAvatarUrl()).into(img);
            tvLogin.setText(item.getLogin());
            makeDrawable(R.drawable.thumb_up_like);
            changeLike();
        }

        public void changeLike() {
//            sDb = sDatabaseHelper.getWritableDatabase();

            Items item = mGithubList.get(getAdapterPosition());

//            ContentValues values = new ContentValues();
//            values.put(GithubTable.COLUMN_LOGIN, item.getLogin());
//            values.put(GithubTable.COLUMN_AVATAR_URL, item.getAvatarUrl());
//            values.put(GithubTable.COLUMN_LIKE, item.getLike());

            if (mGithubList.get(getAdapterPosition()).getLike()) {
//                sDb.insert(GithubTable.TABLE_NAME, null, values);
                makeDrawable(R.drawable.thumb_up_like);
            } else {
//                String selection = GithubTable.COLUMN_LOGIN + " LIKE ?";
//                String[] selectionArgs = {item.getLogin()};
//                sDb.delete(GithubTable.COLUMN_LOGIN, selection, selectionArgs);
                makeDrawable(R.drawable.thumb_up_unlike);
            }
        }

        private void makeDrawable(int drawableValue) {
            drawable = ContextCompat.getDrawable(mContext, drawableValue);
            drawable.setBounds(0, 0, 40, 40);
            tvLike.setCompoundDrawables(null, null, drawable, null);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() < 0) {
                return;
            }

            if (mFragmentDes.equals(LikeFragment.LIKE_LIST_FRAGMENT) && mGithubList.get(getAdapterPosition()).getLike()) {
                mGithubList.get(getAdapterPosition()).setLike(false);

                changeLike();
                mGithubList.remove(mGithubList.get(getAdapterPosition()));
                pushToast(R.string.unlike);
            } else if (mFragmentDes.equals(GithubSearchFragment.SEARCH_FRAGMENT) && !mGithubList.get(getAdapterPosition()).getLike()) {
                mGithubList.get(getAdapterPosition()).setLike(true);

                changeLike();
                pushToast(R.string.like);
            }

            notifyDataSetChanged();
        }

        public void pushToast(int id) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(mContext, id, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public interface AdapterInterface {
        void adapterOnClick();
    }
}
