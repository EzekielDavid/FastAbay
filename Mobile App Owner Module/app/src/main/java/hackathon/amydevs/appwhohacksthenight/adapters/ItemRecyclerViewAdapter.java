package hackathon.amydevs.appwhohacksthenight.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hackathon.amydevs.appwhohacksthenight.R;
import hackathon.amydevs.appwhohacksthenight.models.Item;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>  {

    private Context context;
    public ItemRecyclerViewAdapter(Context context, List<Item> items) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_item, viewGroup, false);

        ItemViewHolder  viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {

        Item item = items.get(i);



        itemViewHolder.remarksTextView.setText(item.getRemarks());
        itemViewHolder.weightTextView.setText(Double.toString(item.getWeight()));
        itemViewHolder.boxWidthTextView.setText(Double.toString(item.getBoxWidth()));
        itemViewHolder.boxLengthTextView.setText(Double.toString(item.getBoxLength()));
        itemViewHolder.boxHeightTextView.setText(Double.toString(item.getBoxHeight()));
        itemViewHolder.estValueTextView.setText(Double.toString(item.getEstValue()));
        itemViewHolder.fragileTextView.setText(Integer.toString(item.isFragile()));
        itemViewHolder.countryCodeTextView.setText(Integer.toString(item.getCountryCode()));
    }



    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView weightTextView;
        private TextView boxHeightTextView;
        private TextView boxLengthTextView;
        private TextView boxWidthTextView;
        private TextView estValueTextView;
        private TextView remarksTextView;
        private TextView fragileTextView;
        private TextView countryCodeTextView;
        public ItemViewHolder(View itemView) {

            super(itemView);


            weightTextView = itemView.findViewById(R.id.weightTextView);
            boxHeightTextView = itemView.findViewById(R.id.boxHeightTextView);
            boxLengthTextView = itemView.findViewById(R.id.boxLengthTextView);
            boxWidthTextView = itemView.findViewById(R.id.boxWidthTextView);
            estValueTextView = itemView.findViewById(R.id.estValueTextView);
            remarksTextView = itemView.findViewById(R.id.remarksTextView);
            fragileTextView = itemView.findViewById(R.id.fragileTextView);
            countryCodeTextView = itemView.findViewById(R.id.countryCodeTextView);

        }
    }
    private List<Item> items;

    public void updateItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

}
