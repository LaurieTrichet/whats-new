package com.laurietrichet.whatsnew.core.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.laurietrichet.whatsnew.core.database.FeedTable;
import com.laurietrichet.whatsnew.core.database.WNContentProvider;
import com.laurietrichet.whatsnew.core.database.WNContract;

import junit.framework.Assert;

public class WNContentProviderTest extends ProviderTestCase2<WNContentProvider> {

    // Contains a reference to the mocked content resolver for the provider under test.
    private MockContentResolver mockContentResolver;

    private static String feedTitle = "Weather Warnings for Victoria. Issued by the Australian Bureau of Meteorology";
    private static String feedLink = "http://www.bom.gov.au/fwo/IDZ00059.warnings_vic.xml";
    private static String feedDescription = "Current weather warnings for Victoria, Australia including strong wind, gale, storm force and hurricane force wind warnings; tsunami; damaging waves; abnormally high tides; tropical cyclones; severe thunderstorms; severe weather; fire weather; flood; frost; driving; bushwalking; sheep graziers and other agricultural warnings.";


    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public WNContentProviderTest(Class<WNContentProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    public WNContentProviderTest (){
        super(WNContentProvider.class, WNContract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockContentResolver = getMockContentResolver();

        createRows ();
    }

    private void createRows (){
        String feedTitle = "The Australian | News";
        String feedLink = "http://www.theaustralian.news.com.au/";
        String feedDescription = "News, views and information from the pages of The Australian";

        ContentValues contentValues = new ContentValues();
        contentValues.put(WNContract.COLUMN_NAME_TITLE, feedTitle);
        contentValues.put(WNContract.COLUMN_NAME_LINK, feedLink);
        contentValues.put(WNContract.COLUMN_NAME_DESCRIPTION, feedDescription);

        mockContentResolver.insert(FeedTable.CONTENT_URI, contentValues);
    }

    public void testOnCreate() throws Exception {
        Assert.fail();
    }

    public void testQuery() throws Exception {

        Uri uri = FeedTable.CONTENT_URI;

        String feedLink = "http://www.theaustralian.news.com.au/";

        String [] selectionArgs = {feedLink};

        Cursor cursor = mockContentResolver.query(uri,
                FeedTable.READ_FEED_PROJECTION,
                FeedTable.SEARCH_LINK_SELECTION,
                selectionArgs,
                null);

        Assert.assertNotNull(cursor);
        Assert.assertEquals(1,cursor.getCount());
    }

    public void testGetType() throws Exception {
        Assert.fail();
    }

    public void testInsert() throws Exception {

        String feedTitle = "Liftoff News";
        String feedLink = "http://liftoff.msfc.nasa.gov/";
        String feedDescription = "Liftoff to Space Exploration.";

        ContentValues contentValues = new ContentValues();
        contentValues.put(WNContract.COLUMN_NAME_TITLE, feedTitle);
        contentValues.put(WNContract.COLUMN_NAME_LINK, feedLink);
        contentValues.put(WNContract.COLUMN_NAME_DESCRIPTION, feedDescription);

        Uri uri = mockContentResolver.insert(FeedTable.CONTENT_URI, contentValues);

        Assert.assertNotNull(uri);

    }

    public void testDelete() throws Exception {
        insertTestFeedRow();

        String [] selectionArgs = {feedLink};

        //delete a record
        int count = mockContentResolver.delete(FeedTable.CONTENT_URI,
                FeedTable.SEARCH_LINK_SELECTION,
                selectionArgs
                );

        //test that the nulber of deleted rows equals 1
        Assert.assertEquals(1, count);

        //test that the record no longer exists.
        Cursor cursor = mockContentResolver.query(
                FeedTable.CONTENT_URI,
                FeedTable.READ_FEED_PROJECTION,
                FeedTable.SEARCH_LINK_SELECTION,
                selectionArgs,
                null
        );

        Assert.assertEquals(0, cursor.getCount());
    }

    public void testUpdate() throws Exception {
        insertTestFeedRow ();
        String newFeedTitle = "New title for testing.";

        ContentValues contentValues = new ContentValues();
        contentValues.put(WNContract.COLUMN_NAME_TITLE, newFeedTitle);
        String [] selectionArgs = {feedLink};
        int count = mockContentResolver.update(
                FeedTable.CONTENT_URI,
                contentValues,
                FeedTable.SEARCH_LINK_SELECTION,
                selectionArgs);
        Assert.assertEquals(1, count);

        Cursor cursor = mockContentResolver.query(
                FeedTable.CONTENT_URI,
                FeedTable.READ_FEED_PROJECTION,
                FeedTable.SEARCH_LINK_SELECTION,
                selectionArgs,
                null
        );

        Assert.assertTrue(cursor.moveToNext());

        int titleIndex = cursor.getColumnIndex(WNContract.COLUMN_NAME_TITLE);
        //test that the title is the one we changed
        Assert.assertEquals(cursor.getString(titleIndex),newFeedTitle);
    }

    private void insertTestFeedRow (){

        ContentValues contentValues = new ContentValues();
        contentValues.put(WNContract.COLUMN_NAME_TITLE, feedTitle);
        contentValues.put(WNContract.COLUMN_NAME_LINK, feedLink);
        contentValues.put(WNContract.COLUMN_NAME_DESCRIPTION, feedDescription);

        Uri uri = mockContentResolver.insert(FeedTable.CONTENT_URI, contentValues);
        Assert.assertNotNull(uri);
    }
}