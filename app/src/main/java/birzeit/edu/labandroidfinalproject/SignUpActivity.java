package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;

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

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_RESTRICTION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%{}!])(?=\\S+$).{8,15}$";

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

        signUpBtn = findViewById(R.id.btnSignUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormValid()) {
                    String email = emailEditText.getText().toString().trim();
                    String firstName = firstNameEditText.getText().toString().trim();
                    String lastName = lastNameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                    String selectedContinent = preferredDestinationSpinner.getSelectedItem().toString();
                    dbHelper = new DatabaseHelper(SignUpActivity.this);

                    if (!dbHelper.isEmailExist(email)) {
                        if (password.equals(confirmPassword)) {

                            dbHelper.addUser(email, firstName, lastName, password, selectedContinent);
                            Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(SignUpActivity.this, NavigationDrawerActivity.class);
                            startActivity(loginIntent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Email already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isFormValid() {
        if (emailEditText.getText().toString().trim().isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString().trim()).matches()) {
            emailEditText.setError("Enter a valid email");
            emailEditText.requestFocus();
            return false;
        }

        if (firstNameEditText.getText().toString().trim().isEmpty()) {
            firstNameEditText.setError("First name is required");
            firstNameEditText.requestFocus();
            return false;
        }
        if (firstNameEditText.getText().toString().trim().length() < 3 || firstNameEditText.getText().toString().trim().length() > 20) {
            firstNameEditText.setError("First name should be between 3-20");
            firstNameEditText.requestFocus();
            return false;
        }

        if (lastNameEditText.getText().toString().trim().isEmpty()) {
            lastNameEditText.setError("Last name is required");
            lastNameEditText.requestFocus();
            return false;
        }
        if (lastNameEditText.getText().toString().trim().length() < 3 || lastNameEditText.getText().toString().trim().length() > 20) {
            lastNameEditText.setError("Last name should be between 3-20");
            lastNameEditText.requestFocus();
            return false;
        }


        if (passwordEditText.getText().toString().trim().isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return false;
        }

        if (passwordEditText.getText().toString().trim().length() < 8) {
            passwordEditText.setError("Password should be at least 8 characters long");
            passwordEditText.requestFocus();
            return false;
        }
        if ( !passwordEditText.getText().toString().trim().matches(".*[0-9].*") || !passwordEditText.getText().toString().trim().matches(".*[a-z].*") || !passwordEditText.getText().toString().trim().matches(".*[A-Z].*") ){
            passwordEditText.setError("Password must contain at least one number, one lowercase letter, and one uppercase letter");
            passwordEditText.requestFocus();
            return false;
        }

        if (!passwordEditText.getText().toString().trim().equals(confirmPasswordEditText.getText().toString().trim())) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return false;
        }

        return true;
    }
}

