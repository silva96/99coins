package com.silva.benjamin.ninetyninecoins.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.activities.MainActivity;
import com.silva.benjamin.ninetyninecoins.fragments.ItemDetailFragment;
import com.silva.benjamin.ninetyninecoins.fragments.StarredFragment;
import com.silva.benjamin.ninetyninecoins.models.Item;
import com.silva.benjamin.ninetyninecoins.models.StarredItem;
import com.silva.benjamin.ninetyninecoins.util.Helper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by benjamin on 12/10/15.
 */
public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder>{

    private ArrayList<Item> mDataset;
    private MainActivity mContext;
    private String mType;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView mTitle;
        protected TextView mSellerCount;
        protected TextView mMinPrice;
        protected ImageView mPicture;
        protected ImageView mFavourite;
        protected CardView mCardView;




        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.game_title);
            mPicture = (ImageView) v.findViewById(R.id.game_picture);
            mFavourite = (ImageView) v.findViewById(R.id.game_favourite);
            mSellerCount = (TextView) v.findViewById(R.id.game_seller_count);
            mMinPrice = (TextView) v.findViewById(R.id.game_min_price);
            mCardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameListAdapter(ArrayList<Item> mDataset, MainActivity mContext) {
        this.mDataset = mDataset;
        this.mContext = mContext;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_card_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item i = mDataset.get(position);
        holder.mTitle.setText(i.getTitle());
        holder.mSellerCount.setText(i.getPriceCount() + " vendedores");
        holder.mMinPrice.setText("desde: $" + Helper.formatPrice(i.getLowerPrice()));
        Picasso.with(mContext).load(Helper.COVER_URL + i.getCover()).placeholder(R.drawable.coin).into(holder.mPicture);
        final Realm realm = Realm.getInstance(mContext);
        StarredItem starred = realm.where(StarredItem.class).equalTo("_id", i.get_id()).findFirst();
        if(starred!= null){
            holder.mFavourite.setImageResource(R.drawable.ic_action_favorite);
        }
        else{
            holder.mFavourite.setImageResource(R.drawable.ic_action_favorite_outline);
        }
        holder.mFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StarredItem starred = realm.where(StarredItem.class).equalTo("_id", i.get_id()).findFirst();
                if(starred!= null){
                    realm.beginTransaction();
                    starred.removeFromRealm();
                    realm.commitTransaction();
                    ((ImageView)v).setImageResource(R.drawable.ic_action_favorite_outline);
                }
                else{
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(new StarredItem(i.get_id()));
                    realm.commitTransaction();
                    ((ImageView)v).setImageResource(R.drawable.ic_action_favorite);
                }
            }
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("_id", i.get_id());
                mContext.openFragment(ItemDetailFragment.class,bundle, false);
            }
        });

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Item getItemAt(int position){
        return mDataset.get(position);
    }

}
