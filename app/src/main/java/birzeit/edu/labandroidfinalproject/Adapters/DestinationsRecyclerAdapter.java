package birzeit.edu.labandroidfinalproject.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import birzeit.edu.labandroidfinalproject.DestinationActivity;
import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.R;

public class DestinationsRecyclerAdapter extends RecyclerView.Adapter<DestinationsRecyclerAdapter.ViewHolder>{

    private final List<Destination> destinations;

    public DestinationsRecyclerAdapter(List<Destination> destinations) {
        this.destinations = destinations;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView txt = (TextView)cardView.findViewById(R.id.text_city2);
        TextView txt2 = (TextView)cardView.findViewById(R.id.text_country2);
        txt.setText(destinations.get(position).getCity());
        txt2.setText(destinations.get(position).getCountry());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = holder.getAbsoluteAdapterPosition();
                Intent intent = new Intent(holder.itemView.getContext(), DestinationActivity.class);
                intent.putExtra("DESTINATION_CITY", destinations.get(position).getCity());
                intent.putExtra("DESTINATION_COUNTRY", destinations.get(position).getCountry());
                intent.putExtra("DESTINATION_CONTINENT", destinations.get(position).getContinent());
                intent.putExtra("DESTINATION_COST", destinations.get(position).getCost());
                intent.putExtra("DESTINATION_DESCRIPTION", destinations.get(position).getDescription());
                intent.putExtra("DESTINATION_IMG", destinations.get(position).getImg());
                intent.putExtra("DESTINATION_LAT", destinations.get(position).getLatitude());
                intent.putExtra("DESTINATION_LONG", destinations.get(position).getLongitude());

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}
