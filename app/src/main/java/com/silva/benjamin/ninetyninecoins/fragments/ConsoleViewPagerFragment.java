package com.silva.benjamin.ninetyninecoins.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.adapters.ViewPagerAdapter;


public class ConsoleViewPagerFragment extends Fragment {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_consoles_viewpager, container, false);
        setViews(mRootView);
        return mRootView;
    }

    private void setViews(View rootView){
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Últimas ofertas");
        mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPagerAdapter.addFragment(ConsoleFragment.newInstance("XBOX 360", "xbox-360-games"));
        mViewPagerAdapter.addFragment(ConsoleFragment.newInstance("PS4", "ps4-games"));
        mViewPagerAdapter.addFragment(ConsoleFragment.newInstance("XBOX ONE", "xbox-one-games"));
        mViewPagerAdapter.addFragment(ConsoleFragment.newInstance("WII U", "wii-u-games"));
        mViewPagerAdapter.addFragment(ConsoleFragment.newInstance("PS3", "ps3-games"));
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        ((MainActivity)getActivity()).getmTablayout().setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).getmTablayout().setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        setViews(mRootView);
    }
}
