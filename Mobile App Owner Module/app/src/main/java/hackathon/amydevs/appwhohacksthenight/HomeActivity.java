package hackathon.amydevs.appwhohacksthenight;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.daprlabs.cardstack.SwipeDeck;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

        private NavigationView navigationView;
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
                    case R.id.addItem:
                        intent = new Intent(HomeActivity.this, ItemAddActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.viewItem:
                        intent = new Intent(HomeActivity.this, ItemViewActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logoutItem:


                        break;

                }

                return false;
            }
        });
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
}
