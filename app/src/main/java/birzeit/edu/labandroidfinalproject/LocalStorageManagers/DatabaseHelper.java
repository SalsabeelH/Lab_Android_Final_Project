package birzeit.edu.labandroidfinalproject.LocalStorageManagers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.List;

import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "my_travel_db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PREFERRED_TRAVEL_DESTINATIONS = "preferred_travel_destinations";

    private static final String COLUMN_FAVORITE_ID = "id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String FAVORITE_DESTINATIONS_TABLE_NAME = "favorite_destination";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_CONTINENT = "continent";
    private static final String COLUMN_LONG = "longitude";
    private static final String COLUMN_LAT = "latitude";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_IMG = "image";
    private static final String COLUMN_DESCRIPTION = "description";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY ,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_PREFERRED_TRAVEL_DESTINATIONS + " TEXT" + ")";

    private String CREATE_FAVORITE_DESTINATIONS_TABLE = "CREATE TABLE " + FAVORITE_DESTINATIONS_TABLE_NAME + "("
            + COLUMN_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_CITY + " TEXT,"
            + COLUMN_COUNTRY + " TEXT,"
            + COLUMN_CONTINENT + " TEXT,"
            + COLUMN_LONG + " TEXT,"
            + COLUMN_LAT + " TEXT,"
            + COLUMN_COST + " TEXT,"
            + COLUMN_IMG + " TEXT,"
            + COLUMN_DESCRIPTION  + " TEXT" + ")";

    ////////////////////////
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private String DROP_FAVORITE_DESTINATIONS_TABLE = "DROP TABLE IF EXISTS " + FAVORITE_DESTINATIONS_TABLE_NAME;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FAVORITE_DESTINATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_FAVORITE_DESTINATIONS_TABLE);
        onCreate(db);
    }
   ////////////////////////////////////

    public void addUser(String email, String firstName, String lastName, String password, String preferredTravelDestinations) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PREFERRED_TRAVEL_DESTINATIONS, preferredTravelDestinations);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateUser(String email, String firstName, String lastName, String password, String preferredTravelDestinations) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_PREFERRED_TRAVEL_DESTINATIONS, preferredTravelDestinations);
        db.update(TABLE_NAME, values, " email = ?", new String[]{email});
        db.close();
    }

    public void addFavoriteDestination(String userEmail,String city, String country, String continent, String longitude, String latitude, String cost, String img, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_CONTINENT, continent);
        values.put(COLUMN_LONG, longitude);
        values.put(COLUMN_LAT, latitude);
        values.put(COLUMN_COST, cost);
        values.put(COLUMN_IMG, img);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(FAVORITE_DESTINATIONS_TABLE_NAME, null, values);
        db.close();
    }

    public boolean isFavoriteDestination(String userEmail,String city){
        List<Destination>  destinations =  new ArrayList<Destination>();
        destinations = getAllFavoriteDestinationsByEmail(userEmail);
        for(int i = 0 ; i < destinations.size() ; ++i){
            if (destinations.get(i).getCity().equals(city))
                return true;
        }
        return false;
    }

    public List<Destination> getAllFavoriteDestinationsByEmail(String email){
        List<Destination>  destinations = new ArrayList<Destination>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favorite_destination WHERE user_email = ?", new String[] {email});
        while (cursor.moveToNext()){
            destinations.add(new Destination(cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9)));
        }
        cursor.close();
        db.close();
        return destinations;
    }

    public void deleteFavoriteDestination(String userEmail,String city){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAVORITE_DESTINATIONS_TABLE_NAME,"user_email=? and city=?",new String[]{userEmail,city});
    }

    //////////
    public boolean checkUser(String email, String firstName, String lastName, String password, String confirmPassword, String travelDestinations) {
// check if the email is in a correct format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }


// check if first name length is between 3 and 20 characters
        if (firstName.length() < 3 || firstName.length() > 20) {
            return false;
        }

// check if last name length is between 3 and 20 characters
        if (lastName.length() < 3 || lastName.length() > 20) {
            return false;
        }

// check if password length is between 8 and 15 characters
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }

// check if password contains at least one number, one lowercase letter, and one uppercase letter
        if (!password.matches(".*\\d.*") || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            return false;
        }

// check if the password and confirm password match
        if (!password.equals(confirmPassword)) {
            return false;
        }

// check if travel destinations is not empty
        if (travelDestinations.isEmpty()) {
            return false;
        }

// check if the email is already registered in the database
        String[] columns = { COLUMN_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return false;
        }

        return true;
    }





////////////////////////////////////

    public boolean checkUser2(String email, String password) {
        String[] columns = { COLUMN_EMAIL };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }

    public User getUserByEmail(String email){
        User user = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        while (cursor.moveToNext()){
            user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        }
        return user;
    }

    public String getUserPreferredContinentByEmail(String email){
        String preferredContinent = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
        while (cursor.moveToNext()){
            preferredContinent = cursor.getString(4);
        }
        return preferredContinent;
    }

    public boolean isEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_EMAIL},
                COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }



  /*  public boolean isValidEmailAndPassword(String email, String password) {

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            return false;
        }
        if (isEmailExist(email)) {
            return false;
        }
        return true;
    }
    */

    public Pair<Boolean, String> isValidEmailAndPassword(String email, String password) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return new Pair<>(false, "Invalid email format");
        }
        if (password.isEmpty() || password.length() < 6) {
            return new Pair<>(false, "Password must be at least 6 characters");
        }
        // Check if the email is already registered in the database
        if (!isEmailExist(email)) {
            return new Pair<>(false, "You have to sign up");
        }
        return new Pair<>(true, "");
    }

}
