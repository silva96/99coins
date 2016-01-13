package com.silva.benjamin.ninetyninecoins.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

import com.silva.benjamin.ninetyninecoins.fragments.PagerFragment;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private  List<Fragment> mFragmentList = new ArrayList<>();



    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public void addFragment(Fragment f){
        mFragmentList.add(f);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((PagerFragment)mFragmentList.get(position)).getmTabName();
    }
}