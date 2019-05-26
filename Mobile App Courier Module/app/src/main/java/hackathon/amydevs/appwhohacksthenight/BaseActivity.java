package hackathon.amydevs.appwhohacksthenight;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

public abstract class BaseActivity extends AppCompatActivity {

    protected Gson gson;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor sharedPreferencesEditor;
    protected SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gson = new Gson();
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        initialize();
    }
    protected abstract void initializeViews();

    protected abstract void initializeListeners();

    private void initialize() {
        if ( this instanceof LoginActivtiy ) {
            setContentView(R.layout.login_layout);
        } else if ( this instanceof HomeActivity) {
            setContentView(R.layout.home_layout);
        } else if ( this instanceof TripAddActivity ) {
            setContentView(R.layout.trip_add_layout);
        } else if ( this instanceof  TripViewActivity ) {
            setContentView(R.layout.trip_view_layout);
        }
        initializeViews();
        initializeListeners();


    }


    protected void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
