package birzeit.edu.labandroidfinalproject;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.util.Pair;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;

public class LogInActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private Button logInBtn;
    private Button goToSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        extracted();
    }

    private void extracted() {
        dbHelper = new DatabaseHelper(this);
        logInBtn = findViewById(R.id.btnSignIn);
        goToSignUpBtn = findViewById(R.id.btnSignUp);
        EditText email = (EditText) findViewById(R.id.signInEmail);
        EditText password = (EditText) findViewById(R.id.signInPassword);
        CheckBox rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        boolean loggedIn = sharedPrefManager.readBoolean("loggedIn", false);
        email.setText(sharedPrefManager.readString("Email", ""));
        password.setText(sharedPrefManager.readString("Password", ""));

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = email.getText().toString();
                String enteredPassword = password.getText().toString();
                Pair<Boolean, String> result = dbHelper.isValidEmailAndPassword(enteredEmail, enteredPassword);
                sharedPrefManager.writeString("Preferred Continent", dbHelper.getUserPreferredContinentByEmail(enteredEmail));

                // Check if the entered email and password are correct and registered in the database
                if ( result.first) {
                    // If the "remember me" checkbox is checked, save the entered email and password in shared preferences
                    if (rememberMe.isChecked()) {
                        sharedPrefManager.writeString("Email", enteredEmail);
                        sharedPrefManager.writeString("Password", enteredPassword);
                    } else {
                        // If the "remember me" checkbox is not checked, clear the saved password in shared preferences
                        sharedPrefManager.writeString("Email", enteredEmail);
                        sharedPrefManager.writeString("Password", "");
                    }
                    // Start the next activity
                    Intent intent = new Intent(LogInActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Show error message if the entered email and password are incorrect or not registered
                    Toast.makeText(LogInActivity.this, "Incorrect email or password|| email is already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });


                    ////////////////////////////////////////////////////



        goToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}