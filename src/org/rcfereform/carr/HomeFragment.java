package org.rcfereform.carr;
import org.rcfereform.carr.footer.Footer;

import android.app.Fragment;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;





public class HomeFragment extends Fragment{
	
	
	public HomeFragment(){}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View rootView=inflater.inflate(R.layout.fragment_home, container, false);
		
		 TextView CARRTextView = (TextView)rootView.findViewById(R.id.txtViewCARR);
		 if (CARRTextView != null) {
			 CARRTextView.setMovementMethod(LinkMovementMethod.getInstance());

		 }
		new Footer(rootView);
		
		return rootView;
		
		
	}



}
