package yahoo.view;


public class Query {

    private Integer count;
    private String created;
    private String lang;
    private YahooResult results;

    public Query(Integer count, String created, String lang, YahooResult results) {
        this.count = count;
        this.created = created;
        this.lang = lang;
        this.results = results;
    }

    public Query() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public YahooResult getResults() {
        return results;
    }

    public void setResults(YahooResult results) {
        this.results = results;
    }
}
