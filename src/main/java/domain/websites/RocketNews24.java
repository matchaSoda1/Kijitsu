package domain.websites;

import domain.Website;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class RocketNews24 extends Website {

    private Document rocketnews24;

    public RocketNews24(String articleURL) throws IOException {
        super(articleURL,"h2.entry-title","div.entry-content");
    }

    @Override
    public Elements getElements() throws IOException {
        Elements articleElements = getDoc().select(getArticleContainerTag()); //could be a super function
        articleElements.select("div.extra-content").remove();
        return articleElements;
    }

    @Override
    public String getFirstImgUrl() throws IOException {
        return getElements().select("img[src~=(?i)\\.(png|jpe?g)]").attr("src");
    }

    @Override
    public String getImgUrl(Document doc) throws IOException {
        return doc.select("img[src~=(?i)\\.(png|jpe?g)]").attr("src");
    }

}

