package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.maps.MapView;

import birzeit.edu.labandroidfinalproject.Models.Destination;

public class DestinationActivity extends AppCompatActivity {

    private TextView textCity;
    private TextView textCountry;
    private TextView textContinent;
    private TextView textLongitude;
    private TextView textLatitude;
    private TextView textDescription;
    private TextView textCost;
    private ImageView imageView;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        Intent intent = getIntent();

        String city = intent.getStringExtra("DESTINATION_CITY");
        String country = intent.getStringExtra("DESTINATION_COUNTRY");
        String continent = intent.getStringExtra("DESTINATION_CONTINENT");
        String cost = intent.getStringExtra("DESTINATION_COST");
        String description = intent.getStringExtra("DESTINATION_DESCRIPTION");
        String img = intent.getStringExtra("DESTINATION_IMG");
        String lat = intent.getStringExtra("DESTINATION_LAT");
        String lon = intent.getStringExtra("DESTINATION_LONG");
        Destination destination = new Destination(city,country,continent,lon,lat,cost,img,description);

        textCity = findViewById(R.id.text_city7);
        textCountry = findViewById(R.id.text_country7);
        textContinent = findViewById(R.id.text_continent7);
        textLongitude = findViewById(R.id.text_longitude7);
        textLatitude = findViewById(R.id.text_latitude7);
        textCost = findViewById(R.id.text_cost7);
//        textDescription = findViewById();
//        mapView = findViewById();
//        imageView = findViewById();

        textCity.setText(destination.getCity());
        textCountry.setText(destination.getCountry());
        textContinent.setText(destination.getContinent());
        textLongitude.setText(destination.getLongitude().substring(0,7));
        textLatitude.setText(destination.getLatitude().substring(0,7));
        textCost.setText(destination.getCost());

    }
}