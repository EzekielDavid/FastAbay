package hackathon.amydevs.appwhohacksthenight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hackathon.amydevs.appwhohacksthenight.adapters.TripRecyclerViewAdapter;
import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.Trip;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TripViewActivity extends BaseActivity {


    private RecyclerView tripsRecyclerView;
    private TripRecyclerViewAdapter tripRecyclerViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeViews() {

        tripsRecyclerView = findViewById(R.id.tripsRecyclerView);
    }

    @Override
    protected void initializeListeners() {


        tripRecyclerViewAdapter = new TripRecyclerViewAdapter(this, new ArrayList<>());
        tripsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripsRecyclerView.setAdapter(tripRecyclerViewAdapter);
        initializeTrips();

    }

    private void initializeTrips() {
        HttpConnection.Param[] params = new HttpConnection.Param[1];
        params[0] = new HttpConnection.Param("user_id", sharedPreferences.getInt(Constants.USER_ID, 0));
        HttpConnection.doPost(params, Constants.API_URL + "/getITemTripCourier", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();

                System.out.println(responseBody);
                MResponse<ArrayList<Trip>> responseObject = gson.fromJson(responseBody,
                        new TypeToken<MResponse<ArrayList<Trip>>>() {
                        }.getType());

                if (responseObject.isError()) {
                    showMessage(responseObject.getMessage());
                } else {
                    List<Trip> trips = responseObject.getResult();


                    TripViewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tripRecyclerViewAdapter.updateTrips(trips);
                        }
                    });

                }
            }
        });
    }
}
