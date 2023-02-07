package birzeit.edu.labandroidfinalproject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PREFERRED_TRAVEL_DESTINATIONS = "preferred_travel_destinations";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_PREFERRED_TRAVEL_DESTINATIONS + " TEXT" + ")";
    ////////////////////////
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
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
        String[] columns = { COLUMN_ID };
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
        String[] columns = { COLUMN_ID };
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
    public Cursor getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE email = ?", new String[] {email});
    }


    public boolean isValidEmailAndPassword(String email, String password) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            return false;
        }
        return true;
    }
}
