package com.laurietrichet.whatsnew.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static com.laurietrichet.whatsnew.database.WNContract.AUTHORITY;

/**
 * Content provider provides an API to interact with database.
 */
public class WNContentProvider extends ContentProvider {


    private static final Map<String, String> feedProjectionMap = new HashMap<String, String>();
    private static final Map<String, String> itemProjectionMap = new HashMap<String, String>();

    private static final int FEEDS = 1;
    private static final int FEED_ID = 2;
    private static final int FEED_ITEMS = 3;
    private static final int FEED_ITEMS_ID = 4;
    private static UriMatcher uriMatcher;

    private static final String STR_URI_FEED = "feeds";
    private static final String STR_URI_FEED_ID = "feeds/#";
    private static final String STR_URI_FEED_ITEM = "feeds/#/items";
    private static final String STR_URI_FEED_ITEM_ID = "feeds/#/items/#";

    static {
        // Create a new instance
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add a pattern that routes URIs terminated with "feeds" to a FEEDS operation
        uriMatcher.addURI(AUTHORITY, STR_URI_FEED, FEEDS);

        // Add a pattern that routes URIs terminated with "feeds" plus an integer
        // to a note ID operation
        uriMatcher.addURI(AUTHORITY, STR_URI_FEED_ID, FEED_ID);

        // Add a pattern that routes URIs terminated with "feeds/#/items" to a ITEMS operation
        uriMatcher.addURI(AUTHORITY, STR_URI_FEED_ITEM, FEED_ITEMS);

        // Add a pattern that routes URIs terminated with "feeds/#/items/" plus an integer
        uriMatcher.addURI(AUTHORITY, STR_URI_FEED_ITEM_ID, FEED_ITEMS_ID);

        // Add projection columns for a request on the feed table
        feedProjectionMap.put(FeedTable._ID, FeedTable._ID);
        feedProjectionMap.put(WNContract.COLUMN_NAME_TITLE, WNContract.COLUMN_NAME_TITLE);
        feedProjectionMap.put(WNContract.COLUMN_NAME_LINK, WNContract.COLUMN_NAME_LINK);
        feedProjectionMap.put(WNContract.COLUMN_NAME_DESCRIPTION, WNContract.COLUMN_NAME_DESCRIPTION);

        // Add projection columns for a request on the item table
        itemProjectionMap.put(ItemTable.COLUMN_NAME_FEED_ID_FK, ItemTable.COLUMN_NAME_FEED_ID_FK);
        itemProjectionMap.put(ItemTable._ID, ItemTable._ID);
        itemProjectionMap.put(WNContract.COLUMN_NAME_TITLE, WNContract.COLUMN_NAME_TITLE);
        itemProjectionMap.put(WNContract.COLUMN_NAME_LINK, WNContract.COLUMN_NAME_LINK);
        itemProjectionMap.put(WNContract.COLUMN_NAME_DESCRIPTION, WNContract.COLUMN_NAME_DESCRIPTION);
    }
    private SQLiteOpenHelper openHelper;

    @Override
    public boolean onCreate() {
        openHelper = new WNDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        fillQueryBuilder(queryBuilder, uri);

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = openHelper.getReadableDatabase();

        String orderBy;
        // If no sort order is specified, uses the default
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = getOrder(uri);
        } else {
            // otherwise, uses the incoming sort order
            orderBy = sortOrder;
        }

       /*
        * Performs the query. If no problems occur trying to read the database, then a Cursor
        * object is returned; otherwise, the cursor variable contains null. If no records were
        * selected, then the Cursor object is empty, and Cursor.getCount() returns 0.
        */
        Cursor cursor = queryBuilder.query(
                db,            // The database to query
                projection,    // The columns to return from the query
                selection,     // The columns for the where clause
                selectionArgs, // The values for the where clause
                null,          // don't group the rows
                null,          // don't filter by row groups
                orderBy        // The sort order
        );

        return cursor;
    }

    /**
     * convenient method to parse the uri given in argument in order to fill the query builder table,
     * projection map and where clause
     * @param queryBuilder
     * @param uri
     */
    private void fillQueryBuilder(SQLiteQueryBuilder queryBuilder, Uri uri){
        switch (uriMatcher.match(uri)) {
            case FEEDS:
                queryBuilder.setTables(FeedTable.TABLE_NAME);
                queryBuilder.setProjectionMap(feedProjectionMap);
                break;
            case FEED_ID:
                queryBuilder.setTables(FeedTable.TABLE_NAME);
                queryBuilder.setProjectionMap(feedProjectionMap);
                queryBuilder.appendWhere(
                        ItemTable.COLUMN_NAME_FEED_ID_FK + " = " +
                                uri.getLastPathSegment()
                );
                break;
            case FEED_ITEMS:
                queryBuilder.setTables(ItemTable.TABLE_NAME);
                queryBuilder.setProjectionMap(feedProjectionMap);
                break;
            case FEED_ITEMS_ID:
                queryBuilder.setTables(ItemTable.TABLE_NAME + WNContract.COMMA_SEP + FeedTable.TABLE_NAME);
                queryBuilder.setProjectionMap(itemProjectionMap);
                queryBuilder.appendWhere(
                        FeedTable.TABLE_NAME + "." + FeedTable._ID + " = " +
                                ItemTable.TABLE_NAME + "." + ItemTable.COLUMN_NAME_FEED_ID_FK
                );
                break;
            default:
                throw new IllegalArgumentException ("Unknown URI " + uri);
        }
    }

    private String getOrder (Uri uri) {
        String order;
        switch (uriMatcher.match(uri)) {
            case FEEDS:
            case FEED_ID:
                order = FeedTable.DEFAULT_SORT_ORDER;
                break;
            case FEED_ITEMS:
            case FEED_ITEMS_ID:
                order = ItemTable.DEFAULT_SORT_ORDER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return order;
    }

    @Override
    public String getType(Uri uri) {
        String mimeType;
        switch (uriMatcher.match(uri)) {
            case FEEDS:
                mimeType = FeedTable.MIME_TYPE_DIR;
                break;
            case FEED_ID:
                mimeType = FeedTable.MIME_TYPE_ITEM;
                break;
            case FEED_ITEMS:
                mimeType = ItemTable.MIME_TYPE_DIR;
                break;
            case FEED_ITEMS_ID:
                mimeType = ItemTable.MIME_TYPE_ITEM;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return mimeType;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (uri == null || values == null){
            throw new NullPointerException("Arguments must not be null.");
        }

        // Opens the database object in "write" mode.
        SQLiteDatabase database = openHelper.getWritableDatabase();
        long rowId; //the row id of the newly created row
        Uri noteUri; // path to the newly created row
        switch (uriMatcher.match(uri)) {
            case FEEDS:
                if (FeedTable.isValidForInsert(values) == false)
                    throw new IllegalArgumentException("Invalid argments for insert " + uri);
                // Performs the insert and returns the ID of the new note.
                rowId = database.insert(
                        FeedTable.TABLE_NAME,        // The table to insert into.
                        null,
                        values  // A map of column names, and the values to insert into the columns.
                );
                // Creates a URI with the feed ID pattern and the new row ID appended to it.
                noteUri = ContentUris.withAppendedId(FeedTable.CONTENT_URI, rowId);
                break;
            case FEED_ITEMS:
                ItemTable.isValidForInsert(values);
                // Performs the insert and returns the ID of the new note.
                rowId = database.insert(
                        ItemTable.TABLE_NAME,        // The table to insert into.
                        null,  // A hack, SQLite sets this column value to null if values is empty.
                        values  // A map of column names, and the values to insert
                        // into the columns.
                );
                noteUri = ContentUris.withAppendedId(ItemTable.CONTENT_URI, rowId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            // Notifies observers registered against this provider that the data changed.
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        // If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName;

        SQLiteDatabase database = openHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case FEEDS:
            case FEED_ID:
                tableName = FeedTable.TABLE_NAME;
                break;
            case FEED_ITEMS:
            case FEED_ITEMS_ID:
                tableName = ItemTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }
        int nbRowsDeleted = database.delete(
            tableName,
            selection,
            selectionArgs);

        return nbRowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName;
        SQLiteDatabase database = openHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case FEEDS:
            case FEED_ID:
                tableName = FeedTable.TABLE_NAME;
                break;
            case FEED_ITEMS:
            case FEED_ITEMS_ID:
                tableName = ItemTable.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);

        }

        int count = database.update(
                tableName,
                values,
                selection,
                selectionArgs
                );

        /*Gets a handle to the content resolver object for the current context, and notifies it
         * that the incoming URI changed. The object passes this along to the resolver framework,
         * and observers that have registered themselves for the provider are notified.
         */
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
