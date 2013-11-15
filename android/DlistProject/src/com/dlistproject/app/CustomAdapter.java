package com.dlistproject.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dlistproject.app.data.TodoTasksArray;

public class CustomAdapter extends BaseAdapter {
	private TodoTasksArray list;
	Context context;
	public CustomAdapter(Context context,TodoTasksArray objects) {
		this.context = context;
        this.list= objects;
		
	}

	@Override
	public int getCount() {
		return list.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        
        //creating LayoutInflator for inflating the row layout.
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
 
        //inflating the row layout we defined earlier.
        if(convertView == null ){
        	convertView = inflator.inflate(R.layout.individual_card, parent,false);
        }
        TextView descViewObj = (TextView) convertView.findViewById(R.id.descView);
        TextView creatorViewObj = (TextView) convertView.findViewById(R.id.creatorNameView);
        TextView acceptButtonViewObj = (TextView) convertView.findViewById(R.id.acceptButtonView);
        TextView locationObj = (TextView) convertView.findViewById(R.id.locationView);
        TextView statusObj = (TextView) convertView.findViewById(R.id.statusView);
        TextView priorityObj = (TextView) convertView.findViewById(R.id.priorityTextView);
        locationObj.setText(list.getPosition(position).getGeoLoc());
        statusObj.setText(list.getPosition(position).getStatus());
        if(list.getPosition(position).getPriority().equals("High")){
        	priorityObj.setBackgroundColor(Color.RED);
        }else
        if(list.getPosition(position).getPriority().equals("Medium")) {
        	priorityObj.setBackgroundColor(Color.YELLOW);
        	
        }else{
        	priorityObj.setBackgroundColor(Color.GREEN);
        	
        }
        descViewObj.setText(list.getPosition(position).getTitle());
        //setting the views into the ViewHolder.
        //return the row view.
        return convertView;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}

