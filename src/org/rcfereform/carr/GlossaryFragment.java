package org.rcfereform.carr;


import java.util.regex.Pattern;

import org.rcfereform.carr.footer.Footer;
import org.rcfereform.carr.model.CARRTermDB;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.util.Linkify;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import android.widget.TextView;




public class GlossaryFragment extends Fragment{
	public TextView glossaryTextView,defTextView;
	
	CARRTermDB mCARRTermDB;
	
	public GlossaryFragment(){
		
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView=inflater.inflate(R.layout.fragment_glossary, container, false);


		final String[] glossary=getResources().getStringArray(R.array.nav_drawer_glossary);
		
		final ViewPager dispViewPager=(ViewPager)rootView.findViewById(R.id.viewPagerDisp);


		dispViewPager.setAdapter(new PagerAdapter(){
			ImageView imgView;
			
			TextView txtViewGlossDef;
			
			  Integer img=R.drawable.ic_glossary;
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View)object);
			}

			@Override
			public int getCount() {
	
				
				 return glossary.length;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
		
				
				LayoutInflater inflater = (LayoutInflater) container.getContext()
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				 View glossView = inflater.inflate(R.layout.pager_glossary, null);
				 imgView=(ImageView)glossView.findViewById(R.id.imgViewGloss);
			
				 txtViewGlossDef=(TextView)glossView.findViewById(R.id.txtViewGlossDef);
			
				 
				 
				 
				 imgView.setImageResource(img);
			
				 

				if(glossary[position] !=null)
					{
					Cursor cursor=mCARRTermDB.termFetch(glossary[position]);
					InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
					if(inputMethodManager.isAcceptingText()){
				    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);}
					if(cursor != null  && 
							cursor.getCount() > 0){
						
						 
					if(cursor.moveToFirst()){
			
						
			     ((TextView)txtViewGlossDef).setText(cursor.getString(2));
			     
			   
			     
			     Linkify.addLinks(txtViewGlossDef, Linkify.PHONE_NUMBERS | Linkify.WEB_URLS);
			     
			     PackageManager pm = getActivity().getPackageManager();
			     String carrContentURL =    "content://org.rcfereform.carr.carrcontentProvider/terms/";
			
			     Intent testIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(carrContentURL));
			     boolean activityExists = testIntent.resolveActivity(pm) != null;
			         
			     // If there is an Activity capable of viewing the content
			     // Linkify the text.
			     if (activityExists) {
			
			
			    	 Pattern carrWordMatcher;			     
			    	 carrWordMatcher = Pattern.compile("(?<=See ).*?(?=\\.)");
			  
			     
			   Linkify.addLinks(txtViewGlossDef, carrWordMatcher, carrContentURL);
			   carrWordMatcher = Pattern.compile("(?<=.(Also - see | Also see )).*?(?=\\.)");
			   Linkify.addLinks(txtViewGlossDef, carrWordMatcher, carrContentURL);
			 
			     }
		
					
					}
					}
					}
			
				 ((ViewPager) container).addView(glossView, 0);
				 return glossView;
				
				
				
				
				
			}

			@Override
			public boolean isViewFromObject(View view, Object obj) {
				return view==(View)obj;
			}
			
			
			
		
			 
			@Override
			public CharSequence getPageTitle(int position) {
			    return glossary[position];
			}

			
			
			
	
		});
		
		

		
	dispViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
		    @Override
		    public void transformPage(View page, float position) {
		        // do transformation here

		    	 if(getResources().getDisplayMetrics().widthPixels <getResources().getDisplayMetrics().heightPixels)
		    	 {//Portrait mode
		    	 page.setRotationY(position * -60);}
		    	 else{
		    	//landscape mode
		    	 page.setRotationX(position*18);	 
		    		 
		    	 }
		    	 
		    	 
		        }
		});
	
	dispViewPager.setOffscreenPageLimit(1);
	
	
		
		new Footer(rootView);
		
		return rootView;
		
		
	}



		@Override
		public void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			
			mCARRTermDB=new CARRTermDB(getActivity().getApplicationContext());
			
		}
		
		

}
