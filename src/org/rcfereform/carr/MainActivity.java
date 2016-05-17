package org.rcfereform.carr;

 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.rcfereform.carr.adapter.ExpandableListAdapter;
import org.rcfereform.carr.model.NavDrawerItem;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;



import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SearchView;



 
public class MainActivity extends Activity {
 
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    
    
    private String[] navMenuTitles;
    private String[] navMenuFirstSet,navMenuSecondSet,navMenuThirdSet,navMenuFourthSet,navMenuFifthSet;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
    

	private DrawerLayout mDrawerLayout;
	
	private ActionBarDrawerToggle mDrawerToggle;
	
	
	
	private CharSequence mTitle;
	//Added for eula
	private boolean eulaFlag;
	
	

	
	
	
	
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       

       
    
       new CustomEula(this).show();
      
        
        
        
        
        
      
      eulaFlag=true;
        
       
       

		navMenuTitles=getResources().getStringArray(R.array.nav_drawer_items);
  		
  		//load sliding menu icons
  		navMenuIcons=getResources().obtainTypedArray(R.array.nav_drawer_icons);
  		
  	    navMenuFirstSet=getResources().getStringArray(R.array.nav_drawer_first_item);
  	    navMenuSecondSet=getResources().getStringArray(R.array.nav_drawer_second_item);
  	    navMenuThirdSet=getResources().getStringArray(R.array.nav_drawer_third_item);
  	    navMenuFourthSet=getResources().getStringArray(R.array.nav_drawer_fourth_item);
  	    navMenuFifthSet=getResources().getStringArray(R.array.nav_drawer_fifth_item);
     
        
        
    		
    	
   		
        
        
        mTitle=getTitle(); // inbuilt Activity class's application title
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
       
        getActionBar().setHomeButtonEnabled(true);
        
		
		mDrawerToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
				
				R.string.app_name, 
R.string.app_name){
			public void onDrawerClosed (View drawerView){
		
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // To show Action bar icons
				
			}
			
			public void onDrawerOpened (View drawerView){
	
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // to hide Action bar icons
				
				InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
			    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			    
			}
			
			
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	
        
        
        


     
		
                	  // get the listview
                      expListView = (ExpandableListView) findViewById(R.id.list_slidermenu);
                      
                      new PrepTask().execute(1);

                      if(savedInstanceState == null){
  		    	
  		    			// first time display the view of the first navigation item
  		    		displayView(-1,-1);
  		    		}
    }
    				
                      class PrepTask extends AsyncTask<Integer, Integer, Void>{

						@Override
						protected Void doInBackground(Integer... params) {
							int i=params[0];
					
				      		
				      		navDrawerItems=new ArrayList<NavDrawerItem>();
				      		
				      		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],navMenuIcons.getResourceId(0,-1)));
				    		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],navMenuIcons.getResourceId(1,-1)));
				    		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],navMenuIcons.getResourceId(2,-1)));
				    		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],navMenuIcons.getResourceId(3,-1)));
				    		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],navMenuIcons.getResourceId(4,-1)));
				 
				      		
				      		
				    	
				    		navMenuIcons.recycle();
							 // preparing list data
		                      prepareListData();
		                      publishProgress(i);
							return null;
						}

						@Override
						protected void onPreExecute() {
							
							super.onPreExecute();
						}

						@Override
						protected void onPostExecute(Void result) {
							
							super.onPostExecute(result);
							
							
						}

						@Override
						protected void onProgressUpdate(Integer... values) {
							listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild, navDrawerItems);
				               
		                      // setting list adapter
		                      expListView.setAdapter(listAdapter);
		                      
		                  	
		               
		                      // Listview Group click listener
		                      expListView.setOnGroupClickListener(new OnGroupClickListener() {
		               
		                          @Override
		                          public boolean onGroupClick(ExpandableListView parent, View v,
		                                  int groupPosition, long id) {
		   
		                              return false;
		                          }
		                      });
		               
		                      // Listview Group expanded listener
		                      expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
		               
		                          @Override
		                          public void onGroupExpand(int groupPosition) {
		                     
		                          }
		                      });
		               
		                      // Listview Group collapsed listener
		                      expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
		               
		                          @Override
		                          public void onGroupCollapse(int groupPosition) {
		                             
		                    
		               
		                          }
		                      });
		               
		                      // Listview on child click listener
		                      expListView.setOnChildClickListener(new OnChildClickListener() {
		               
		                          @Override
		                          public boolean onChildClick(ExpandableListView parent, View v,
		                                  int groupPosition, int childPosition, long id) {
		                        
		                              displayView(groupPosition,childPosition);
		                              return false;
		                          }
		                      });
		                      
		                      
		                     
		              		
		               
		       
				 
		        
						}

					
                    	  
                    	  
                      }
               
                     
                      
   

    private void prepareListData() {
    	
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        
        listDataHeader.add(navMenuTitles[0]);
        listDataHeader.add(navMenuTitles[1]);
        listDataHeader.add(navMenuTitles[2]);
        listDataHeader.add(navMenuTitles[3]);
        listDataHeader.add(navMenuTitles[4]);
     
      
       
        // Adding child data
        
        List<String> firstSet = new ArrayList<String>();
       
        for(int i=0; i<navMenuFirstSet.length;i++){
      
        	firstSet.add(navMenuFirstSet[i]);}
        
        List<String> secondSet = new ArrayList<String>();
      
        for(int i=0; i<navMenuSecondSet.length;i++){
        	
        	secondSet.add(navMenuSecondSet[i]);}
        
        List<String> thirdSet = new ArrayList<String>();
     
        for(int i=0; i<navMenuThirdSet.length;i++){
        	
        	thirdSet.add(navMenuThirdSet[i]);}
        
        List<String> fourthSet = new ArrayList<String>();
        
        for(int i=0; i<navMenuFourthSet.length;i++){
        
        	fourthSet.add(navMenuFourthSet[i]);}
        
        List<String> fifthSet = new ArrayList<String>();
       
        for(int i=0; i<navMenuFifthSet.length;i++){
        
        	fifthSet.add(navMenuFifthSet[i]);}
        
        
        
        
        
      listDataChild.put(listDataHeader.get(0), firstSet); // Header, Child data
      listDataChild.put(listDataHeader.get(1), secondSet);
      listDataChild.put(listDataHeader.get(2), thirdSet);
      listDataChild.put(listDataHeader.get(3), fourthSet); // Header, Child data
      listDataChild.put(listDataHeader.get(4), fifthSet);

    }
    
  //Display the corresponding fragment view for the selected navigation drawer item
  	private void displayView(int groupPosition, int childPosition){
  
  		Fragment fragment=null;
  		
  		
  		String term=null;
  		if(groupPosition !=-1){
  		 term= listDataChild.get(
                 listDataHeader.get(groupPosition)).get(
                 childPosition);
  		}
  		
  		
  		
  		switch(childPosition){
  		
  		
  		
  		case -1:fragment = new HomeFragment();break;
  		
  		
  		default: 
  		Bundle args=new Bundle();
  		args.putString("term", term);
  		fragment = new TermFragment(); fragment.setArguments(args); break;
  		
  		
  		}
  		
  		if (fragment!=null)
  		{
  			FragmentManager fragmentManager=getFragmentManager();
  			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
  	
  			
  			//update the selected item and close the drawer
  			expListView.setItemChecked((childPosition+groupPosition+1), true);
  			expListView.setSelection((childPosition+groupPosition+1));
  			mDrawerLayout.closeDrawer(expListView);
  		}
  		
  	}

  	@Override
  	public boolean onCreateOptionsMenu(Menu menu) {
  		
  		// Inflate the menu; this adds items to the action bar if it is present.
  		getMenuInflater().inflate(R.menu.main, menu);
  		
  		
  	//Associate searchable configuration with SearchView
  			SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
  			SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
  			
  			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
  			
  		
  			return super.onCreateOptionsMenu(menu);
  		
  	
  	}


  	@Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  	
  		
  		// Open and close navigation drawer on clicking app icon / title
  		if(mDrawerToggle.onOptionsItemSelected(item))
  		{
  			return true; 
  		}		
  		switch(item.getItemId()){
  		//handle action bar action clicks
  		
  		
  		case R.id.action_search:
  			return true;
  			
  		case R.id.action_glossary_fragment:
  			Fragment gFragment=null;
  			gFragment=new GlossaryFragment();
  			if(gFragment!=null){
  				FragmentManager fragmentManager=getFragmentManager();
  	  			fragmentManager.beginTransaction().replace(R.id.frame_container, gFragment).commit();
  	  			
  	  			InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
  	  			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
  	  
  				
  			}
  		
  		
  				
			break;	
  		
  		
  		default:
  			return super.onOptionsItemSelected(item);
  		
  		}
  		return true;
  		
  	}

    
    
    
    
    
  //Called when invalidateOptionsMenu() is used

  	@Override
  	public boolean onPrepareOptionsMenu(Menu menu) {
  		
  		if(eulaFlag==false){
  			
  	  		menu.findItem(R.id.action_glossary_fragment).setVisible(false);
  	  		menu.findItem(R.id.action_search).setVisible(false);
  		}
  		boolean drawerOpen=mDrawerLayout.isDrawerOpen(expListView);
  	
  	
  		menu.findItem(R.id.action_search).setVisible(!drawerOpen);
  		menu.findItem(R.id.action_glossary_fragment).setVisible(!drawerOpen);
  		
  		
  		
  		return super.onPrepareOptionsMenu(menu);
  	}


  	@Override
  	public void setTitle(CharSequence title) {
  		
  		mTitle=title;
  		getActionBar().setTitle(mTitle);
  	}

  	// The below methods are for ActionBarDrawerToggle

  	@Override
  	protected void onPostCreate(Bundle savedInstanceState) {

  		super.onPostCreate(savedInstanceState);
  		
  		//Sync the toggle state after onRestoreInstanceState has occurred
  		mDrawerToggle.syncState();
  	}


  	@Override
  	public void onConfigurationChanged(Configuration newConfig) {
  	
  		super.onConfigurationChanged(newConfig);
  		
  		//Pass the configuration change to drawer toggle
  		mDrawerToggle.onConfigurationChanged(newConfig);
  	}
  	
  	
}
