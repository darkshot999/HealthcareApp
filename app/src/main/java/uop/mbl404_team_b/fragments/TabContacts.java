package uop.mbl404_team_b.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import uop.mbl404_team_b.MainActivity;
import uop.mbl404_team_b.R;
import uop.mbl404_team_b.models.ContactModel;


public class TabContacts extends Fragment {
    //Variables
    private static TextView clinicName;
    private static TextView phoneNumber;
    private static TextView faxNumber;
    private static TextView website;
    private static TextView address;
    public static ContactModel currentContact;

    private static Activity myActivity;

    //default constructor
    public TabContacts(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        //instantiate the control variables
        clinicName = (TextView)rootView.findViewById(R.id.lblClinicName);
        phoneNumber = (TextView)rootView.findViewById(R.id.lnkPhone);
        faxNumber = (TextView)rootView.findViewById(R.id.lnkFax);
        website = (TextView)rootView.findViewById(R.id.lnkWebsite);
        address = (TextView)rootView.findViewById(R.id.lnkAddress);

        //set the onclick listener for the button
        rootView.findViewById(R.id.btnAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentContact != null) {
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, currentContact.getName());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, currentContact.getPhone());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "No contact info to save.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    public static void refreshDisplay() {
        PopulateContactInfo();
    }

    private static void PopulateContactInfo(){
        int index = TabLocations.CONTACT_SELECTED_INDEX;
        if(index >= 0){
            currentContact = MainActivity.dbHelper.getContact(index);
            clinicName.setText(currentContact.getName());
            phoneNumber.setText(currentContact.getPhone());
            faxNumber.setText(currentContact.getFax());
            website.setText(currentContact.getWebsite());
            address.setText(currentContact.getFullAddress());
        }
    }
}
