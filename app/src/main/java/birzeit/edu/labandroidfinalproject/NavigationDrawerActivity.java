package birzeit.edu.labandroidfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.Models.Continent;
import birzeit.edu.labandroidfinalproject.Models.Destination;

import birzeit.edu.labandroidfinalproject.databinding.ActivityNavigationDrawerBinding;


public class NavigationDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;
    private RequestQueue queue;
    private ArrayList<Destination> preferredDestinations = new ArrayList<>();
    private ArrayList<Destination> allDestinations = new ArrayList<>();
    private ArrayList<Continent> continentDestinations = new ArrayList<>();
    private boolean dataReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavigationDrawer.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_all, R.id.nav_favorite, R.id.nav_profile,R.id.nav_sorted,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*
        * Get the preferred continent
        */
        String[] continents = {"Africa", "Antarctica", "Asia", "Australia", "Europe", "North America", "South America"};
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        String preferredContinent = sharedPrefManager.readString("Preferred Continent","Africa");
        for(int i=0 ; i < continents.length ; ++i){
            continentDestinations.add(new Continent(continents[i], new ArrayList<Destination>()));
        }


        /*
        * Request to get all destinations using Volley and map them to Destination objects
        */
        queue = Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> destinations = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Gson gson = new Gson();
                        Destination destination = gson.fromJson(String.valueOf(obj),Destination.class);
                        if(destination.getContinent().equals(preferredContinent))
                            preferredDestinations.add(destination);
                        for(int j = 0 ; j < continents.length ; ++j){
                            if(destination.getContinent().equals(continents[j])){
                                continentDestinations.get(j).getDestinations().add(destination);
                            }
                        }
                        allDestinations.add(destination);
                    } catch (JSONException exception) {
                        Log.d("volley_error", exception.toString());
                    }
                }
                dataReady = true;
                ArrayList<Continent> continentDestTemp = new ArrayList<>();
                for (int i=0 ; i < continentDestinations.size() ; ++i){
                    if (continentDestinations.get(i).getDestinations().size() != 0)
                        continentDestTemp.add(continentDestinations.get(i));
                }
                continentDestinations = continentDestTemp;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley_error", error.toString());
            }
        });
        queue.add(request);


    }



    public boolean isDataReadyNow() {
        return dataReady;
    }

    public ArrayList<Destination> getPreferredDestinations(){
        return preferredDestinations;
    }

    public List<Destination> getAllDestinations(){
        return allDestinations;
    }


    public Destination getRandomDestination(){
        int randomIndex = getRandomNumber(0, preferredDestinations.size());
        return preferredDestinations.get(randomIndex);
    }


    public ArrayList<Continent> getDestinationsGroupedByContinent(){
        return continentDestinations;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); // Obtain a number between [min - max]
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navigation_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}