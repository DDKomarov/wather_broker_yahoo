package db.entity;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Прогноз погоды
 */
@Entity
//@Table(name = "weather")
public class WeatherEntity {

    /**
     * Идентификатор проноза
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Служебное поле hibernate
     */
    @Version
    private Integer version;

    /**
     * Дата прогноза
     */
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date date;

    /**
     * Название города
     */
    @NotNull
    private String city;

    /**
     * День прогноза
     */
    @NotNull
    @Max(3)
    private String day;

    /**
     * Верхняя граница темпиратуры
     */
    @NotNull
    @Max(3)
    private String highTemp;

    /**
     * Нижняя граница температуры
     */
    @NotNull
    @Max(3)
    private String lowTemp;

    /**
     * Описание погоды
     */
    @NotNull
    private String description;

    public WeatherEntity(@NotNull Date date, @NotNull String city, @NotNull String day, @NotNull String highTemp,
                         @NotNull String lowTemp, @NotNull String description) {
        this.date = date;
        this.city = city;
        this.day = day;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
    }

    public WeatherEntity() {
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherEntity[" +
                "id=" + id +
                ", version=" + version +
                ", date=" + date +
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
        WeatherEntity entity = (WeatherEntity) o;
        return Objects.equals(date, entity.date) &&
                Objects.equals(city, entity.city) &&
                Objects.equals(day, entity.day) &&
                Objects.equals(highTemp, entity.highTemp) &&
                Objects.equals(lowTemp, entity.lowTemp) &&
                Objects.equals(description, entity.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, city, day, highTemp, lowTemp, description);
    }
}






