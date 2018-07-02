package com.ryu.dev.likeitgithub.view;

import static com.ryu.dev.likeitgithub.view.GithubSearchFragment.githubList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ryu.dev.likeitgithub.R;
import com.ryu.dev.likeitgithub.adapter.PagePagerAdapter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.tab_pager_title)
    TabLayout mTabLayout;

    private PagePagerAdapter pagerAdapter;
    private int tabPosition = -1;
    private long backPressedTime = 0;
    private Toast toast;

    public static int NUM_PAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new PagePagerAdapter(this, getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                resumeFragment();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void resumeFragment() {
        mPager.setCurrentItem(tabPosition);
        Fragment fragment = pagerAdapter.getFragment(tabPosition);
        if (fragment != null) {
            fragment.onResume();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (networkCheck(MainActivity.this)) {
                    if (!TextUtils.isEmpty(s.trim()) && textCheck(s.trim())) {
                        NUM_PAGE = 0;
                        githubList = new ArrayList<>();
                        ((GithubSearchFragment) pagerAdapter.getFragment(0))
                                .retrofitNetworking(s.trim());

                        View view = getCurrentFocus();
                        if (view != null) {
                            view.clearFocus();
                        }
                    } else {
                        pushToast(R.string.no_search);
                    }
                } else {
                    pushToast(R.string.network_is_not_working);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    private boolean textCheck(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public boolean networkCheck(Context context) {
        if (context == null) {
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null;
        } else {
            return false;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    Fragment mFragment;

    @Override
    public void onPageSelected(int position) {
        mFragment = pagerAdapter.getFragment(position);

        if (mFragment != null) {
            mFragment.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void pushToast(int id) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(getApplication(), id, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime == 0) {
            pushToast(R.string.exit);
            backPressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - backPressedTime);
            if (seconds > 2000) {
                pushToast(R.string.exit);
                backPressedTime = 0;
            } else {
                super.onBackPressed();
                this.finish();
            }
        }
    }
}
