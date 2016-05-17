


package org.rcfereform.carr;

import org.rcfereform.carr.model.CARRTermDB;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;



public class CARRContentProvider extends ContentProvider {
	
	
	
	public static final String AUTHORITY = "org.rcfereform.carr.carrcontentprovider";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/terms" );
	
	

	 CARRTermDB mCARRTermDB = null;

     private static final int SUGGESTIONS_CARR = 1;
     private static final int SEARCH_CARR = 2;
     private static final int GET_CARR = 3;
     private static final int FETCH_CARR = 4;
     
     
    
     UriMatcher mUriMatcher = buildUriMatcher();
     
     private UriMatcher buildUriMatcher(){
    	 UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);  	 
    	 
    	 // Suggestion items of Search Dialog is provided by this uri
    	 uriMatcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY,SUGGESTIONS_CARR);
    	 
    	 // This URI is invoked, when user presses "Go" in the Keyboard of Search Dialog
    	 // Listview items of SearchableActivity is provided by this uri    	 
    	 // See android:searchSuggestIntentData of searchable.xml
    	 uriMatcher.addURI(AUTHORITY, "terms", SEARCH_CARR);
    	 
    	 // This URI is invoked, when user selects a suggestion from search dialog or an item from the listview
    	 // Term details for CARRTermActivity is provided by this uri    	 
    	 // See, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID in CARRTermDB.java
    	 uriMatcher.addURI(AUTHORITY, "terms/#", GET_CARR);
    	 
    	 
         uriMatcher.addURI(AUTHORITY, "terms/*", FETCH_CARR);
    	 
    	 
    	 return uriMatcher;
     }
     
     
     @Override
	 public boolean onCreate() {
 
    	
 		
    	 mCARRTermDB = new CARRTermDB(getContext());
		 	return true;
	 }
     
     @Override
	 public Cursor query(Uri uri, String[] projection, String selection,
			 String[] selectionArgs, String sortOrder) {
    	 
    	 Cursor c = null;
    	 switch(mUriMatcher.match(uri)){
    	 case SUGGESTIONS_CARR :
   
    		 c =  mCARRTermDB.getCARRTerms(selectionArgs);
    		 break;
    	 case SEARCH_CARR :
    
    		 c =  mCARRTermDB.getCARRTerms(selectionArgs);
    		 break;
    	 case GET_CARR :
 
    		 String id = uri.getLastPathSegment();
    		 c =  mCARRTermDB.getCARRTerm(id); 
    		 break;
    		
    	 case FETCH_CARR :
    
    		 String term = uri.getLastPathSegment();
    		 c =  mCARRTermDB.termFetch(term); 
    		 break;
    	 }

    	 return c;
    	 
	}     

	 @Override
	 public int delete(Uri uri, String selection, String[] selectionArgs) {
		 	throw new UnsupportedOperationException();
	 }

	 @Override
	 public String getType(Uri uri) {
		 
		 	throw new UnsupportedOperationException();
		
	 }

	 @Override
	 public Uri insert(Uri uri, ContentValues values) {
		 	throw new UnsupportedOperationException();
	 }	 
	 

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
	 		throw new UnsupportedOperationException();
	}
}
