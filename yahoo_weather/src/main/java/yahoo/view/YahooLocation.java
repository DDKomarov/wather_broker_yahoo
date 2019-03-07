package yahoo.view;


public class YahooLocation {

    private String city;
    private String country;
    private String region;

    public YahooLocation(String city, String country, String region) {
        this.city = city;
        this.country = country;
        this.region = region;
    }

    public YahooLocation() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
