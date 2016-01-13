package com.silva.benjamin.ninetyninecoins.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.adapters.GameListAdapter;
import com.silva.benjamin.ninetyninecoins.models.Item;
import com.silva.benjamin.ninetyninecoins.models.StarredItem;
import com.silva.benjamin.ninetyninecoins.util.PreCachingLayoutManager;

import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class FavouritesFragment  extends Fragment{

    private static final int RECYCLER_CACHE_SIZE = 50;
    private RecyclerView mRecyclerView;
    private PreCachingLayoutManager mLayoutManager;
    private GameListAdapter mAdapter;
    private TextView mNoFavouritesMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        setViews(rootView);
        return rootView;
    }

    private void setViews(View rootView){
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.getmTablayout().setVisibility(View.GONE);
        mainActivity.getSupportActionBar().setTitle("Juegos Favoritos");
        mNoFavouritesMsg = (TextView) rootView.findViewById(R.id.no_favourites);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favourites_recycler_view);
        mRecyclerView.setItemViewCacheSize(RECYCLER_CACHE_SIZE);
        mLayoutManager = new PreCachingLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        Realm realm = Realm.getInstance(getActivity());
        RealmResults<StarredItem> result = realm.where(StarredItem.class).findAll();
        ArrayList<Item> items = new ArrayList<>();
        for(StarredItem i : result){
            items.add(realm.where(Item.class).equalTo("_id", i.get_id()).findFirst());
        }
        mAdapter = new GameListAdapter(items, ((MainActivity)getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        if(items.size()==0){
            mRecyclerView.setVisibility(View.GONE);
            mNoFavouritesMsg.setVisibility(View.VISIBLE);
        }
    }

}
