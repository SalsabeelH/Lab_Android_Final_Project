package birzeit.edu.labandroidfinalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import birzeit.edu.labandroidfinalproject.Models.Continent;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.R;

public class ContinentRecyclerAdapter extends RecyclerView.Adapter<ContinentRecyclerAdapter.ViewHolder> {

    List<Continent> continents;
    Context context;

    public ContinentRecyclerAdapter(List<Continent> continents, Context context) {
        this.continents = continents;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.continent_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Continent continent = continents.get(position);
        String continentName = continent.getContinentName();
        List<Destination> destinations = continent.getDestinations();
        holder.continentName.setText(continentName);
        holder.destinationsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        DestinationsRecyclerAdapter adapter = new DestinationsRecyclerAdapter(destinations);
        holder.destinationsRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return continents.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView continentName;
        RecyclerView destinationsRecyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            continentName = itemView.findViewById(R.id.continent_text);
            destinationsRecyclerView = itemView.findViewById(R.id.destinations_recycler_view);

        }
    }

}
