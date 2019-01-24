package uop.mbl404_team_b.helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import uop.mbl404_team_b.MainActivity;
import uop.mbl404_team_b.R;
import uop.mbl404_team_b.fragments.TabContacts;
import uop.mbl404_team_b.fragments.TabLocations;
import uop.mbl404_team_b.models.ContactModel;

public class CustomListViewAdapter extends BaseAdapter {

    private Activity mContext;
    private ArrayList<ContactModel> mlistLocations;
    private float[] mRatings;
    private static LayoutInflater inflater = null;

    public CustomListViewAdapter(Activity context, ArrayList<ContactModel> locationModelList) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //assign values to private variables
        mContext = context;
        mlistLocations = locationModelList;
    }

    @Override
    public int getCount() {
        return mlistLocations.size();
    }

    //get item method returns the location model info of the selected item
    @Override
    public Object getItem(int i) {
        return mlistLocations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    //generates each row view
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = mContext.getLayoutInflater();
        View v = mInflater.inflate(R.layout.listview_row, null);

        final int itemPos = position;
        //instantiates all controls
        TextView tvName = (TextView) v.findViewById(R.id.txtViewName);
        Button btnDetails = (Button) v.findViewById(R.id.btnDetails);
        RatingBar rateBar = (RatingBar) v.findViewById(R.id.ratingBar);

        //set control values
        tvName.setText(mlistLocations.get(position).getName());
        rateBar.setRating(mlistLocations.get(position).getRating());

        //button click event for button Details to go to the contacts tab
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TabContacts.PopulateContactInfo((ContactModel)getItem(itemPos));
                TabLocations.CONTACT_SELECTED_INDEX = mlistLocations.get(itemPos).getId(); //ADDED 11/25 **********************
                TabContacts.refreshDisplay();
                MainActivity.setCurrentTab(3);
            }
        });

        return v;
    }
}
