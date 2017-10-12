import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import weather.CurrentWeather;
import weather.ForecastWeather;
import weatherdata.WeatherData;

import java.io.IOException;

public class WeatherGateway {
    private CurrentWeather currentWeather;
    private ForecastWeather forecastWeather;

    private WeatherGateway(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
        forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
    }

    public void getNewCurrentWeather() throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=Tallinn,ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeather();
    }

    public void getNewCurrentWeather(String cityName) throws IOException{
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        currentWeather = CurrentWeather.getCurrentWeatherByCity(cityName);
    }

    public void getNewForecastWeather() throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=Tallinn&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = ForecastWeather.getForecastWeather();
    }

    public void getNewForecastWeather(String cityName) throws IOException{
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        WeatherData weatherData = new WeatherData();
        forecastWeather = ForecastWeather.getForecastWeatherByCity(cityName);
    }

    /*
    private WeatherGateway(JsonObject currentWeather) throws IOException{
        String city = "Tallinn";
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        this.currentWeather = currentWeather;
        forecastWeather = weatherData.getJsonData(forecastWeatherUrl).get("list").getAsJsonArray();
    }

    private WeatherGateway(JsonArray forecastWeather) throws IOException{
        String city = "Tallinn";
        String currentWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ee&appid=1213b3bd7d7dd50d09ce5464347f3c71";
        String forecastWeatherUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&APPID=1213b3bd7d7dd50d09ce5464347f3c71";

        weatherdata.WeatherData weatherData = new weatherdata.WeatherData();
        currentWeather = weatherData.getJsonData(currentWeatherUrl).getAsJsonObject();
        this.forecastWeather = forecastWeather;
    }

    private WeatherGateway(JsonObject currentWeather, JsonArray forecastWeather) {


        this.currentWeather = currentWeather;
        this.forecastWeather = forecastWeather;
    }
    */

    static WeatherGateway getWeatherGatewayByCity() throws  IOException{
        return new WeatherGateway("Tallinn");
    }

    static WeatherGateway getWeatherGatewayByCity(String cityName) throws IOException{
        return new WeatherGateway(cityName);
    }

    /*
    public static WeatherGateway getWeatherGatewayByCurrentWeather(JsonObject currentWeather) throws IOException{
        return new WeatherGateway(currentWeather);
    }

    public static WeatherGateway getWeatherGatewayByForecastWeather(JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(forecastWeather);
    }

    public static WeatherGateway getWeatherGatewayByBoth(JsonObject currentWeather, JsonArray forecastWeather) throws IOException{
        return new WeatherGateway(currentWeather, forecastWeather);
    }*/

    JsonObject getForecastObjectFromArray(int index) {
        return forecastWeather.getForecastObjectFromArray(index);
    }

    int getForecastArrayLength() {
        return forecastWeather.getForecastArrayLength();
    }

    double getCurrentTemperature() {
        return currentWeather.getCurrentTemperature();
    }

    double getCurrentTemperatureFromArrayObject(JsonObject obj) { return currentWeather.getCurrentHumidityFromArrayObject(obj); }

    int getCurrentHumidityFromArrayObject(JsonObject obj) { return currentWeather.getCurrentHumidityFromArrayObject(obj); }

    int getCurrentHumidity() { return currentWeather.getCurrentHumidity(); }

    String getCountryCode() {
        return currentWeather.getCountryCode();
    }

    String getCityName() { return currentWeather.getCityName(); }

    String getCurrentCityData() {
        return "City : " + this.getCityName() + "\n" +
                "Current Temperature : " + this.getCurrentTemperature() + "\n" +
                "Current Humidity : " + this.getCurrentHumidity();
    }

    public static void main(String[] args) throws IOException{
        String cityName = "Tallinn";
        if (args.length > 0) {
            cityName = args[0];
        }
        WeatherGateway weatherGateway = WeatherGateway.getWeatherGatewayByCity(cityName);
        System.out.println(weatherGateway.getCurrentCityData());
    }
}
