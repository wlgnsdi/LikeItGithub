package com.ryu.dev.likeitgithub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.view.GithubSearchFragment;
import com.ryu.dev.likeitgithub.view.LikeFragment;
import java.util.HashMap;
import java.util.Map;

public class PagePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 2;
    private Map<Integer, String> mFragmentTags = new HashMap<>();
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private Context context;

    public PagePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        this.mFragmentManager = fm;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.titleSearch);

            default:
                return context.getString(R.string.titleLike);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mFragment = GithubSearchFragment.newInstance();
                return mFragment;

            default:
                mFragment = LikeFragment.newInstance();
                return mFragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }

        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = mFragmentManager.findFragmentByTag(tag);
        }

        return fragment;
    }
}
