package hackathon.amydevs.appwhohacksthenight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hackathon.amydevs.appwhohacksthenight.models.Item;
import hackathon.amydevs.appwhohacksthenight.models.Trip;

public class SwipeDeckAdapter extends BaseAdapter {
    private List<Item> data;
    private Context context;

    public SwipeDeckAdapter(List<Item> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.swipe_item_layout, parent, false);
        }


        Item item = (Item) getItem(position);


        TextView weightTextView = convertView.findViewById(R.id.weightTextView);
        TextView boxLengthTextView = convertView.findViewById(R.id.boxLengthTextView);
        TextView boxHeightTextView = convertView.findViewById(R.id.boxHeightTextView);
        TextView boxWidthTextView = convertView.findViewById(R.id.boxWidthTextView);
        TextView descTextView = convertView.findViewById(R.id.descTextView);
        ImageView idpota = convertView.findViewById(R.id.idpota);


        String[] arr = new String[] {
                "Lenovo laptop to deliver to USA",
                "Coffee for my loved one",
                "Gravy made by gordon ramsaay",
                "ballpen for my kid"
        };
        weightTextView.setText("Weight: " + Double.toString(item.getWeight()));
        boxLengthTextView.setText("Box length: " + Double.toString((item.getBoxLength())));
        boxHeightTextView.setText("Box Height: " + Double.toString(item.getBoxHeight()));
        boxWidthTextView.setText("Box Width: " + Double.toString(item.getBoxWidth()));
        descTextView.setText(arr[position]);

        idpota.setImageBitmap(decryptBitmap(item.getImage()));




        return convertView;
    }
    public void updateItem(List<Item> items){
        this.data = items;
        notifyDataSetChanged();
    }
    public static Bitmap decryptBitmap(String encrypted_bitmap)
    {
        byte[] decoded_bitmap = Base64.decode(encrypted_bitmap, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded_bitmap, 0,  decoded_bitmap.length);
    }

}
