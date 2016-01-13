package com.silva.benjamin.ninetyninecoins.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.adapters.GameListAdapter;
import com.silva.benjamin.ninetyninecoins.models.Item;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class SearchResultsFragment extends Fragment{


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private GameListAdapter mAdapter;
    private final int RECYCLER_CACHE_SIZE = 50;
    private String mQuery;
    private Spinner mFilterSpinner;
    private ArrayList<Item> mCurrentDataset;
    private ArrayList<Item> mOriginalDataset;
    private TextView mNoResultsForCatMsg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        mQuery = getArguments().getString("query");
        doTheSearch();
        setViews(rootView);
        return rootView;
    }

    private void setViews(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.search_results_recycler_view);
        mRecyclerView.setItemViewCacheSize(RECYCLER_CACHE_SIZE);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mNoResultsForCatMsg = (TextView) rootView.findViewById(R.id.no_results_at_category_msg);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Buscar: " + mQuery);
        mFilterSpinner   = (Spinner) rootView.findViewById(R.id.filterSpinner);
        String[] types = {"Ver todas", "PS4", "PS3", "XBOX ONE", "XBOX 360", "Wii U"};
        ArrayAdapter<String> mTypeSpinnerArrayAdapter = populateSpinner(mFilterSpinner, types);
        mFilterSpinner.setAdapter(mTypeSpinnerArrayAdapter);
        setFilterListener();
    }

    private <T> ArrayAdapter<T> populateSpinner(Spinner spinner, T[] array){
        ArrayAdapter<T> field_adapter = null;
        field_adapter = new ArrayAdapter<T>(getActivity(), R.layout.custom_drop_down_item_centered, array);
        field_adapter.setDropDownViewResource(R.layout.custom_drop_down_item_checked);
        return field_adapter;
    }

    public void setFilterListener(){
        mFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String t = (String) parent.getSelectedItem();
                mCurrentDataset = new ArrayList<>();
                if (t.equals("Ver todas")) {
                    mCurrentDataset = mOriginalDataset;
                } else if (t.equals("PS4")) {
                    for (Item i : mOriginalDataset) {
                        if (i.getCategory().equals("ps4-games")) mCurrentDataset.add(i);
                    }
                } else if (t.equals("PS3")) {
                    for (Item i : mOriginalDataset) {
                        if (i.getCategory().equals("ps3-games")) mCurrentDataset.add(i);
                    }
                } else if (t.equals("XBOX ONE")) {
                    for (Item i : mOriginalDataset) {
                        if (i.getCategory().equals("xbox-one-games")) mCurrentDataset.add(i);
                    }
                } else if (t.equals("XBOX 360")) {
                    for (Item i : mOriginalDataset) {
                        if (i.getCategory().equals("xbox-360-games")) mCurrentDataset.add(i);
                    }
                } else if (t.equals("Wii U")) {
                    for (Item i : mOriginalDataset) {
                        if (i.getCategory().equals("wii-u-games")) mCurrentDataset.add(i);
                    }
                }
                if (mCurrentDataset.size() == 0) mNoResultsForCatMsg.setVisibility(View.VISIBLE);
                else mNoResultsForCatMsg.setVisibility(View.GONE);
                mAdapter = new GameListAdapter(mCurrentDataset, ((MainActivity) getActivity()));
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void doTheSearch(){
        Realm realm = Realm.getInstance(getActivity());
        RealmResults<Item> result = realm.where(Item.class).contains("title", mQuery, Case.INSENSITIVE).findAll();
        mOriginalDataset = new ArrayList<>();
        for(Item i : result) mOriginalDataset.add(i);
    }

}