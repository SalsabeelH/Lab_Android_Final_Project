package birzeit.edu.labandroidfinalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.Adapters.ContinentRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.Adapters.DestinationsRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.DatabaseHelper;
import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.Models.Continent;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    private DatabaseHelper dbHelper;
    private ArrayList<Destination> favoriteDestinations;
    public static RecyclerView recyclerView;
    public static DestinationsRecyclerAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance((NavigationDrawerActivity) getActivity());
        String userEmail = sharedPrefManager.readString("Email", "");

        dbHelper = new DatabaseHelper((NavigationDrawerActivity) getActivity());
        recyclerView = binding.favoriteRecyclerView;
        favoriteDestinations = new ArrayList<>(dbHelper.getAllFavoriteDestinationsByEmail(userEmail));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter = new DestinationsRecyclerAdapter(favoriteDestinations);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}