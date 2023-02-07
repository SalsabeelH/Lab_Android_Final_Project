package birzeit.edu.labandroidfinalproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import birzeit.edu.labandroidfinalproject.Models.Destination;
import birzeit.edu.labandroidfinalproject.R;

public class AllDestinationsRecyclerAdapter extends RecyclerView.Adapter<AllDestinationsRecyclerAdapter.ViewHolder>{

    private final Destination[] destinations;

    public AllDestinationsRecyclerAdapter(Destination[] destinations) {
        this.destinations = destinations;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.all_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView txt = (TextView)cardView.findViewById(R.id.text_city2);
        TextView txt2 = (TextView)cardView.findViewById(R.id.text_country2);
        txt.setText(destinations[position].getCity());
        txt2.setText(destinations[position].getCountry());
        cardView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinations.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;
        }

    }

}
