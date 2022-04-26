package domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Website {
    private String articleURL;
    private String articleTitleTag;
    private String articleContainerTag;
    private Document doc;

    public Website(String articleURL, String articleTitleTag, String articleContainerTag) throws IOException {
        this.articleURL = articleURL;
        this.articleTitleTag = articleTitleTag;
        this.articleContainerTag = articleContainerTag;
        this.doc = Jsoup.connect(articleURL).get();
    }
    public Website(String articleURL) throws IOException {
        this(articleURL, "h1", "p");
    }



    public String getFirstImgUrl() throws IOException {
        if (!hasImages()) {
            return "";
        }
        return getElements().select("img").attr("src");
    }

    public String getImgUrl(Document doc) throws IOException {
        return doc.select("img").attr("src");
    }


    //commonly shared methods between websites
    public Elements getElements() throws IOException {
        return doc.select(articleContainerTag);
    }

    public String getHtml() throws IOException {
        return getElements().html();
    }

    public boolean hasImages() throws IOException {
        return !getFirstImgUrl().isBlank();
    }

    public boolean hasText(Document doc) {
        return doc.hasText();
    }

    public boolean hasImage(Document document) throws IOException {
        return !getImgUrl(document).isEmpty();
    }

    public static String getText(Document document) {
        return document.text();
    }

    //getters & setters =)
    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getArticleTitle() {
        return doc.select(articleTitleTag).text();
    }

    public String getArticleTitleTag() {
        return articleTitleTag;
    }

    public void setArticleTitleTag(String articleTitleTag) {
        this.articleTitleTag = articleTitleTag;
    }

    public String getArticleContainerTag() {
        return articleContainerTag;
    }

    public void setArticleContainerTag(String articleContainerTag) {
        this.articleContainerTag = articleContainerTag;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String toString() {
        return getClass() + ": " + this.articleURL;
    }



}
