package domain.websites;

import domain.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Togetter extends Website {

    //needs improvement - dates and weird links showing. edit Elements!


    public Togetter(String articleURL) throws IOException {
        super(articleURL,"h1","div.tweet_box");
    }

    @Override
    public Elements getElements() throws IOException {
        Elements togetterElements = getDoc().select(getArticleContainerTag());
        togetterElements.select("a.user_link").remove(); //removes user profile pics & names but keep text :)
        togetterElements.select("span.status").remove(); //removes dates
        togetterElements.select("div.more_tweet_box").remove(); //removes weird end bits
        return togetterElements;
    }

    @Override
    public String getFirstImgUrl() throws IOException{
        return getElements().select("img").attr("data-lazy-src");
    }

    @Override
    public String getImgUrl(Document doc) throws IOException{
        return doc.select("img").attr("data-lazy-src");
    }

}
