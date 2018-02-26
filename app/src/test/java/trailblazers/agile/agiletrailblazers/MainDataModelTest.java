package trailblazers.agile.agiletrailblazers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import trailblazers.agile.agiletrailblazers.model.Main;
import trailblazers.agile.agiletrailblazers.model.Weather;

import static org.junit.Assert.assertEquals;

public class MainDataModelTest {

    private static final double DELTA = 1e-15;
    private String json;
    @Before
    public void before() throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("MainData.json");
        json = IOUtils.toString(in, "UTF-8");
    }

    @Test
    public void testInitializeWeatherFromJSON() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Main main = gson.fromJson(json, Main.class);
        JSONObject mainJson = new JSONObject(json);

        assertEquals(main.getHumidity(), mainJson.getDouble("humidity"),DELTA);
        assertEquals(main.getTemp(), mainJson.getDouble("temp"),DELTA);
        assertEquals(main.getPressure(), mainJson.getDouble("pressure"),DELTA);


    }


}
