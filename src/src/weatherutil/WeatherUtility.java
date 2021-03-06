package weatherutil;

import com.google.gson.JsonObject;
import file.FileReader;
import file.FileWriter;
import weather.WeatherCurrent;
import weather.WeatherForecast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherUtility {
    public String getCityCurrentData(WeatherCurrent weatherCurrent) {
        return "City : " + weatherCurrent.getCityName() + " \n" +
                "Coordinates : " + weatherCurrent.getCoordinatesAsString() + "\n" +
                "Current Temperature : " + weatherCurrent.getCurrentTemperature() + " \n" +
                "Current Humidity : " + weatherCurrent.getCurrentHumidity();
    }

    private String getCityForecastTemperatures(WeatherForecast weatherForecast) {
        List<JsonObject> forecastObjects = weatherForecast.getAllForecastObjects();
        List<JsonObject> objectsOfCurrentDay = new ArrayList<>();
        StringBuilder returnString = new StringBuilder("");
        Boolean newDay = false;
        Integer lastTime = Integer.parseInt(forecastObjects.get(0).getAsJsonObject().get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
        List<Integer> maxTemps = new ArrayList<>();
        List<Integer> minTemps = new ArrayList<>();
        maxTemps.add(forecastObjects.get(0).getAsJsonObject().getAsJsonObject("main").get("temp_max").getAsInt());
        minTemps.add(forecastObjects.get(0).getAsJsonObject().getAsJsonObject("main").get("temp_min").getAsInt());
        forecastObjects.remove(0);
        Integer dayCount = 1;
        for(JsonObject forecastObj:forecastObjects) {
            if (!newDay) {
                Integer currentTime = Integer.parseInt(forecastObj.get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
                if (currentTime >= 0 && lastTime > currentTime) {
                    newDay = true;
                } else {
                    objectsOfCurrentDay.add(forecastObj);
                }
            } else {
                if (objectsOfCurrentDay.size() != 0) {
                    for (JsonObject weatherObj : objectsOfCurrentDay) {
                        maxTemps.add(weatherObj.get("main").getAsJsonObject().get("temp_max").getAsInt());
                        minTemps.add(weatherObj.get("main").getAsJsonObject().get("temp_min").getAsInt());
                    }
                    returnString.append("Day : ").append(dayCount).append("\n").append("    Max Temp : ")
                            .append(Collections.max(maxTemps)).append("\n").append("    Min Temp : ")
                            .append(Collections.min(minTemps)).append("\n");
                }
                newDay = false;
                objectsOfCurrentDay = new ArrayList<>();
                objectsOfCurrentDay.add(forecastObj);
                dayCount += 1;
                if (dayCount > 3) break;
            }
            lastTime = Integer.parseInt(forecastObj.get("dt_txt").getAsString().split(" ")[1].split(":")[0]);
        }
        return returnString.toString();
    }

    private String getCityBothData(WeatherCurrent weatherCurrent, WeatherForecast weatherForecast) {
        return "City : " + weatherCurrent.getCityName() + "\n" +
                "Coordinates : " + weatherCurrent.getCoordinatesAsString() + "\n" +
                this.getCityForecastTemperatures(weatherForecast) +
                "Current Temp : " + weatherCurrent.getCurrentTemperature();
    }

    public void writeToFileAllCitiesData() throws IOException {
        FileReader fileReader = new FileReader();
        List<String> cities = fileReader.readCitiesFromInput();
        for (String city : cities) {
            WeatherCurrent weatherCurrent = new WeatherCurrent(city);
            WeatherForecast weatherForecast = new WeatherForecast(city);

            FileWriter fileWriter = new FileWriter(weatherCurrent.getCityName());
            System.out.println(this.getCityBothData(weatherCurrent, weatherForecast));
            fileWriter.writeDataIntoOutputFile(this.getCityBothData(weatherCurrent, weatherForecast));
        }
    }

    public static void main(String[] args) {
        try {
            WeatherUtility weatherUtility = new WeatherUtility();
            System.out.println(weatherUtility.getCityForecastTemperatures(new WeatherForecast("Tallinn")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
