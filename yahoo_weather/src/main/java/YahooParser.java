import com.fasterxml.jackson.core.JsonParser;
import model.Weather;
import org.apache.log4j.Logger;

import java.io.InputStream;

public class YahooParser {
    private static Logger log = Logger.getLogger(YahooParser.class);

    public Weather parse(InputStream inputStream) throws Exception {
        Weather weather = new Weather();

        log.info( "Creating JSON Reader" );
//        SAXReader xmlReader = createXmlReader();
//        Document doc = xmlReader.read( inputStream );

//        JsonParser parser = new JsonParser().
//        log.info( "Parsing JSON Response" );
//        weather.setCity( doc.valueOf("/rss/channel/y:location/@city") );
//        weather.setRegion( doc.valueOf("/rss/channel/y:location/@region") );
//        weather.setCountry( doc.valueOf("/rss/channel/y:location/@country") );
//        weather.setCondition( doc.valueOf("/rss/channel/item/y:condition/@text") );
//        weather.setTemp( doc.valueOf("/rss/channel/item/y:condition/@temp") );
//        weather.setChill( doc.valueOf("/rss/channel/y:wind/@chill") );
//        weather.setHumidity( doc.valueOf("/rss/channel/y:atmosphere/@humidity") );

        return weather;
    }
}
