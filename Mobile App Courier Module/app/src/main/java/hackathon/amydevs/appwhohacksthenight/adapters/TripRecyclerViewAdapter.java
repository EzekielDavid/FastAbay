package hackathon.amydevs.appwhohacksthenight.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import hackathon.amydevs.appwhohacksthenight.R;
import hackathon.amydevs.appwhohacksthenight.models.Trip;

public class TripRecyclerViewAdapter extends RecyclerView.Adapter<TripRecyclerViewAdapter.TripViewHolder> {

    private Context context;
    public TripRecyclerViewAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
    }
    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.trip_item_layout, viewGroup, false);

        TripViewHolder  viewHolder = new TripViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder tripViewHolder, int i) {

        Trip trip = trips.get(i);

        tripViewHolder.tripTypeEditText.setText(trip.getTripType());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder{

        private TextView tripTypeEditText;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);


            tripTypeEditText = itemView.findViewById(R.id.tripTypeEditText);
        }
    }

    public void updateTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }
    List<Trip> trips;
}
