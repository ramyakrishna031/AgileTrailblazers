package trailblazers.agile.agiletrailblazers.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import trailblazers.agile.agiletrailblazers.model.WeatherResponse;

public interface ApiInterface {

    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeatherReport(@Query("zip") String zip,@Query("appid") String appId);

}
