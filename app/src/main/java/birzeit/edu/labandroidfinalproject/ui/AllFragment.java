package birzeit.edu.labandroidfinalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.Adapters.ContinentRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.Models.Continent;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.R;
import birzeit.edu.labandroidfinalproject.databinding.FragmentAllBinding;


public class AllFragment extends Fragment {
    private FragmentAllBinding binding;
    private ArrayList<Continent> destinationsGroupedByContinent;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.continent_destinations_recycler_view);
        destinationsGroupedByContinent = ((NavigationDrawerActivity) getActivity()).getDestinationsGroupedByContinent();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        ContinentRecyclerAdapter adapter = new ContinentRecyclerAdapter(destinationsGroupedByContinent,getContext().getApplicationContext() );
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext(),DividerItemDecoration.VERTICAL));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}