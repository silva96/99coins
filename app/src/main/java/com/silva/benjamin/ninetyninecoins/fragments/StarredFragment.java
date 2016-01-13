package com.silva.benjamin.ninetyninecoins.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.adapters.GameListAdapter;


public class StarredFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RelativeLayout mLoadingWrapper;
    private Toast mToast;
    private GameListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_starred, container, false);
        setViews(rootView);
        renderData();
        return rootView;
    }

    private void setViews(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.console_recycler_view);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLoadingWrapper = (RelativeLayout) rootView.findViewById(R.id.loading_wrapper);
    }

    private void renderData(){


    }
}
