package org.rcfereform.carr.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.text.TextUtils;

import android.widget.Toast;
	 

public class CARRTermDB {
	

	

	    private static final String DBNAME = "carrterms";
	    private static final int VERSION = 3;
	    private CARRTermDBOpenHelper mCARRTermDBOpenHelper;
	    private static final String FIELD_ID = "_id";
	    private static final String FIELD_TERM = "term";

	    private static final String FIELD_DEFN = "definition";
	    private static final String FIELD_TYPE= "type";
	    private static final String TABLE_NAME = "carr";
	    
	   
	    

	    private HashMap<String, String> mAliasMap;
	    
	 
	 
	    public CARRTermDB(Context context){
	    
	    	
	        mCARRTermDBOpenHelper = new CARRTermDBOpenHelper(context, DBNAME, null, VERSION);
	       
	 
	        // This HashMap is used to map table fields to Custom Suggestion fields
	        mAliasMap = new HashMap<String, String>();
	 
	        // Unique id for the each Suggestions ( Mandatory )
	        mAliasMap.put("_ID", FIELD_ID + " as " + "_id" );
	 
	        // Text for Suggestions ( Mandatory )
	        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, FIELD_TERM + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);
	 
	
	 
	        // This value will be appended to the Intent data on selecting an item from Search result or Suggestions ( Optional )
	        mAliasMap.put( SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, FIELD_ID + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID );
	    }
	 
	    /** Returns CARR Terms */
	    public Cursor getCARRTerms(String[] selectionArgs){
	    	
	        String selection = FIELD_TERM + " like ? ";
	 
	        if(selectionArgs!=null){
	            selectionArgs[0] = "%"+selectionArgs[0] + "%";
	        }
	 
	        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	        queryBuilder.setProjectionMap(mAliasMap);
	 
	        queryBuilder.setTables(TABLE_NAME);
	 
	        Cursor c = queryBuilder.query(mCARRTermDBOpenHelper.getReadableDatabase(),
	            new String[] { "_ID",
	                            SearchManager.SUGGEST_COLUMN_TEXT_1 ,
	                         	SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID } ,
	            selection,
	            selectionArgs,
	            null,
	            null,
	            FIELD_TERM + " asc ","10"
	        );
	        return c;
	    }
	 
	    /** Return Term corresponding to the id */
	    public Cursor getCARRTerm(String id){
	 
	        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	 
	        queryBuilder.setTables(TABLE_NAME);
	 
	  
	        Cursor c = queryBuilder.query(mCARRTermDBOpenHelper.getReadableDatabase(), 
					new String[] { FIELD_ID, FIELD_TERM, FIELD_DEFN  } ,
					"_id = ?", new String[] { id } , null, null ,"1"
				);  	
	        
	        
	        
	        return c;
	    }
	    
	   
	    
	   
	    
	    /** Fetch Term */
	    public Cursor termFetch( String term){
	    	
	    
	    	
	        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	 
	        queryBuilder.setTables(TABLE_NAME);
	 
	  
	        Cursor c = queryBuilder.query(mCARRTermDBOpenHelper.getReadableDatabase(), 
					new String[] {FIELD_ID,FIELD_TERM, FIELD_DEFN  } ,
					"term = ?", new String[] { term } , null, null ,"1"
				);  	
	        
	        
	        
	        return c;
	    }
	    
	    
	    public void close(){
	    	 mCARRTermDBOpenHelper.close();
	    }
	    
	    static class CARRTermDBOpenHelper extends SQLiteOpenHelper{
	    	
	    	 private final Context mHelperContext;
	    	 private SQLiteDatabase mDatabase;
	 
	        public CARRTermDBOpenHelper( Context context,
	            String name,
	            CursorFactory factory,
	            int version ) {
	        	
	                super(context, DBNAME, factory, VERSION);
	                mHelperContext = context;
	                
	          
	             }
	
	        
  class InitializeTask extends AsyncTask<Integer, Integer, Void>{
	        	
	        	String sql = "";
	        	
				@Override
				protected Void doInBackground(Integer... params) {
					
					   // Creating table
					
					int i=params[0];
			        
		            
		            initializeDb();
		            publishProgress(i);
					return null;
				}

				
				@Override
				protected void onPreExecute() {
					
		           
		           
		 
		            // Defining table structure
		            sql =   " create table " + TABLE_NAME + "" +
		                    " ( " +
		                    FIELD_ID + " integer primary key autoincrement, " +
		                    FIELD_TERM + " text, " +
		             	    FIELD_DEFN + " text, " +
		                    FIELD_TYPE + " text "+
		                    
		                    " ) " ;
		 
		            mDatabase.execSQL(sql);
		        
				}


				/* (non-Javadoc)
				 * @see android.os.AsyncTask#onProgressUpdate(java.lang.Object[])
				 */
				@Override
				protected void onProgressUpdate(Integer... values) {
					Toast.makeText(mHelperContext,
	                        "Loading data...",
	                        Toast.LENGTH_SHORT).show();
				}

				
				
	        	
	        }
	        
	        
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	        	
	        	mDatabase=db;
	            new InitializeTask().execute(1);
	           
	
	           
	            }
	        
	        
	      
	        
	        private void initializeDb() {
	      
	                    try {
	                    //	long startTime = System.currentTimeMillis();
	                 
	                    	loadBatchTerms();
	                    //	long diff = System.currentTimeMillis() - startTime;
	                    	
	                   } catch (IOException e) {
	                        throw new RuntimeException(e);
	                   }
	             
	        }

	      
	        
	        
	        
	        
	        private void loadBatchTerms() throws IOException {
	        	
	        	
	        
	     
	           String fileName="FAQ.txt";
	     
	            	
	            AssetManager assetMgr=mHelperContext.getAssets();
	            
	            InputStream inputStream=assetMgr.open(fileName);
	            
	          
	      
	            InputStreamReader reader=new InputStreamReader(inputStream);
	
	            
	            Scanner sc=new Scanner(reader).useDelimiter("[%%]");
	          
	            try {
	            
	           mDatabase.beginTransaction();
	            	 
	            String line;
	            	
	         
	                
	                
	              while(sc.hasNext()){
	            	   line=sc.next();
	            	  
	          
	                    String[] strings = TextUtils.split(line, "###");
	                    
	                
	                    
	                   if (strings.length < 3) continue;
	                  
	                   addBatchWord(strings[0].trim(), strings[1].trim(),strings[2].trim());
	               
	                		  
	            }
	                
	              mDatabase.setTransactionSuccessful();	   
	            	  
	            } finally {
	            	inputStream.close();
	            	reader.close();
	                sc.close();
	                mDatabase.endTransaction();	
	              
	            }
	          
	        }
	       
	 
          public void addBatchWord(String type, String term, String definition) {
	       
	           ContentValues initialValues = new ContentValues();
	            
	            
	           initialValues.put(FIELD_TYPE, type);
	           initialValues.put(FIELD_TERM, term);
	           initialValues.put(FIELD_DEFN,definition);
	           

	           
	           mDatabase.insertWithOnConflict(TABLE_NAME, null, initialValues, 0);
        	
	           
	        }
	        
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    
	             
	        
	        	  db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
	        	
	        	
	             
	              // create new table
	              onCreate(db);
	        
	        }
	   
	    }


}
