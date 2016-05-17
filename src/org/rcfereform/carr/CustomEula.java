
package org.rcfereform.carr;


import android.app.Activity;
import android.app.Dialog;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.text.Html;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;


public class CustomEula {
 
    private String EULA_PREFIX = "eula_";
    private Activity mActivity;
 
    public CustomEula(Activity context) {
        mActivity = context;
    }
 
    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
             pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }
 
     public void show() {
        PackageInfo versionInfo = getPackageInfo();
 
        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String eulaKey = EULA_PREFIX + versionInfo.versionCode;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShown = prefs.getBoolean(eulaKey, false);
      if(hasBeenShown == false){
 
            // Show the Eula
            String title = mActivity.getString(R.string.app_name) + " v" + versionInfo.versionName;
 
            
         
 
            final Dialog dialog = new Dialog(mActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
            dialog.setContentView(R.layout.custom_eula);
            
            dialog.setTitle(title);
            
    		
            TextView txtViewEulaHeader=(TextView) dialog.findViewById(R.id.txtViewEulaHeader);
            txtViewEulaHeader.setText(Html.fromHtml(mActivity.getString(R.string.custom_eula_heading)));          
            
		
            TextView txtViewEula=(TextView) dialog.findViewById(R.id.txtEula);
            txtViewEula.setText(Html.fromHtml(mActivity.getString(R.string.custom_eula_formatted)));
            
            
			Button dialogButton = (Button) dialog.findViewById(R.id.btnDialogOK);
			
			
			// if button is clicked, close the custom dialog
						dialogButton.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								 // Mark this version as read.
	                            SharedPreferences.Editor editor = prefs.edit();
	                            editor.putBoolean(eulaKey, true);
	                            editor.commit();
	                            
								dialog.dismiss();
							}
						});
					
			 
						dialog.show();
						Window window = dialog.getWindow();
						
						window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						
						
			
           
       }
    }
 
}