package uop.mbl404_team_b.helpers;

/**
 * Created by Derek on 10/23/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import uop.mbl404_team_b.models.ContactModel;

import static com.google.ads.AdRequest.LOGTAG;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "clinics.db";
    public static final String TABLE_CLINICS = "clinics";
    public static final String DB_CLINIC_ID = "id";
    public static final String DB_CLINIC_NAME = "name";
    public static final String DB_CLINIC_ADDRESS = "address";
    public static final String DB_CLINIC_CITY = "city";
    public static final String DB_CLINIC_STATE = "state";
    public static final String DB_CLINIC_ZIPCODE = "zip";
    public static final String DB_CLINIC_PHONE = "phone";
    public static final String DB_CLINIC_FAX = "fax";
    public static final String DB_CLINIC_WEBSITE = "website";
    public static final String DB_CLINIC_SCHEDULE_WEEKDAY = "weekday";
    public static final String DB_CLINIC_SCHEDULE_SATURDAY = "saturday";
    public static final String DB_CLINIC_SCHEDULE_SUNDAY = "sunday";
    public static final String DB_CLINIC_STATUS = "status";
    public static final String DB_CLINIC_RATING = "rating";
    public static final String DB_CLINIC_LAT = "latitude";
    public static final String DB_CLINIC_LONG = "longitude";

    public static final String SORT_NAME = "Name";
    public static final String SORT_DISTANCE = "Distance";
    public static final String SORT_RATING = "Rating";
    public static final String SORT_STATUS = "Status";


    SQLiteDatabase db;

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CLINICS + " (" +
                DB_CLINIC_ID + " integer primary key, " +
                DB_CLINIC_NAME + " text, " +
                DB_CLINIC_ADDRESS + " text, " +
                DB_CLINIC_CITY + " text, " +
                DB_CLINIC_STATE + " text, " +
                DB_CLINIC_ZIPCODE + " text, " +
                DB_CLINIC_PHONE + " text, " +
                DB_CLINIC_FAX + " text, " +
                DB_CLINIC_WEBSITE + " text, " +
                DB_CLINIC_SCHEDULE_WEEKDAY + " text, " +
                DB_CLINIC_SCHEDULE_SATURDAY + " text, " +
                DB_CLINIC_SCHEDULE_SUNDAY + " text, " +
                DB_CLINIC_STATUS + " integer, " +
                DB_CLINIC_RATING + " integer, " +
                DB_CLINIC_LAT + " double, " +
                DB_CLINIC_LONG + " double)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINICS);
        onCreate(db);
    }

    public void open(){
        Log.i(LOGTAG, "Database opened");
        db = getWritableDatabase();
        addData();
    }

    public void close()
    {
        Log.d(LOGTAG, "Database closed");
        this.close();
    }

    private void addData(){
        insertContact("Beating Heart Clinic", "101 Main St", "Toledo", "Ohio", "43605", "419-111-1111", "419-111-1112", "http://www.beatingheartclinic.org", "8:00 AM - 5:00 PM", "8:00 AM - 12:00 PM", "Closed", 1, 2, 41.648525, -83.523348);
        insertContact("Clean Lungs Clinic", "1460 W Michigan St", "Indianapolis", "Indiana", "46202", "317-111-1111", "317-111-1112", "http://www.cleanlungsclinic.org", "9:00 AM - 6:00 PM", "9:00 AM - 12:30 PM", "Closed", 1, 5, 39.775696, -86.186488);
        insertContact("Healthy Liver Clinic", "609 Faircloth St", "Colfax", "LA", "71417", "318-111-1111", "318-111-1112", "http://www.healthyliverclinic.org", "9:00 AM - 5:00 PM", "9:30 AM - 1:00 PM", "Closed", 1, 4, 31.520867, -92.709570);
        insertContact("Dyanamite Clinic", "367 Sumner St", "Toledo", "OH", "43609", "318-111-1131", "318-111-1132", "http://www.dynamiteclinic.org", "8:00 AM - 5:00 PM", "9:30 AM - 1:00 PM", "Closed", 1, 5, 41.632527, -83.544203);
        insertContact("Cave Johnson Clinic", "814 E Central Ave", "Toledo", "OH", "43608", "318-111-1121", "318-111-1122", "http://www.cavejohnsonclinic.org", "9:00 AM - 5:00 PM", "9:30 AM - 1:00 PM", "Closed", 1, 3, 41.678816, -83.526405);
        insertContact("The Butcher's Clinic", "2513 Monroe St", "Toledo", "OH", "43620", "318-111-1117", "318-111-1118", "http://www.butcherclinic.org", "9:00 AM - 5:00 PM", "9:30 AM - 1:00 PM", "Closed", 1, 2, 41.659295, -83.559758);


    }

    public boolean insertContact (String name, String address, String city, String state,
                                  String zipcode, String phone, String fax, String website, String scheduleWeekday,
                                  String scheduleSaturday, String scheduleSunday, Integer status, Integer rating,
                                  Double lat, Double lng )
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_CLINIC_NAME, name);
        contentValues.put(DB_CLINIC_ADDRESS, address);
        contentValues.put(DB_CLINIC_CITY, city);
        contentValues.put(DB_CLINIC_STATE, state);
        contentValues.put(DB_CLINIC_ZIPCODE, zipcode);
        contentValues.put(DB_CLINIC_PHONE, phone);
        contentValues.put(DB_CLINIC_FAX, fax);
        contentValues.put(DB_CLINIC_WEBSITE, website);
        contentValues.put(DB_CLINIC_SCHEDULE_WEEKDAY, scheduleWeekday);
        contentValues.put(DB_CLINIC_SCHEDULE_SATURDAY, scheduleSaturday);
        contentValues.put(DB_CLINIC_SCHEDULE_SUNDAY, scheduleSunday);
        contentValues.put(DB_CLINIC_STATUS, status);
        contentValues.put(DB_CLINIC_RATING, rating);
        contentValues.put(DB_CLINIC_LAT, lat);
        contentValues.put(DB_CLINIC_LONG, lng);

        db.insertWithOnConflict(TABLE_CLINICS, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        //db.insert(TABLE_CLINICS, null, contentValues);
        return true;
    }

    //Extracts and returns cursor object containing requested record
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_CLINICS + " where " + DB_CLINIC_ID + "=" + id + "", null );
        return res;
    }

    //Extracts and returns contactmodel object containing requested record
    public ContactModel getContact(int id){
        Cursor res =  db.rawQuery( "select * from " + TABLE_CLINICS + " where " + DB_CLINIC_ID + "=" + id + "", null );

        ContactModel newContact = new ContactModel();
        if (res != null) {
            res.moveToFirst();
            newContact.setId(id);
            newContact.setName(res.getString(res.getColumnIndex(DB_CLINIC_NAME)));
            newContact.setAddress(res.getString(res.getColumnIndex(DB_CLINIC_ADDRESS)));
            newContact.setCity(res.getString(res.getColumnIndex(DB_CLINIC_CITY)));
            newContact.setState(res.getString(res.getColumnIndex(DB_CLINIC_STATE)));
            newContact.setZipcode(res.getInt(res.getColumnIndex(DB_CLINIC_ZIPCODE)));
            newContact.setPhone(res.getString(res.getColumnIndex(DB_CLINIC_PHONE)));
            newContact.setFax(res.getString(res.getColumnIndex(DB_CLINIC_FAX)));
            newContact.setWebsite(res.getString(res.getColumnIndex(DB_CLINIC_WEBSITE)));
            newContact.setScheduleWeekday(res.getString(res.getColumnIndex(DB_CLINIC_SCHEDULE_WEEKDAY)));
            newContact.setScheduleSaturday(res.getString(res.getColumnIndex(DB_CLINIC_SCHEDULE_SATURDAY)));
            newContact.setScheduleSunday(res.getString(res.getColumnIndex(DB_CLINIC_SCHEDULE_SUNDAY)));
            newContact.setStatus(res.getInt(res.getColumnIndex(DB_CLINIC_STATUS)));
            newContact.setRating(res.getInt(res.getColumnIndex(DB_CLINIC_RATING)));
            newContact.setLatitude(res.getDouble(res.getColumnIndex(DB_CLINIC_LAT)));
            newContact.setLongitude(res.getDouble(res.getColumnIndex(DB_CLINIC_LONG)));
            res.close();
        }
        return newContact;
    }

    //Returns the number of rows in the database
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_CLINICS);
        return numRows;
    }

    //Delete clinic of the passed ID
    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLINICS,
                DB_CLINIC_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    //Return an array list containing each names of all clinics within specified distance of target
    //lat and longitude
    public ArrayList<ContactModel> getContactsWithinDistance(double lat, double lng, int dist, String sort)
    {
        int distance = dist;
        double d = milesToDeg(dist); //miles in degrees
        double lat_lower = lat - d; //latitude lower boundary
        double lat_upper = lat + d; //latitude upper boundary
        double lng_lower = lng - d;
        double lng_upper = lng + d;

        ArrayList<ContactModel> array_list = new ArrayList<ContactModel>();

        String strOrder = "";
        switch(sort){
            case SORT_NAME:
                strOrder = " ORDER BY " + DB_CLINIC_NAME + " ASC";;
                break;
            case SORT_DISTANCE:
                //strOrder = DB_CLINIC_;
                break;
            case SORT_STATUS:
                strOrder = " ORDER BY " + DB_CLINIC_STATUS + " ASC";
                break;
            case SORT_RATING:
                strOrder = " ORDER BY " + DB_CLINIC_RATING + " ASC";
                break;
        }


        Cursor res =  db.rawQuery( "select * from " + TABLE_CLINICS + " where (" + DB_CLINIC_LAT +
                " >= " + lat_lower + " AND " + DB_CLINIC_LAT + " <= " + lat_upper + " AND " +
                DB_CLINIC_LONG + " >= " + lng_lower + " AND " + DB_CLINIC_LONG + " <= " +
                lng_upper + ")" + strOrder, null );

        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(getContact(res.getInt(res.getColumnIndex(DB_CLINIC_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    //Return an array list containing contacmodel objects of all clinics in database
    public ArrayList<ContactModel> getAllContacts()
    {
        ArrayList<ContactModel> array_list = new ArrayList<ContactModel>();

        Cursor res =  db.rawQuery( "select * from " + TABLE_CLINICS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(getContact(res.getInt(res.getColumnIndex(DB_CLINIC_ID))));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    //Return an array list containing each names of all clinics in the database
    public ArrayList<String> getAllContactNames()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        Cursor res =  db.rawQuery( "select * from " + TABLE_CLINICS, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(DB_CLINIC_NAME)));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    private double getMilesBetween(double lat1, double lon1, double lat2, double lon2) {
        double R = 3959; // Radius of the earth in miles
        double dLat = degToRad(lat2-lat1);
        double dLon = degToRad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in miles
        return d;
    }



    private double milesToDeg(double miles){
        return miles * (1/69);
    }

    private double milesToDeg(int miles){
        double m = miles;
        return m / 69;
    }

    private double degToRad(double deg) {
        return deg * (Math.PI/180);
    }

}


