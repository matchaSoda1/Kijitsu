package logic;

import domain.Card;
import domain.Picture;
import domain.Website;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import settings.TextParsingSettings;
import static settings.TextParsingSettings.keepFullStopDelimiter;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.Scanner;


public class CardCreator {

    private Website website;
    private Picture mainPicture;

    public CardCreator(String articleUrl) throws IOException {
        this.website = WebURLParser.getWebsite(articleUrl);
    }


    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
    }

    public Picture getMainPicture() {
        return this.mainPicture;
    }

    public List<Card> createCardsFromFile(String fileName) throws IOException {
        return createCardsFromFile(fileName, null);
    }

    public List<Card> createCardsFromFile(String fileName, Picture picture) throws IOException {
        List<String> textLines = new TextFileParser().readFile(fileName);
        List<Card> cards = new ArrayList<>();
        for (String text : textLines) {
            if (!(picture == null)) {
                cards.add(new Card(text, picture));
            } else {
                cards.add(new Card(text));
            }
        }
        return cards;
    }

    public String getFirstImageUrl() throws IOException {
        String imageURL = "";
        if (this.mainPicture == null) {
            imageURL = website.getFirstImgUrl();
        } else {
            imageURL = this.mainPicture.getUrl();
        }
        return imageURL;
    }

    public List<Card> articleToCards() throws IOException {
        List<Card> articleToCards = new ArrayList<>();
        articleToCards.add(createTitleCard());
        articleToCards.addAll(createArticleBodyCards());
        return articleToCards;
    }

    public Card createTitleCard() throws IOException {
        String articleTitle = website.getArticleTitle();
        String imageURL = getFirstImageUrl();

        if (!imageURL.isBlank()) {
            Picture articleMainPicture = new Picture(imageURL);
            return new Card(articleTitle, articleMainPicture);
        }
        return new Card(articleTitle);
    }

    public List<Card> createArticleBodyCards() throws IOException {
        List<Card> cards = new ArrayList<>();

        String html = website.getHtml();
        String imgUrl = website.getFirstImgUrl(); //works

        Scanner reader = new Scanner(html);
        String nextLine = "";

        while (reader.hasNextLine()) {

            nextLine = reader.nextLine();
            Document line = Jsoup.parse(nextLine);

            if (website.hasImages() && website.hasImage(line)) {
                imgUrl = website.getImgUrl(line);
            }

            if (!hasText(line)) { //don't make a card with no text
                continue;
            }

            //fetch text of element & split long texts into single sentences if necessary
            String lineText = website.getText(line);

            String[] cardSentences = {""};

            if (containsPeriod(lineText)) {
                cardSentences = lineText.split(keepFullStopDelimiter);
            } else {
                cardSentences[0] = lineText;
            }

            for (String cardSentence : cardSentences) {
                Card newCard = new Card(cardSentence);
                if (website.hasImages()) {
                    newCard.setPicture(new Picture(imgUrl));
                }
                cards.add(newCard);
            }
        }

        return cards;
    }

    private boolean hasText(Document line) {
        return line.hasText();
    }

    private boolean containsPeriod(String text) {
        return text.contains(TextParsingSettings.period);
    }

    public void addBeforeContextIntoOneField(List<Card> cards, String contextBeforeField, int numberOfLines) {
        for (int i = 0; i < cards.size(); i++) {
            StringBuilder sb = new StringBuilder();
            Card currentCard = cards.get(i);
            for (int j = i - 1; j >= i - numberOfLines; j--) {

                if (j < 0) {
                    continue;
                }

                Card cardAtJ = cards.get(j);
                String cardAtJExpression = cardAtJ.getTextFields().getFieldText("Expression");
                sb.append(cardAtJExpression);
                sb.append("\n");
                currentCard.setTextFields(contextBeforeField, sb.toString());

            }
        }
    }

    public void addBeforeContext(List<Card> cards, String[] contextBeforeFields, int numberOfLines) {
        if (contextBeforeFields.length != numberOfLines) {
            throw new IllegalArgumentException("Number of context fields must be equal to the number of lines");
        }
        for (int i = 0; i < cards.size(); i++) {
            StringBuilder sb = new StringBuilder();
            Card currentCard = cards.get(i);
            int i1 = 0;
            for (int j = i - 1; j >= i - numberOfLines; j--) {

                if (j < 0) {
                    continue;
                }

                Card cardAtJ = cards.get(j);
                String cardAtJExpression = cardAtJ.getTextFields().getFieldText("Expression");
                sb.append(cardAtJExpression);
                sb.append("\n");
                currentCard.setTextFields(contextBeforeFields[i1], sb.toString());
                i1++;
            }
        }
    }

    public void addAfterContextIntoOneField(List<Card> cards, String contextAfterField, int numberOfLines) {
        for (int i = 0; i < cards.size(); i++) {
            StringBuilder sb = new StringBuilder();
            Card currentCard = cards.get(i);
            for (int j = i + 1; j <= i + numberOfLines; j++) {

                if (j >= cards.size()) {
                    continue;
                }
                Card cardAtJ = cards.get(j);
                String cardAtJExpression = cardAtJ.getTextFields().getFieldText("Expression");
                sb.append(cardAtJExpression);
                sb.append("\n");
                currentCard.setTextFields(contextAfterField, sb.toString());

            }
        }
    }

    public void addAfterContext(List<Card> cards, String[] contextAfterFields, int numberOfLines) {
        if (contextAfterFields.length != numberOfLines) {
            throw new IllegalArgumentException("Number of context fields must be equal to the number of lines");
        }
        for (int i = 0; i < cards.size(); i++) {

            Card currentCard = cards.get(i);
            int i1 = 0; //for indexing through context fields
            for (int j = i + 1; j <= i + numberOfLines; j++) {

                if (j >= cards.size()) {
                    continue;
                }
                Card cardAtJ = cards.get(j);
                String cardAtJExpression = cardAtJ.getTextFields().getFieldText("Expression");
                StringBuilder sb = new StringBuilder();
                sb.append(cardAtJExpression);
                currentCard.setTextFields(contextAfterFields[i1], sb.toString());
                i1++;
            }
        }
    }
}
