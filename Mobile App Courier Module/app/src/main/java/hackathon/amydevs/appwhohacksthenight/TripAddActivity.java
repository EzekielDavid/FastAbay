package hackathon.amydevs.appwhohacksthenight;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import hackathon.amydevs.appwhohacksthenight.models.MResponse;
import hackathon.amydevs.appwhohacksthenight.models.Trip;
import hackathon.amydevs.appwhohacksthenight.models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TripAddActivity extends BaseActivity {
    private EditText tripTypeEditText;
    private EditText countryCodeEditText;
    private EditText descriptionEditText;
    private EditText arrivalTimeEditText;
    private EditText departureTimeEditText;
    private EditText stayDurationEditText;
    private EditText stayLocationEditText;
    private EditText cityEditText;
    private EditText destionationEditText;
    private Button addButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initializeViews() {

        tripTypeEditText = findViewById(R.id.tripTypeEditText);
        countryCodeEditText = findViewById(R.id.countryCodeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        arrivalTimeEditText = findViewById(R.id.arrivalTimeEditText);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        stayDurationEditText = findViewById(R.id.stayDurationEditText);
        stayLocationEditText = findViewById(R.id.stayLocationEditText);
        cityEditText = findViewById(R.id.cityEditText);
        destionationEditText = findViewById(R.id.destinationEditText);
        addButton = findViewById(R.id.addButton);


    }

    private View.OnClickListener calendarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            show_calendar(v);
        }
    };

    @Override
    protected void initializeListeners() {
        addButton.setOnClickListener(addButtonOnClickListener);
        departureTimeEditText.setOnClickListener(calendarOnClickListener);
        arrivalTimeEditText.setOnClickListener(calendarOnClickListener);
    }

    public static Date valuesToDate(int year, int month, int dayOfMonth)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        return calendar.getTime();
    }
    public void show_calendar(final View view)
    {
        final EditText edit_text = (EditText) view;
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog date_picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edit_text.setText(dateFormat.format(valuesToDate(i, i1, i2)));
                edit_text.clearFocus();

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        date_picker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                edit_text.clearFocus();
            }
        });
        date_picker.show();
    }

    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Trip trip = new Trip();

            trip.setTripType(tripTypeEditText.getText().toString());
            trip.setCountryCode(countryCodeEditText.getText().toString());
            trip.setArrivalTime(arrivalTimeEditText.getText().toString());
            trip.setDescription(descriptionEditText.getText().toString());
            trip.setDepartureTime(departureTimeEditText.getText().toString());
            trip.setStayDuration(stayDurationEditText.getText().toString());
            trip.setStayLocation(stayLocationEditText.getText().toString());
            trip.setCity(cityEditText.getText().toString());
            trip.setDestionationName(destionationEditText.getText().toString());

            trip.setUserId(sharedPreferences.getInt("user_id", 0));
            HttpConnection.Param[] params = new HttpConnection.Param[1];

            params[0] = new HttpConnection.Param("trip", gson.toJson(trip));


            System.out.println(gson.toJson(trip));
            HttpConnection.doPost(params, Constants.API_URL + "/postTripUser", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseBody = response.body().string();

                    System.out.println(responseBody);
                    TripAddActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            });

        }
    };
}
