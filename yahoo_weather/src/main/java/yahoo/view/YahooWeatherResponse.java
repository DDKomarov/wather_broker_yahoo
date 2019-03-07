package yahoo.view;

public class YahooWeatherResponse {

    private Query query;

    public Query getQuery() {
        return query;
    }

    public YahooWeatherResponse(Query query) {
        this.query = query;
    }

    public YahooWeatherResponse() {
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
