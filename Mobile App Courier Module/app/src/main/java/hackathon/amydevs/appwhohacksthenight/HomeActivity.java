package hackathon.amydevs.appwhohacksthenight;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.view.MenuItem;

import com.daprlabs.cardstack.SwipeDeck;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hackathon.amydevs.appwhohacksthenight.models.Item;
import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.Trip;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends BaseActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 722)
        {
            if ( permissions[0] == Manifest.permission.CAMERA  )
            {
                if ( grantResults[0] == PackageManager.PERMISSION_GRANTED )
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }
            }
        }

    }
    private NavigationView navigationView;
    SwipeDeckAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                Intent intent;
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.addTrip:
                        intent = new Intent(HomeActivity.this, TripAddActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.viewTrip:
                        intent = new Intent(HomeActivity.this, TripViewActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.viewItem:


                        break;

                }

                return false;
            }
        });
        initializeCards();
    }

    @Override
    protected void initializeViews() {

    }

    @Override
    protected void initializeListeners() {

    }

    @Override
    public void onBackPressed() {

    }

    public void initializeCards() {
        HttpConnection.Param[] params = new HttpConnection.Param[1];
        params[0] = new HttpConnection.Param("user_id", sharedPreferences.getInt("user_id", 0));
        HttpConnection.doPost(params, Constants.API_URL + "/getITemTripCourier", new Callback() {
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            SwipeDeck cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
                            adapter = new SwipeDeckAdapter(items, HomeActivity.this);
                            cardStack.setAdapter(adapter);

                            cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                                @Override
                                public void cardSwipedLeft(int position) {

                                }

                                @Override
                                public void cardSwipedRight(int position) {
                                }

                                @Override
                                public void cardsDepleted() {
                                }

                                @Override
                                public void cardActionDown() {

                                }

                                @Override
                                public void cardActionUp() {

                                }
                            });
                        }
                    });




                }
            }
        });
    }
}
