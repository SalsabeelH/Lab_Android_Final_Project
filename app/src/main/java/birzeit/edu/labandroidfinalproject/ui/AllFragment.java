package birzeit.edu.labandroidfinalproject.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import birzeit.edu.labandroidfinalproject.Adapters.AllDestinationsRecyclerAdapter;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.R;
import birzeit.edu.labandroidfinalproject.databinding.FragmentAllBinding;


public class AllFragment extends Fragment {
    private RequestQueue queue;
    private FragmentAllBinding binding;
    private ArrayList<Destination> destinationsArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = root.findViewById(R.id.all_destinations_recycler_view);
        destinationsArrayList = ((NavigationDrawerActivity) getActivity()).getDestinationsArrayList();
        Destination[] destinations1 = new Destination[destinationsArrayList.size()];
        destinations1 = destinationsArrayList.toArray(destinations1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        AllDestinationsRecyclerAdapter adapter = new AllDestinationsRecyclerAdapter(destinations1);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}