package uop.mbl404_team_b.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dustin on 11/11/2016.
 */

public class ContactModel implements Parcelable{
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private int zipcode;
    private String phone;
    private String fax;
    private String website;
    private String scheduleWeekday;
    private String scheduleSaturday;
    private String scheduleSunday;
    private int status;
    private float rating;
    private double latitude;
    private double longitude;

    //default constructor
    public ContactModel(){}

    public ContactModel(int cId, String cName, String cAddress, String cCity,
        String cState, int cZipcode, String cPhone, String cFax, String cWebsite, String cScheduleWeekday, String cScheduleSat,
        String cScheduleSun, int cStatus, float cRating, double cLatitude, double cLongitude) {
        this.id = cId;
        this.name = cName;
        this.address = cAddress;
        this.city = cCity;
        this.state = cState;
        this.zipcode = cZipcode;
        this.phone = cPhone;
        this.fax = cFax;
        this.website = cWebsite;
        this.scheduleWeekday = cScheduleWeekday;
        this.scheduleSaturday = cScheduleSat;
        this.scheduleSunday = cScheduleSun;
        this.status = cStatus;
        this.rating = cRating;
        this.latitude = cLatitude;
        this.longitude = cLongitude;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
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
    public int getZipcode(){
        return this.zipcode;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getFax(){
        return this.fax;
    }
    public String getWebsite(){
        return this.website;
    }
    public String getScheduleWeekday(){
        return this.scheduleWeekday;
    }
    public String getScheduleSaturday(){
        return this.scheduleSaturday;
    }
    public String getScheduleSunday(){
        return this.scheduleSunday;
    }
    public int getStatus(){
        return this.status;
    }
    public float getRating(){
        return this.rating;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public String getFullAddress(){
        return String.format("%s\n%s, %s\n%s", address, city, state, zipcode);
    }

    public void setId(int id){ this.id = id; }
    public void setName(String name){ this.name = name; }
    public void setAddress(String address){ this.address = address; }
    public void setCity(String city){ this.city = city; }
    public void setState(String state){ this.state = state; }
    public void setZipcode(int zipcode){ this.zipcode = zipcode; }
    public void setPhone(String phone){ this.phone = phone; }
    public void setFax(String fax){ this.fax = fax; }
    public void setWebsite(String website){ this.website = website; }
    public void setScheduleWeekday(String scheduleWeekday){ this.scheduleWeekday = scheduleWeekday; }
    public void setScheduleSaturday(String scheduleSaturday){ this.scheduleSaturday = scheduleSaturday; }
    public void setScheduleSunday(String scheduleSunday){ this.scheduleSunday = scheduleSunday; }
    public void setStatus(int status){ this.status = status; }
    public void setRating(float rating){ this.rating = rating; }
    public void setLatitude(double latitude){ this.latitude = latitude; }
    public void setLongitude(double setlongitude){ this.longitude = setlongitude; }

    public ContactModel(Parcel in) {
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
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(state);
        parcel.writeInt(zipcode);
        parcel.writeString(phone);
        parcel.writeString(fax);
        parcel.writeString(website);
        parcel.writeString(scheduleSaturday);
        parcel.writeString(scheduleSunday);
        parcel.writeInt(status);
        parcel.writeFloat(rating);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
