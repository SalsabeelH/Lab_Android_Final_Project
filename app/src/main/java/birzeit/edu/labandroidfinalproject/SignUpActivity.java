package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
//////////////////////////////

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private Button signUpBtn;
    private DatabaseHelper dbHelper;

    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner preferredDestinationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ///////////////////////////
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_Restriction = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%{}!])(?=\\S+$).{8,15}$";
        emailEditText = findViewById(R.id.signUpEmailage);
        passwordEditText = findViewById(R.id.signUpPasswordage);
        confirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordage);
        firstNameEditText = findViewById(R.id.signUpFirstNameage);
        lastNameEditText = findViewById(R.id.signUpLastNameage);


        preferredDestinationSpinner = findViewById(R.id.preferredDestinationSpinner);
        String[] continents = {
                "Africa", "Antarctica", "Asia", "Australia", "Europe", "North America", "South America"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, continents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferredDestinationSpinner.setAdapter(adapter);


    }
    String email = emailEditText.getText().toString().trim();
    String firstName = firstNameEditText.getText().toString().trim();
    String lastName =  lastNameEditText.getText().toString().trim();
    String password = passwordEditText.getText().toString().trim();
    String confirmPassword = confirmPasswordEditText.getText().toString().trim();
    String selectedContinent = preferredDestinationSpinner.getSelectedItem().toString();
        private boolean isFormValid() {
            boolean isValid = true;


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Invalid email address");
                emailEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else {
                emailEditText.setError(null);
                emailEditText.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
            }


            if (firstName.length() < 3 || firstName.length() > 20) {
                firstNameEditText.setError("First name must be between 3 and 20 characters");
                firstNameEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else {
                firstNameEditText.setError(null);
                firstNameEditText.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
            }


            if (lastName.length() < 3 || lastName.length() > 20) {
                lastNameEditText.setError("Last name must be between 3 and 20 characters");
                lastNameEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else {
                lastNameEditText.setError(null);
                lastNameEditText.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
            }


            if (password.length() < 8 || password.length() > 15) {
                passwordEditText.setError("Password must be between 8 and 15 characters");
                passwordEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else if (!password.matches(".*[0-9].*") || !password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
                passwordEditText.setError("Password must contain at least one number, one lowercase letter, and one uppercase letter");
                passwordEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else if (!password.equals(confirmPassword)) {
                passwordEditText.setError("Passwords do not match");
                passwordEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                confirmPasswordEditText.setError("Passwords do not match");
                confirmPasswordEditText.setBackgroundColor(getResources().getColor(R.color.errorBackground));
                isValid = false;
            } else {
                passwordEditText.setError(null);
                passwordEditText.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
                confirmPasswordEditText.setError(null);
                confirmPasswordEditText.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
            }

            return isValid;
        }
    public void onSignUpButtonClicked(View view) {
        if (isFormValid()) {
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
            dbHelper.addUser(email, firstName, lastName,password,  selectedContinent);
            Intent intent=new Intent(SignUpActivity.this, NavigationDrawerActivity.class);
        }
    }


}