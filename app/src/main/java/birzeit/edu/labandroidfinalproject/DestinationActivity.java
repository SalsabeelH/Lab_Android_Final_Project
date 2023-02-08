package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        TextView textView = findViewById(R.id.textView2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("DESTINATION_CITY");
        textView.setText(name);
    }
}