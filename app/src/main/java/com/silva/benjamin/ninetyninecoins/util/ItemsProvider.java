package com.silva.benjamin.ninetyninecoins.util;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.silva.benjamin.ninetyninecoins.R;
import com.silva.benjamin.ninetyninecoins.models.Item;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class ItemsProvider extends ContentProvider {
    public static final String AUTHORITY = "com.silva.benjamin.ninetyninecoins.itemsprovider";

    // UriMatcher constant for search suggestions
    private static final int SEARCH_SUGGEST = 1;

    private static final UriMatcher uriMatcher;

    private static final String[] SEARCH_SUGGEST_COLUMNS = {
            BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_ICON_1,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA
    };

    private MatrixCursor asyncCursor;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
    }

    @Override
    public int delete(Uri uri, String arg1, String[] arg2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues arg1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean onCreate() {
        asyncCursor = new MatrixCursor(SEARCH_SUGGEST_COLUMNS, 10);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Use the UriMatcher to see what kind of query we have
        switch (uriMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                String query = uri.getLastPathSegment();
                Realm realm = Realm.getInstance(getContext());
                RealmResults<Item> result = realm.where(Item.class).contains("title", query, Case.INSENSITIVE).findAll();
                MatrixCursor nCursor = new MatrixCursor(SEARCH_SUGGEST_COLUMNS);
                int counter = 1;
                for (Item i : result) {

                    nCursor.addRow(new Object[] {
                            counter, i.getTitle(), getDrawable(i.getCategory()), counter
                    });
                    counter ++;
                }
                asyncCursor = nCursor;
                return asyncCursor;
            default:
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    private int getDrawable(String category){
        if(category.equals("ps3-games")) return R.drawable.ps3;
        else if(category.equals("ps4-games")) return R.drawable.ps4;
        else if(category.equals("wii-u-games")) return R.drawable.wiiu;
        else if(category.equals("xbox-360-games")) return R.drawable.xbox360;


        else return R.drawable.xboxone;
    }

    @Override
    public int update(Uri uri, ContentValues arg1, String arg2, String[] arg3) {
        throw new UnsupportedOperationException();
    }
}