import domain.Card;
import logic.AnkiHandler;
import logic.CardCreator;

import java.io.*;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException {

        String NHKURL =
                "https://www3.nhk.or.jp/news/html/20220322/k10013546211000.html?utm_int=news-new_contents_latest_006";

        AnkiHandler anki = new AnkiHandler();
        List<Card> cards = new CardCreator(NHKURL).articleToCards();

        anki.addNotes(cards);

    }





}








