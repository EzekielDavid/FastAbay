package hackathon.amydevs.appwhohacksthenight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hackathon.amydevs.appwhohacksthenight.adapters.ItemRecyclerViewAdapter;
import hackathon.amydevs.appwhohacksthenight.models.Item;
import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ItemViewActivity extends BaseActivity {


    private RecyclerView itemsRecyclerView;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(this, new ArrayList<>());
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsRecyclerView.setAdapter(itemRecyclerViewAdapter);
        initializeItems();
    }

    @Override
    protected void initializeViews() {
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView);
    }

    @Override
    protected void initializeListeners() {

    }
    private void initializeItems() {

        HttpConnection.Param[] params = new HttpConnection.Param[1];
        params[0] = new HttpConnection.Param("user_id", sharedPreferences.getInt(Constants.USER_ID, 0));
        HttpConnection.doPost(params, Constants.API_URL + "/getItems", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();

                System.out.println(responseBody);
                MResponse<ArrayList<Item>> responseObject = gson.fromJson(responseBody,
                        new TypeToken<MResponse<ArrayList<Item>>>() {
                        }.getType());

                if (responseObject.isError()) {
                    showMessage(responseObject.getMessage());
                } else {
                    List<Item> items = responseObject.getResult();

                    ItemViewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            itemRecyclerViewAdapter.updateItems(items);
                        }
                    });

                }
            }
        });
    }
}
