package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Objects;

@JsonRootName("Weather")
public class Weather implements JsonModel{
    @JsonProperty("date")
    private String date;

    @JsonProperty("city")
    private String city;

    @JsonProperty("day")
    private String day;

    @JsonProperty("highTemp")
    private String highTemp;

    @JsonProperty("lowTemp")
    private String lowTemp;

    @JsonProperty("description")
    private String description;

    public Weather(String date, String city, String day, String highTemp, String lowTemp, String description) {
        this.date = date;
        this.city = city;
        this.day = day;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
    }

    public Weather() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getDay() {
        return day;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Weather[" +
                "date='" + date + '\'' +
                ", city='" + city + '\'' +
                ", day='" + day + '\'' +
                ", highTemp='" + highTemp + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", description='" + description + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather forecast = (Weather) o;
        return Objects.equals(date, forecast.date) &&
                Objects.equals(city, forecast.city) &&
                Objects.equals(day, forecast.day) &&
                Objects.equals(highTemp, forecast.highTemp) &&
                Objects.equals(lowTemp, forecast.lowTemp) &&
                Objects.equals(description, forecast.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, city, day, highTemp, lowTemp, description);
    }
}
