package trailblazers.agile.agiletrailblazers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import trailblazers.agile.agiletrailblazers.model.Weather;
import trailblazers.agile.agiletrailblazers.model.WeatherResponse;

import static org.junit.Assert.assertEquals;

public class WeatherModelTest {

    private String json;
    @Before
    public void before() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("Weather.json");
        json = IOUtils.toString(in, "UTF-8");
    }

    @Test
    public void testInitializeWeatherFromJSON() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Weather weather = gson.fromJson(json, Weather.class);
        JSONObject weatherJson = new JSONObject(json);

        assertEquals(weather.getDescription(), weatherJson.getString("description"));
        assertEquals(weather.getMain(), weatherJson.getString("main"));

    }


}
