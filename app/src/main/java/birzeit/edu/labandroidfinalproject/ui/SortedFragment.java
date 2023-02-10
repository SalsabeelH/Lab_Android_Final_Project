package birzeit.edu.labandroidfinalproject.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import birzeit.edu.labandroidfinalproject.Adapters.DestinationsRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.Adapters.SortRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.R;
import birzeit.edu.labandroidfinalproject.databinding.FragmentSortedBinding;


public class SortedFragment extends Fragment {
    private FragmentSortedBinding binding;

    private RecyclerView recyclerView;
    private List<Destination> destinations = new ArrayList<>();
    private ImageView btnUp;
    private ImageView btnDown;
    private ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSortedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.sort_recycular_view);
        btnUp = root.findViewById(R.id.btn_up);
        btnDown = root.findViewById(R.id.btn_down);
        imageView = root.findViewById(R.id.imageViewDoller);

        destinations = ((NavigationDrawerActivity) getActivity()).getAllDestinations();
        Collections.sort(destinations, new Comparator<Destination>() {
            @Override
            public int compare(Destination destination, Destination t1) {
                return (Integer.parseInt(destination.getCost()) - Integer.parseInt(t1.getCost()));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        SortRecyclerAdapter adapter = new SortRecyclerAdapter(destinations);
        recyclerView.setAdapter(adapter);

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinations = ((NavigationDrawerActivity) getActivity()).getAllDestinations();
                Collections.sort(destinations, new Comparator<Destination>() {
                    @Override
                    public int compare(Destination destination, Destination t1) {
                        return (Integer.parseInt(destination.getCost()) - Integer.parseInt(t1.getCost()));
                    }
                });
                SortRecyclerAdapter adapter = new SortRecyclerAdapter(destinations);
                recyclerView.setAdapter(adapter);

                Animation animation1 = AnimationUtils.loadAnimation((NavigationDrawerActivity)getActivity(),R.anim.animation_big_to_small);
                imageView.setImageResource(R.drawable.dollar_symbol);
                imageView.startAnimation(animation1);
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinations = ((NavigationDrawerActivity) getActivity()).getAllDestinations();
                Collections.sort(destinations, new Comparator<Destination>() {
                    @Override
                    public int compare(Destination destination, Destination t1) {
                        return (Integer.parseInt(t1.getCost()) - Integer.parseInt(destination.getCost()));
                    }
                });
                SortRecyclerAdapter adapter = new SortRecyclerAdapter(destinations);
                recyclerView.setAdapter(adapter);

                Animation animation1 = AnimationUtils.loadAnimation((NavigationDrawerActivity)getActivity(),R.anim.animation_small_to_big);
                imageView.setImageResource(R.drawable.dollar_symbol);
                imageView.startAnimation(animation1);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
