package yahoo.view;


public class YahooItem {

    private YahooWeather forecast;

    public YahooItem(YahooWeather forecast) {
        this.forecast = forecast;
    }

    public YahooItem() {
    }

    public YahooWeather getForecast() {
        return forecast;
    }

    public void setForecast(YahooWeather forecast) {
        this.forecast = forecast;
    }
}
