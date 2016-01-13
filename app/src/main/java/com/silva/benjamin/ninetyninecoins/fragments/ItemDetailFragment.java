package com.silva.benjamin.ninetyninecoins.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.models.Item;
import com.silva.benjamin.ninetyninecoins.util.Helper;
import com.squareup.picasso.Picasso;

import io.realm.Realm;


public class ItemDetailFragment extends Fragment {


    private String mItemId;
    private TextView mTitle;
    private TextView mCategory;
    private  TextView mMinPrice;
    private TextView mMaxPrice;
    private TextView mAvgprice;
    private ImageView mGamePicture;
    private MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        mItemId = getArguments().getString("_id");
        setViews(rootView);
        return rootView;
    }

    private void setViews(View rootView){
        mContext = ((MainActivity) getActivity());
        mContext.getmTablayout().setVisibility(View.GONE);
        mTitle = (TextView) rootView.findViewById(R.id.game_title_detail);
        mCategory = (TextView) rootView.findViewById(R.id.game_category_detail);
        mMinPrice = (TextView) rootView.findViewById(R.id.game_min_price_detail);
        mMaxPrice = (TextView) rootView.findViewById(R.id.game_max_price_detail);
        mAvgprice = (TextView) rootView.findViewById(R.id.game_average_price_detail);
        mGamePicture = (ImageView) rootView.findViewById(R.id.game_picture_detail);

        Realm realm =  Realm.getInstance(getActivity());
        Item item = realm.where(Item.class).equalTo("_id",mItemId).findFirst();
        mContext.getSupportActionBar().setTitle(item.getTitle());
        mTitle.setText(String.format(getResources().getString(R.string.game_title_detail_txt), item.getTitle()));
        mCategory.setText(String.format(getResources().getString(R.string.game_category_detail_txt), getCategoryName(item.getCategory())));
        mMinPrice.setText(String.format(getResources().getString(R.string.game_min_price_detail_txt), item.getLowerPrice()));
        mMaxPrice.setText(String.format(getResources().getString(R.string.game_max_price_detail_txt), item.getHigherPrice()));
        mAvgprice.setText(String.format(getResources().getString(R.string.game_average_price_detail_txt), item.getAveragePrice()));
        Picasso.with(getActivity()).load(Helper.COVER_URL + item.getCover()).placeholder(R.drawable.coin).into(mGamePicture);
    }

    private String getCategoryName(String category){
        if(category.equals("ps4-games")) return "PS4";
        else if(category.equals("ps3-games")) return "PS3";
        else if(category.equals("xbox-360-games")) return "XBOX 360";
        else if(category.equals("xbox-one-games")) return "XBOX ONE";
        else return "Wii U";
    }
}
