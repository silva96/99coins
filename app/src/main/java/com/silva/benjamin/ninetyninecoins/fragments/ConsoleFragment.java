package com.silva.benjamin.ninetyninecoins.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.adapters.GameListAdapter;
import com.silva.benjamin.ninetyninecoins.models.Item;
import com.silva.benjamin.ninetyninecoins.util.Helper;
import com.silva.benjamin.ninetyninecoins.util.PreCachingLayoutManager;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ConsoleFragment extends PagerFragment {

    private static final String TAB_CATEGORY = "tab_category_tag";
    private RecyclerView mRecyclerView;
    private PreCachingLayoutManager mLayoutManager;
    private RelativeLayout mLoadingWrapper;
    private Toast mToast;
    private GameListAdapter mAdapter;
    private final int RECYCLER_CACHE_SIZE = 50;

    public static PagerFragment newInstance(String tabName, String tabCategory) {
        ConsoleFragment fragment = new ConsoleFragment();
        fragment.setmTabName(tabName);
        Bundle args = new Bundle();
        args.putString(TAB_CATEGORY, tabCategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_console, container, false);
        setViews(rootView);
        renderData();
        return rootView;
    }

    private void setViews(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.console_recycler_view);
        mRecyclerView.setItemViewCacheSize(RECYCLER_CACHE_SIZE);
        mLayoutManager = new PreCachingLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mLoadingWrapper = (RelativeLayout) rootView.findViewById(R.id.loading_wrapper);
    }

    private void renderData(){
        Bundle bundle = this.getArguments();
        String category = bundle.getString(TAB_CATEGORY);
        final Realm realm = Realm.getInstance(getActivity());
        if(Helper.FETCHED){
            ArrayList<Item> items = new ArrayList<>();
            RealmResults<Item> results = realm.where(Item.class).equalTo("category",category).findAll();
            for(Item i : results){
                items.add(i);
            }
            mAdapter = new GameListAdapter(items, ((MainActivity)getActivity()));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setVisibility(View.VISIBLE);
            mLoadingWrapper.setVisibility(View.GONE);
        }
        else{
            Call<ArrayList<Item>> call = Helper.service().listItems("100", "1", "0", category);
            call.enqueue(new Callback<ArrayList<Item>>() {
                @Override
                public void onResponse(Response<ArrayList<Item>> response) {
                    ArrayList<Item> items = response.body();
                    if (items != null) {
                        Helper.FETCHED = true;
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(items);
                        realm.commitTransaction();
                        mAdapter = new GameListAdapter(items, ((MainActivity)getActivity()));
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mLoadingWrapper.setVisibility(View.GONE);
                    } else {
                        Helper.showAToast(mToast, getActivity(), "Error al obtener los juegos", Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -20);
                        renderData();
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    Helper.showAToast(mToast, getActivity(), "Error al obtener los juegos", Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, -20);
                    renderData();

                }
            });
        }


    }


}
