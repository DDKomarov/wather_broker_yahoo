package weatherservice.view;


public class YahooResult {

    private YahooChannel[] channel;

    public YahooResult(YahooChannel[] channel) {
        this.channel = channel;
    }

    public YahooResult() {
    }

    public YahooChannel[] getChannel() {
        return channel;
    }

    public void setChannel(YahooChannel[] channel) {
        this.channel = channel;
    }
}
