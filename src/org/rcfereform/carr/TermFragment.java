package org.rcfereform.carr;
import java.util.regex.Pattern;

import org.rcfereform.carr.footer.Footer;
import org.rcfereform.carr.model.CARRTermDB;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;

import android.content.Loader;
import android.text.Html;
import android.text.util.Linkify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TermFragment extends Fragment implements  LoaderCallbacks<Cursor>{
	String term=null;
	CARRTermDB mCARRTermDB;
	private Uri mUri;
	TextView txtViewDefn ;
	TextView txtViewTitle;
	
	
	
	public TermFragment(){}
	
	
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView=inflater.inflate(R.layout.fragment_term, container, false);
		
	
		 txtViewDefn = (TextView)rootView.findViewById(R.id.txtViewDefinition);
		txtViewTitle= (TextView)rootView.findViewById(R.id.txtViewHeader);
		
	
		
		
		
		if(term !=null)
		{
			 // Invokes the method onCreateloader() in non-ui thread
			 getLoaderManager().initLoader(1, null, this);}
     		
		new Footer(rootView);
		
		return rootView;
		
		
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		term = getArguments() != null ? getArguments().getString("term") : null;
		mCARRTermDB=new CARRTermDB(getActivity().getApplicationContext());
		
		
		
		mUri=Uri.withAppendedPath(CARRContentProvider.CONTENT_URI, formatTerm(term));
        

       
	}
	

	
	 public String stripHtml(String html) {
	        return Html.fromHtml(html).toString();
	    }
	 
	 public String formatTerm(String str){
		
		
		 
		return(str.replaceAll("[\\p{C}\\p{Z}]", " "));
		 
		
		 
		
	 
	 }





	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		
		return new CursorLoader(getActivity().getBaseContext(), mUri, null, null , null, null);
	}





	
	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor1) {
		
		if(term !=null)
		{
	Cursor cursor=mCARRTermDB.termFetch(formatTerm(term));

		if(cursor != null  && 
				cursor.getCount() > 0){
			
			 
		if(cursor.moveToFirst()){

     (txtViewDefn).setText(cursor.getString(2));
     (txtViewTitle).setText(term);
     
     
     
  
     Linkify.addLinks(txtViewDefn, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);
     
     PackageManager pm = getActivity().getPackageManager();
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
		
	}





	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		
		
	}

}
