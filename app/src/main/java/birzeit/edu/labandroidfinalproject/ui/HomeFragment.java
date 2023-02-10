package birzeit.edu.labandroidfinalproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.R;
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
    private ImageView btnAddToFavorite;
    private TextView textAddFavorite;
    private RequestQueue queue;
    private ArrayList<Destination> preferredDestinations = new ArrayList<>();
    private DatabaseHelper dbHelper;


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
        btnAddToFavorite = binding.btnAddToFavorite2;
        textAddFavorite = binding.txtAddFavorite2;


        /*
         * Get the preferred continent and user id
         */
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance((NavigationDrawerActivity) getActivity());
        String preferredContinent = sharedPrefManager.readString("Preferred Continent","Africa");;
        String userEmail = sharedPrefManager.readString("Email", "");
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
            setCameraPosition(Double.parseDouble(randomDestination.getLongitude()), Double.parseDouble(randomDestination.getLatitude()));
            setMarkerOnMap(Double.parseDouble(randomDestination.getLongitude()), Double.parseDouble(randomDestination.getLatitude()));
            initAddToFavoriteStyles(randomDestination,userEmail);
            setListenerAddFavorite(randomDestination, userEmail);
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
                                preferredDestinations.add(destination);
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
                    setCameraPosition(Double.parseDouble(randomDestination.getLongitude()), Double.parseDouble(randomDestination.getLatitude()));
                    setMarkerOnMap(Double.parseDouble(randomDestination.getLongitude()), Double.parseDouble(randomDestination.getLatitude()));
                    initAddToFavoriteStyles(randomDestination,userEmail);
                    setListenerAddFavorite(randomDestination, userEmail);
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

    private void initAddToFavoriteStyles(Destination destination, String userEmail){
        dbHelper = new DatabaseHelper((NavigationDrawerActivity) getActivity());
        if (dbHelper.isFavoriteDestination(userEmail,destination.getCity())){
            btnAddToFavorite.setImageResource(R.drawable.remove);
            textAddFavorite.setText("remove from your favorite");
            textAddFavorite.setTextColor(Color.parseColor("#FF0000"));

        } else{
            btnAddToFavorite.setImageResource(R.drawable.plus);
            textAddFavorite.setText("add to favorite");
            textAddFavorite.setTextColor(Color.parseColor("#0586EC"));
        }
    }

    private void setListenerAddFavorite(Destination destination ,String userEmail){
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
            }
        });
    }

    private void setCameraPosition(double longitude, double latitude){
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(4.0)
                .center(Point.fromLngLat(longitude, latitude))
                .build();
        // set camera position
        mapView.getMapboxMap().setCamera(cameraPosition);
    }
    private void setMarkerOnMap(double longitude, double latitude){
        Bitmap bitmap = BitmapFactory.decodeResource(
                getResources(), R.drawable.red_marker);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate)mapView);
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI,mapView);
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(longitude, latitude))
                .withIconImage(bitmap);
        pointAnnotationManager.create(pointAnnotationOptions);
    }


    public Destination getRandomDestination(){
        int randomIndex = getRandomNumber(0, preferredDestinations.size());
        return preferredDestinations.get(randomIndex);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min); // Obtain a number between [min - max]
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        binding = null;
    }




}