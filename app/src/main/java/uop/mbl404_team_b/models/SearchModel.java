package uop.mbl404_team_b.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Derek on 11/25/2016.
 */

public class SearchModel implements Parcelable {
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private int status;
    private String sortBy;
    private int sortDistance;

    //default constructor
    public SearchModel(){}

    public SearchModel(String cAddress, String cCity, String cState, String cZipcode,
                       int cStatus, String cSortBy, int cDistance) {
        this.address = cAddress;
        this.city = cCity;
        this.state = cState;
        this.zipcode = cZipcode;
        this.sortBy = cSortBy;
        this.sortDistance = cDistance;
        this.status = cStatus;
    }
    public String getAddress(){
        return this.address;
    }
    public String getCity(){
        return this.city;
    }
    public String getState(){
        return this.state;
    }
    public String getZipcode(){
        return this.zipcode;
    }
    public String getSortCategory(){
        return this.sortBy;
    }
    public int getSearchDistance(){
        return this.sortDistance;
    }

    public String getFullAddress(){
        return String.format("%s,%s,%s,%s", address, city, state, zipcode);
    }

    public void setAddress(String address){ this.address = address; }
    public void setCity(String city){ this.city = city; }
    public void setState(String state){ this.state = state; }
    public void setZipcode(String zipcode){ this.zipcode = zipcode; }
    public void setSortCategory(String category){ this.sortBy = category; }
    public void setSearchDistance(int distance){ this.sortDistance = distance; }
    public void setStatus(int status){ this.status = status; }

    public SearchModel(Parcel in) {
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel in) {
            return new ContactModel(in);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(zipcode);
        parcel.writeString(sortBy);
        parcel.writeDouble(sortDistance);
        parcel.writeInt(status);
    }
}