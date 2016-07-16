package com.utils.gdkcorp.comparer;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout coordinator_layout;
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private NavigationView mDrawer;
    private FragmentManager fmng;
    private ImageView appbar_imgview;
    private ViewPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appbar_imgview = (ImageView) findViewById(R.id.appbar_imgview);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.Drawer_Layout);
        coordinator_layout= (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        appbarlayout= (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        tablayout= (TabLayout) findViewById(R.id.tab_layout);
        viewPager= (ViewPager) findViewById(R.id.view_pager);
        mDrawer= (NavigationView) findViewById(R.id.Navigation_View);

        setSupportActionBar(toolbar);

        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.Drawer_open,R.string.Drawer_Close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        fmng=getSupportFragmentManager();
        mPagerAdapter = new ViewPagerAdapter(fmng);
        mPagerAdapter.initializeAmazonFrag();
        viewPager.setAdapter(mPagerAdapter);

        //tablayout.setTabsFromPagerAdapter(mPagerAdapter);
        tablayout.setupWithViewPager(viewPager);
        tablayout.getTabAt(0).setIcon(R.drawable.amazon_selector);
        tablayout.getTabAt(1).setIcon(R.drawable.flipkart_selector);
        tablayout.getTabAt(2).setIcon(R.drawable.snapdeal_selector);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    appbar_imgview.setImageDrawable(getResources().getDrawable(R.drawable.amazon_banner));
                }
                else if(tab.getPosition()==1){
                    appbar_imgview.setImageDrawable(getResources().getDrawable(R.drawable.flipkart_banner));
                }else if(tab.getPosition()==2){
                    appbar_imgview.setImageDrawable(getResources().getDrawable(R.drawable.snapdeal_banner));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        collapsingToolbarLayout.setTitle("Comparer");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT);
                mPagerAdapter.startservice(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter{

        Fragment tabfragment=null;
        AmazonFragment amazonFragment=null;
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
            {
                return amazonFragment;

            }
            else{
                tabfragment=MyFragment.newInstance(position);
                return tabfragment;
            }
        }

        public void initializeAmazonFrag(){
            amazonFragment = AmazonFragment.newInstance("","");
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0)
            {

                return "";
            }
            else if(position==1){
                return "";
            }
            else if(position==2){
                return "";
            }
            else{
                return "TAB";
            }
        }

        public void startservice(String query) {
            if(amazonFragment.adapter!=null){
                amazonFragment.adapter.clearData();
            }
            amazonFragment.startAmazonTask(query);
        }
    }


}

