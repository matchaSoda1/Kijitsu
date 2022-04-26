package domain.websites;

import domain.Website;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NHK extends Website {
    //getImgUrl - should be one line only. previously it returns the below but then cardCreator always returns "https://www3.nhk.or.jp" because getImgURl isn't empty. probably need to modify cardCreator process
    //with something like hasImg(Doc line) -> return !line.select("img").isEmpty();

    //return "https://www3.nhk.or.jp" + doc.select("img").attr("data-src");


    public NHK(String articleURL) throws IOException {
        super(articleURL, "h1", ".module--detail-content");
    }

    @Override
    public Elements getElements()  {
        Elements articleElements = getDoc().select(getArticleContainerTag()); //could be a super function
        articleElements.select("h1.content--title").remove(); //remove title otherwise title card will repeat twice
        return articleElements;
    }

    @Override
    public String getFirstImgUrl() {
        return getElements().select("img").attr("abs:data-src");
    }

    @Override
    public String getImgUrl(Document doc)  {
        String imgUrl = doc.select("img").attr("data-src");
        if (!imgUrl.isEmpty()) {
            return "https://www3.nhk.or.jp" + doc.select("img").attr("data-src");
        }
        return "";
    }


}

