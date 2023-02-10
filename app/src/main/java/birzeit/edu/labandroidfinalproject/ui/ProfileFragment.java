package birzeit.edu.labandroidfinalproject.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.fragment.app.Fragment;

import com.android.volley.Header;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.LogInActivity;
import birzeit.edu.labandroidfinalproject.MainActivity;
import birzeit.edu.labandroidfinalproject.Models.User;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.R;
import birzeit.edu.labandroidfinalproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private DatabaseHelper dbHelper;
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner preferredDestinationSpinner;
    private Button btnUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dbHelper = new DatabaseHelper((NavigationDrawerActivity) getActivity());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance((NavigationDrawerActivity) getActivity());
        String currentUserEmail = sharedPrefManager.readString("Email","");
        User currentUser = dbHelper.getUserByEmail(currentUserEmail);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_RESTRICTION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%{}!])(?=\\S+$).{8,15}$";

        emailEditText = root.findViewById(R.id.text_edit_email);
        firstNameEditText = root.findViewById(R.id.text_edit_first_name);
        lastNameEditText = root.findViewById(R.id.text_edit_last_name);
        passwordEditText = root.findViewById(R.id.text_edit_password);
        confirmPasswordEditText = root.findViewById(R.id.text_edit_confirm_password);
        preferredDestinationSpinner = root.findViewById(R.id.spinner_edit);
        btnUpdate = root.findViewById(R.id.btn_update);

        disableEditText(emailEditText);

        String[] continents = {
                "Africa", "Antarctica", "Asia", "Australia", "Europe", "North America", "South America"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>((NavigationDrawerActivity) getActivity(), android.R.layout.simple_spinner_item, continents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferredDestinationSpinner.setAdapter(adapter);

        emailEditText.setText(currentUser.getEmail());
        firstNameEditText.setText(currentUser.getFirstName());
        lastNameEditText.setText(currentUser.getLastName());
        passwordEditText.setText(currentUser.getPassword());
        confirmPasswordEditText.setText(currentUser.getPassword());
        preferredDestinationSpinner.setSelection(adapter.getPosition(currentUser.getPreferredTravelDestinations()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFormValid()) {
                    String firstName = firstNameEditText.getText().toString().trim();
                    String lastName = lastNameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                    String selectedContinent = preferredDestinationSpinner.getSelectedItem().toString();

                    if (password.equals(confirmPassword)) {
                        dbHelper.updateUser(currentUserEmail, firstName, lastName, password, selectedContinent);
                        Toast.makeText((NavigationDrawerActivity) getActivity(), "Updated Successful!", Toast.LENGTH_LONG).show();
                        sharedPrefManager.writeString("Preferred Continent", selectedContinent);
                        sharedPrefManager.writeBoolean("loggedIn", false);
                        emailEditText.setText("");
                        firstNameEditText.setText("");
                        lastNameEditText.setText("");
                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent((NavigationDrawerActivity) getActivity(), LogInActivity.class);
                                startActivity(intent);
                            }
                        }, 500);

                    } else {
                        Toast.makeText((NavigationDrawerActivity) getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return root;
    }

    private boolean isFormValid() {

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
        if (!passwordEditText.getText().toString().trim().matches(".*[0-9].*") || !passwordEditText.getText().toString().trim().matches(".*[a-z].*") || !passwordEditText.getText().toString().trim().matches(".*[A-Z].*")) {
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

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
