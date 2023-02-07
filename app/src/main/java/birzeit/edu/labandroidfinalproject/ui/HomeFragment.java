package birzeit.edu.labandroidfinalproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    private TextView textCity;
    private TextView textCountry;
    private TextView textContinent;
    private TextView textLongitude;
    private TextView textLatitude;
    private TextView textDescription;
    private TextView textCost;
    private ImageView imageView;
    private MapView mapView;
    private RequestQueue queue;
    private ArrayList<Destination> favoriteDestinations = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textCity = binding.textCity;
        textCountry = binding.textCountry;
        textContinent = binding.textContinent;
        textLongitude = binding.textLongitude;
        textLatitude = binding.textLatitude;
        textDescription = binding.textDescription;
        textCost = binding.textCost;
        mapView = binding.mapView;
        imageView = binding.img;

        /*
         * Get the preferred continent
         */
        String[] continents = {"Africa", "Antarctica", "Asia", "Australia", "Europe", "North America", "South America"};
        String preferredContinent = continents[0];
        /*
         * Request to get all destinations using Volley and map them to Destination objects
         */
        if (((NavigationDrawerActivity) getActivity()).isDataReadyNow()){
            Destination randomDestination = getRandomDestination();
            textCity.setText(randomDestination.getCity());
            textCountry.setText(randomDestination.getCountry());
            textContinent.setText(randomDestination.getContinent());
            textLongitude.setText(randomDestination.getLongitude().substring(0,7));
            textLatitude.setText(randomDestination.getLatitude().substring(0,7));
            textDescription.setText(randomDestination.getDescription());
            textCost.setText(randomDestination.getCost());
            Glide.with((NavigationDrawerActivity) getActivity()).load(randomDestination.getImg()).into(imageView);
        } else{
            queue = Volley.newRequestQueue(((NavigationDrawerActivity) getActivity()));
            String url = "https://run.mocky.io/v3/d1a9c002-6e88-4d1e-9f39-930615876bca";
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Gson gson = new Gson();
                            Destination destination = gson.fromJson(String.valueOf(obj),Destination.class);
                            if(destination.getContinent().equals(preferredContinent))
                                favoriteDestinations.add(destination);
                        } catch (JSONException exception) {
                            Log.d("volley_error", exception.toString());
                        }
                    }
                    Destination randomDestination = getRandomDestination();
                    textCity.setText(randomDestination.getCity());
                    textCountry.setText(randomDestination.getCountry());
                    textContinent.setText(randomDestination.getContinent());
                    textLongitude.setText(randomDestination.getLongitude().substring(0,7));
                    textLatitude.setText(randomDestination.getLatitude().substring(0,7));
                    textDescription.setText(randomDestination.getDescription());
                    textCost.setText(randomDestination.getCost());
                    Glide.with((NavigationDrawerActivity) getActivity()).load(randomDestination.getImg()).into(imageView);
                    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("volley_error", error.toString());
                }
            });
            queue.add(request);
        }

        return root;
    }


    public Destination getRandomDestination(){
        int randomIndex = getRandomNumber(0,favoriteDestinations.size());
        return favoriteDestinations.get(randomIndex);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); // Obtain a number between [min - max]
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}