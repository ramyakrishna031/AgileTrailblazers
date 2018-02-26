package trailblazers.agile.agiletrailblazers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trailblazers.agile.agiletrailblazers.api.ApiClient;
import trailblazers.agile.agiletrailblazers.api.ApiInterface;
import trailblazers.agile.agiletrailblazers.model.Clouds;
import trailblazers.agile.agiletrailblazers.model.Main;
import trailblazers.agile.agiletrailblazers.model.Sys;
import trailblazers.agile.agiletrailblazers.model.Weather;
import trailblazers.agile.agiletrailblazers.model.WeatherResponse;
import trailblazers.agile.agiletrailblazers.model.Wind;

public class WeatherDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog mProgressDialog;
    private TextView mTextViewErrorMsg;
    private Group mWeatherDetailsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        mTextViewErrorMsg = (TextView) findViewById(R.id.textView_error_msg);
        mWeatherDetailsGroup = (Group) findViewById(R.id.group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getWeatherDetails();
    }


    private void showProgressDialog() {

        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Loading....");
        }

        mProgressDialog.show();
    }

    private void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void getWeatherDetails() {
        showProgressDialog();
        String zipCode = getIntent().getStringExtra("ZipCode");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiService.getWeatherReport(zipCode, getString(R.string.weather_api_key));

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                Log.d("Response: "," "+response);

                if (response != null) {
                    if (response.code() == 200) {
                        WeatherResponse weatherResponse = response.body();
                        displayUI(weatherResponse);
                    } else {
                        showErrorMessage();
                    }
                } else {
                    showErrorMessage();
                }
                dismissProgressDialog();

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                showErrorMessage();
                dismissProgressDialog();
            }
        });

    }

    private void displayUI(WeatherResponse weatherResponse) {

        TextView txtWeatherDescriptionHeader = (TextView) findViewById(R.id.textView_weather_description_header);
        TextView txtWeatherSubDescriptionHeader = (TextView) findViewById(R.id.textView_weather_description_sub_header);
        TextView txtTempValue = (TextView) findViewById(R.id.textView_temperature_value);
        TextView txtWindSpeed = (TextView) findViewById(R.id.textView_windSpeed_Value);
        TextView txtCloudiness = (TextView) findViewById(R.id.textView_cloudiness_Value);
        TextView txtPressure = (TextView) findViewById(R.id.textView_pressure_Value);
        TextView txtHumidity = (TextView) findViewById(R.id.textView_humidity_Value);
        TextView txtSunrise = (TextView) findViewById(R.id.textView_sunrise_Value);
        TextView txtPlace = (TextView) findViewById(R.id.textView_place);

        txtPlace.setText(getString(R.string.measured, weatherResponse.getName()));
        Weather[] weatherArray = weatherResponse.getWeather();
        if (weatherArray.length > 0) {
            Weather weather = weatherArray[0];
            txtWeatherDescriptionHeader.setText(weather.getMain());
            txtWeatherSubDescriptionHeader.setText(weather.getDescription());

        }

        Main main = weatherResponse.getMain();
        if (main != null) {
            txtTempValue.setText(String.valueOf(main.getTemp()));
            txtHumidity.setText(getString(R.string.humidity_value,String.valueOf(main.getHumidity())));
            txtPressure.setText(getString(R.string.pressure_value,String.valueOf(main.getPressure())));
        }
        Sys sys = weatherResponse.getSys();
        if (sys != null) {
            txtSunrise.setText(Utils.getTimeFromUnixTimeStamp(sys.getSunrise()));
        }

        Wind wind = weatherResponse.getWind();
        if(wind != null) {
            txtWindSpeed.setText(getString(R.string.wind_speed_value,String.valueOf(wind.getSpeed())));
        }

        Clouds clouds = weatherResponse.getClouds();
        if(clouds != null) {
            txtCloudiness.setText(clouds.getAll());
        }

    }

    private void showErrorMessage() {
        mTextViewErrorMsg.setVisibility(View.VISIBLE);
        mWeatherDetailsGroup.setVisibility(ConstraintLayout.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
