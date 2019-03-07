package weather_service.view;

import java.util.Date;
import java.util.Objects;

public class WeatherView {
    public Date date;
    public String city;
    public String day;
    public String highTemp;
    public String lowTemp;
    public String description;

    public WeatherView() {
    }

    public WeatherView(Date date, String city, String day, String highTemp, String lowTemp, String description) {
        this.date = date;
        this.city = city;
        this.day = day;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ForecastView{" +
                "date=" + date +
                ", city='" + city + '\'' +
                ", day='" + day + '\'' +
                ", highTemp='" + highTemp + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherView that = (WeatherView) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(city, that.city) &&
                Objects.equals(day, that.day) &&
                Objects.equals(highTemp, that.highTemp) &&
                Objects.equals(lowTemp, that.lowTemp) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, city, day, highTemp, lowTemp, description);
    }
}
