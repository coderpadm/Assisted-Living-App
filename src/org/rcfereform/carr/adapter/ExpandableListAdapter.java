package org.rcfereform.carr.adapter;


 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import  org.rcfereform.carr.R;
import  org.rcfereform.carr.model.NavDrawerItem;
 
public class ExpandableListAdapter extends BaseExpandableListAdapter {
 
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private ArrayList<NavDrawerItem> navDrawerItems;
 
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
            HashMap<String, List<String>> listChildData ,  ArrayList<NavDrawerItem> navDrawerItems) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.navDrawerItems=navDrawerItems;
        		
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
 
        final String childText = (String) getChild(groupPosition, childPosition);
 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            
            convertView = infalInflater.inflate(R.layout.drawer_list_item, null);
        }
 
   
        
        TextView txtListChild = (TextView) convertView
           .findViewById(R.id.txtViewTitle);

 
        txtListChild.setText(childText);
        
       
		ImageView imgViewIcon=(ImageView)convertView.findViewById(R.id.imgViewIcon);

		imgViewIcon.setImageResource(navDrawerItems.get(childPosition%5).getIcon());
		
		TextView txtViewCounter=(TextView)convertView.findViewById(R.id.txtViewCounter);
		txtViewCounter.setVisibility(View.GONE);
		
	
		
		
		
        
        
        
        
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
          convertView=infalInflater.inflate(R.layout.group_list_item,null);
            
        
            
        }
 
        TextView lblListHeader = (TextView) convertView
        	             .findViewById(R.id.txtViewGrTitle);
        
 
        
        
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        
        
 
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
