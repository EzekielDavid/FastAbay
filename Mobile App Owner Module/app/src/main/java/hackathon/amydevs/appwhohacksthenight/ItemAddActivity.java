package hackathon.amydevs.appwhohacksthenight;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import hackathon.amydevs.appwhohacksthenight.models.Item;
import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ItemAddActivity extends BaseActivity {


    private Button addButton;
    private Button pictureButton;
    private EditText weightEditText;
    private EditText boxLengthEditText;
    private EditText boxWidthEditText;
    private EditText boxHeightEditText;
    private EditText estValueEditText;
    private EditText fragileEditText;
    private EditText remarksEditText;
    private EditText countryCodeEditText;
    private Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeViews() {

        addButton = findViewById(R.id.addButton);
        pictureButton = findViewById(R.id.pictureButton);

        weightEditText = findViewById(R.id.weightEditText);
        boxLengthEditText = findViewById(R.id.boxLengthEditText);
        boxWidthEditText = findViewById(R.id.boxWidthEditText);
        boxHeightEditText = findViewById(R.id.boxHeightEditText);
        estValueEditText = findViewById(R.id.estValueEditText);
        fragileEditText = findViewById(R.id.fragileEditText);
        remarksEditText = findViewById(R.id.remarksEditText);
        countryCodeEditText = findViewById(R.id.countryCodeEditText);
    }

    @Override
    protected void initializeListeners() {

        addButton.setOnClickListener(addButtonOnClickListener);
        pictureButton.setOnClickListener(pictureButtonOnClickListener);
    }

    private View.OnClickListener pictureButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK )
        {
            if( requestCode == 1 )
            {

                Bitmap result_bitmap = (Bitmap) data.getExtras().get("data");
              //  ((ImageView) findViewById(R.id.image_result)).setImageBitmap(result_bitmap);
              //  photo_bitmap = result_bitmap;
                bitmap = result_bitmap;
            }
        }
    }

    public static String encryptBitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
    }
    public static Bitmap decryptBitmap(String encrypted_bitmap)
    {
        byte[] decoded_bitmap = Base64.decode(encrypted_bitmap, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded_bitmap, 0,  decoded_bitmap.length);
    }

    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Item item = new Item();

            item.setWeight(Double.parseDouble(weightEditText.getText().toString()));
            item.setBoxLength(Double.parseDouble(boxLengthEditText.getText().toString()));
            item.setBoxWidth(Double.parseDouble(boxWidthEditText.getText().toString()));
            item.setBoxHeight(Double.parseDouble(boxHeightEditText.getText().toString()));
            item.setEstValue(Double.parseDouble(estValueEditText.getText().toString()));
            item.setFragile(Integer.parseInt(fragileEditText.getText().toString()));
            item.setRemarks(remarksEditText.getText().toString());
            item.setCountryCode(Integer.parseInt(countryCodeEditText.getText().toString()));
            item.setImage(encryptBitmap(bitmap));
            item.setOwnerId(sharedPreferences.getInt(Constants.USER_ID, 0));
            String json = gson.toJson(item);


            System.out.println(json);
            HttpConnection.Param[] params = new HttpConnection.Param[1];

            System.out.println("gagagag"+sharedPreferences.getInt(Constants.USER_ID, 0));
            params[0] = new HttpConnection.Param("item", json);

            HttpConnection.doPost(params, Constants.API_URL + "/postItemUser", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseBody = response.body().string();

                    System.out.println(responseBody);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ItemAddActivity.this.finish();
                        }
                    });
                }
            });
        }
    };
}
