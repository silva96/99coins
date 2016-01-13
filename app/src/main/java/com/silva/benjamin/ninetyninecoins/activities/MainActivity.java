package com.silva.benjamin.ninetyninecoins.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.adapters.ViewPagerAdapter;
import com.silva.benjamin.ninetyninecoins.fragments.ConsoleFragment;
import com.silva.benjamin.ninetyninecoins.fragments.ConsoleViewPagerFragment;
import com.silva.benjamin.ninetyninecoins.fragments.FavouritesFragment;
import com.silva.benjamin.ninetyninecoins.fragments.SearchResultsFragment;
import com.silva.benjamin.ninetyninecoins.util.Helper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawerToggle.isDrawerIndicatorEnabled()) {
                    onBackPressed();
                }
            }
        });
        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        openFragment(ConsoleViewPagerFragment.class, null, true);
        if(isSearchIntent(getIntent())) handleIntent(getIntent());
        else  mNavigationView.setCheckedItem(R.id.last_offers);


    }

    public TabLayout getmTablayout() {
        return mTablayout;
    }

    public void openFragment(Class fragmentClass, Bundle bundle, boolean rootFragment) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bundle != null) fragment.setArguments(bundle);
        if(rootFragment)mDrawerToggle.setDrawerIndicatorEnabled(true);
        else{
            mDrawerToggle.setDrawerIndicatorEnabled(false);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(Helper.FIRST_FRAGMENT_ADDED) {
            ft.addToBackStack(fragment.getClass().getName());
            ft.replace(R.id.flContent, fragment);
        }
        else{
            ft.add(R.id.flContent, fragment);
            Helper.FIRST_FRAGMENT_ADDED = true;
        }
        ft.commit();

    }
    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals(SearchResultsFragment.class.getName())){
                //when comming back from search results, to ConsoleViewPager, set "Ultimas ofertas" as checked"
                    mNavigationView.setCheckedItem(R.id.last_offers);
                }
                fm.popBackStack();
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }else{
                super.onBackPressed();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.favourites) {
            openFragment(FavouritesFragment.class, null, true);
        } else if (id == R.id.last_offers) {
            openFragment(ConsoleViewPagerFragment.class, null, true);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isSearchIntent(Intent intent){
        return Intent.ACTION_SEARCH.equals(intent.getAction());
    }

    private void handleIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        mTablayout.setVisibility(View.GONE);
        openFragment(SearchResultsFragment.class, bundle, false);
    }
}
