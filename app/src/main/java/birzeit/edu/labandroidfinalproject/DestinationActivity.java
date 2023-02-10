package birzeit.edu.labandroidfinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.Adapters.DestinationsRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.ui.DescriptionFragment;
import birzeit.edu.labandroidfinalproject.ui.FavoriteFragment;
import birzeit.edu.labandroidfinalproject.ui.ImageFragment;
import birzeit.edu.labandroidfinalproject.ui.MapFragment;

public class DestinationActivity extends AppCompatActivity {

    private TextView textCity;
    private TextView textCountry;
    private TextView textContinent;
    private TextView textLongitude;
    private TextView textLatitude;
    private TextView textCost;
    private Button btnDescription;
    private Button btnMap;
    private Button btnImage;
    private ImageView btnAddToFavorite;
    private TextView textAddFavorite;
    private Destination destination;
    private DatabaseHelper dbHelper;

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
        destination = new Destination(city,country,continent,lon,lat,cost,img,description);

        textCity = findViewById(R.id.text_city7);
        textCountry = findViewById(R.id.text_country7);
        textContinent = findViewById(R.id.text_continent7);
        textLongitude = findViewById(R.id.text_longitude7);
        textLatitude = findViewById(R.id.text_latitude7);
        textCost = findViewById(R.id.text_cost7);
        btnDescription = findViewById(R.id.btn_description);
        btnMap = findViewById(R.id.btn_map);
        btnImage = findViewById(R.id.btn_image);
        btnAddToFavorite = findViewById(R.id.btn_add_to_favorite1);
        textAddFavorite = findViewById(R.id.txt_add_favorite);

        textCity.setText(destination.getCity());
        textCountry.setText(destination.getCountry());
        textContinent.setText(destination.getContinent());
        textLongitude.setText(destination.getLongitude().substring(0,7));
        textLatitude.setText(destination.getLatitude().substring(0,7));
        textCost.setText(destination.getCost());

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        String userEmail = sharedPrefManager.readString("Email", "");
        dbHelper = new DatabaseHelper(this);
        if (dbHelper.isFavoriteDestination(userEmail,destination.getCity())){
            btnAddToFavorite.setImageResource(R.drawable.remove);
            textAddFavorite.setText("remove from your favorite");
            textAddFavorite.setTextColor(Color.parseColor("#FF0000"));

        } else{
            btnAddToFavorite.setImageResource(R.drawable.plus);
            textAddFavorite.setText("add to favorite");
            textAddFavorite.setTextColor(Color.parseColor("#0586EC"));
        }

        final DescriptionFragment descriptionFragment = new DescriptionFragment();
        final MapFragment mapFragment = new MapFragment();
        final ImageFragment imageFragment = new ImageFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.show_hide_listView, descriptionFragment, "DescriptionFrag");
        fragmentTransaction.add(R.id.show_hide_listView,mapFragment,"MapFrag");
        fragmentTransaction.add(R.id.show_hide_listView, imageFragment, "ImageFrag");
        fragmentTransaction.detach(descriptionFragment);
        fragmentTransaction.detach(mapFragment);
        fragmentTransaction.detach(imageFragment);
        fragmentTransaction.commit();

        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (descriptionFragment.isVisible())
                    fragmentTransaction.detach(descriptionFragment);
                else
                    fragmentTransaction.attach(descriptionFragment);
                fragmentTransaction.commit();

            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (mapFragment.isVisible())
                    fragmentTransaction.detach(mapFragment);
                else
                    fragmentTransaction.attach(mapFragment);
                fragmentTransaction.commit();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (imageFragment.isVisible())
                    fragmentTransaction.detach(imageFragment);
                else
                    fragmentTransaction.attach(imageFragment);
                fragmentTransaction.commit();
            }
        });

        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.isFavoriteDestination(userEmail,destination.getCity())){
                    dbHelper.deleteFavoriteDestination(userEmail, destination.getCity());
                    btnAddToFavorite.setImageResource(R.drawable.plus);
                    textAddFavorite.setText("add to favorite");
                    textAddFavorite.setTextColor(Color.parseColor("#0586EC"));
                } else{
                    dbHelper.addFavoriteDestination(userEmail, destination.getCity(),destination.getCountry(), destination.getContinent(), destination.getLongitude(),destination.getLatitude(),destination.getCost(), destination.getImg(), destination.getDescription());
                    btnAddToFavorite.setImageResource(R.drawable.remove);
                    textAddFavorite.setText("remove from your favorite");
                    textAddFavorite.setTextColor(Color.parseColor("#FF0000"));
                }
                ArrayList<Destination> favoriteDestinations = new ArrayList<>(dbHelper.getAllFavoriteDestinationsByEmail(userEmail));
                FavoriteFragment.adapter = new DestinationsRecyclerAdapter(favoriteDestinations);
                FavoriteFragment.recyclerView.setAdapter(FavoriteFragment.adapter);
            }
        });

    }

    public String getDescription(){
        return destination.getDescription();
    }

    public Double getLat(){
        return Double.parseDouble(destination.getLatitude());
    }

    public Double getLong(){
        return Double.parseDouble(destination.getLongitude());
    }

    public String getImageUrl(){
        return destination.getImg();
    }
}