package org.rcfereform.carr;


import java.util.regex.Pattern;

import org.rcfereform.carr.footer.Footer;


import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;

import android.view.View;
import android.widget.TextView;

public class CARRTermActivity  extends Activity implements LoaderCallbacks<Cursor> {
	

    private Uri mUri;
    

    private TextView txtViewDefn;
    private TextView txtViewHeader;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
	
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_carr_term);
		
	

				
		View rootView=getWindow().getDecorView().findViewById(android.R.id.content);
		
		 
        Intent intent = getIntent();
        mUri = intent.getData();
        
    
    
   
        txtViewDefn = (TextView) findViewById(R.id.txtViewDefn);
        txtViewHeader=(TextView) findViewById(R.id.txtViewHeader);
        
 
       
     // Invokes the method onCreateloader() in non-ui thread
     		
     getLoaderManager().initLoader(0, null, this);
     new Footer(rootView);
       
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		return new CursorLoader(getBaseContext(), mUri, null, null , null, null);
    }
	

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		

		if(cursor != null  && 
				cursor.getCount() > 0){
		
			 
		if(cursor.moveToFirst()){
			
			 txtViewDefn.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
			 txtViewHeader.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
			 
			
			 
			 Linkify.addLinks(txtViewDefn, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);
		     
		     PackageManager pm = getPackageManager();
		     String carrContentURL =    "content://org.rcfereform.carr.carrcontentProvider/terms/";

		     Intent testIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(carrContentURL));
		     boolean activityExists = testIntent.resolveActivity(pm) != null;
		         
		     // If there is an Activity capable of viewing the content
		     // Linkify the text.
		     if (activityExists) {
		  
		    	 Pattern carrWordMatcher;			     
		    	 carrWordMatcher = Pattern.compile("(?<=See ).*?(?=\\.)");
		    	 Linkify.addLinks(txtViewDefn, carrWordMatcher, carrContentURL);
		    	 carrWordMatcher = Pattern.compile("(?<=.(Also - see | Also see )).*?(?=\\.)");
		    	 Linkify.addLinks(txtViewDefn, carrWordMatcher, carrContentURL);
		   
		     }
		        
			 
        }

		}
		
		
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
		
	}

	
	
	
}

