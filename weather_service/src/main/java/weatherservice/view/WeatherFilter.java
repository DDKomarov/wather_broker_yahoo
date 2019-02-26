package weatherservice.view;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WeatherFilter {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;
    public String city;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public WeatherFilter(Date date, String city) {
        this.date = date;
        this.city = city;
    }

    public WeatherFilter() {
    }

    @Override
    public String toString() {
        return "WeatherFilter: {" +
                "date=" + date +
                ", city='" + city + '\'' +
                '}';
    }
}
