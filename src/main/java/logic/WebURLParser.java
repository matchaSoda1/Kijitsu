package logic;

import domain.Website;
import domain.websites.NHK;
import domain.websites.RocketNews24;
import domain.websites.Togetter;

import java.io.IOException;

public class WebURLParser {

    public static Website getWebsite(String articleUrl) throws IOException {
        if (articleUrl.isBlank()) {
            throw new RuntimeException("Article URL is blank");
        }
        if (articleUrl.contains("rocketnews24")){
            return new RocketNews24(articleUrl);
        } else if (articleUrl.contains("nhk.or.jp/news/html")){
            return new NHK(articleUrl);
        } else if (articleUrl.contains("togetter.com/li")){
            return new Togetter(articleUrl);
        }
        return new Website(articleUrl);
    }

}
