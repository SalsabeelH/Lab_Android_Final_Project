package birzeit.edu.labandroidfinalproject.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import birzeit.edu.labandroidfinalproject.DestinationActivity;
import birzeit.edu.labandroidfinalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private MapView mapView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DestinationActivity activity = (DestinationActivity) getActivity();
        double lat = activity.getLat();
        double lon = activity.getLong();

        mapView = (MapView) getActivity().findViewById(R.id.mapView7);
        setCameraPosition(lon, lat);
        setMarkerOnMap(lon, lat);

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

}