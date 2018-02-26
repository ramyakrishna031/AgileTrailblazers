package trailblazers.agile.agiletrailblazers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import trailblazers.agile.agiletrailblazers.model.WeatherResponse;

import static org.junit.Assert.assertEquals;

public class WeatherResponseTest {
    private String json;

    @Before
    public void before() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("WeatherResponse.json");
        json = IOUtils.toString(in, "UTF-8");
    }

    @Test
    public void testInitializeWeatherFromJSON() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        WeatherResponse weatherResponse = gson.fromJson(json, WeatherResponse.class);
        JSONObject weatherResponseJson = new JSONObject(json);

        assertEquals(weatherResponse.getName(), weatherResponseJson.getString("name"));
        assertEquals(weatherResponse.getCod(), weatherResponseJson.getInt("cod"));
        assertEquals(weatherResponse.getWeather().length, weatherResponseJson.getJSONArray("weather").length());
    }
}
