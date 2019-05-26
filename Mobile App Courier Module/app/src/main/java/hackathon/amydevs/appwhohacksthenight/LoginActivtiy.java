package hackathon.amydevs.appwhohacksthenight;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivtiy extends BaseActivity {


    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initializeViews() {

        loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    @Override
    protected void initializeListeners() {

        loginButton.setOnClickListener(onLoginButtonClickListener);
    }

    private View.OnClickListener onLoginButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            HttpConnection.Param[] params = new HttpConnection.Param[2];

            params[0] = new HttpConnection.Param("username", usernameEditText.getText().toString());
            params[1] = new HttpConnection.Param("password", passwordEditText.getText().toString());


            HttpConnection.doPost(params, Constants.API_URL + "/login", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseBody = response.body().string();

                    System.out.println(responseBody);
                    MResponse<User> responseObject = gson.fromJson(responseBody,
                            new TypeToken<MResponse<User>>() {
                            }.getType());

                    if (responseObject.isError()) {
                        showMessage(responseObject.getMessage());
                    } else {
                        User user = responseObject.getResult();
                        sharedPreferencesEditor.putInt(Constants.USER_ID, user.getId());
                        sharedPreferencesEditor.apply();
                        Intent intent = new Intent(LoginActivtiy.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                }
            });

        }
    };
}
