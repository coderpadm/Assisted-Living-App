package org.rcfereform.carr.footer;

import org.rcfereform.carr.R;

import android.text.method.LinkMovementMethod;

import android.view.View;
import android.widget.TextView;

public class Footer {
	
	public Footer (View rootView){

	TextView aboutCARRTextView = (TextView)rootView.findViewById(R.id.txtViewAboutCARR);	
	
	 if (aboutCARRTextView != null) {
	   aboutCARRTextView.setMovementMethod(LinkMovementMethod.getInstance());
	 
	 }
	 TextView aboutDevTextView = (TextView)rootView.findViewById(R.id.txtViewAboutDev);
	 if (aboutDevTextView != null) {
	   aboutDevTextView.setMovementMethod(LinkMovementMethod.getInstance());
	
	 }
}
}
