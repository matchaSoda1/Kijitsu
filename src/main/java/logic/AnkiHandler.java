package logic;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import domain.Card;

public class AnkiHandler {

    /*
        Sample usage:

        Map<String,Object> addNote = new LinkedHashMap<>();

        action.put("action","addNote");
        action.put("version",6);
        action.put("params",paramsMap);

        doAction(addNote);
*/

    public String doAction(Map<String, Object> ActionJSON) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        URL url = new URL("http://127.0.0.1:8765"); //ankiconnect http magic!
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json"); //Set the “Accept” request header to “application/json” to read the response in the desired format:
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            byte[] input = mapper.writeValueAsBytes(ActionJSON);
            outputStream.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            Object responseObject = mapper.readValue(response.toString(), Object.class);
            String jsonResponse = mapper.writeValueAsString(responseObject);

            System.out.println("error: " + mapper.readTree(jsonResponse).get("error"));
            return jsonResponse;
        }
    }

    public void addNote(Card card) throws IOException{
        Map<String,Object> actionParameters = new LinkedHashMap<>();
        actionParameters.put("note",card);

        Map<String,Object> addNote = getActionMap();
        addNote.put("action","addNote");
        addNote.put("params",actionParameters);

        doAction(addNote);
    }

    public void updateNoteFields(String cardId){
//        Map<String,Object> actionParameters = new LinkedHashMap<>();
//        actionParameters.put("note",card);
//
//        Map<String,Object> addNote = getActionMap();
//        addNote.put("action","addNote");
//        addNote.put("params",actionParameters);
//
//        doAction(addNote);
    }

    public int getCurrentCardId() throws IOException {
        String resultJSON = this.getCurrentCard();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode parent = mapper.readTree(resultJSON);
        return parent.get("result").get("cardId").asInt();
    }

    public void addNotes(List<Card> cardList) throws IOException{
        Map<String,Object> actionParameters = new LinkedHashMap<>();
        actionParameters.put("notes",cardList);

        Map<String,Object> addNotes = getActionMap();
        addNotes.put("action","addNotes");
        addNotes.put("params",actionParameters);

        doAction(addNotes);
    }

    public void getDeckNames() throws IOException{
        Map<String,Object> deckNames = getActionMap();
        deckNames.put("action","deckNames");

        doAction(deckNames);
    }

    public String getCurrentCard() throws IOException {
        Map<String,Object> getCurrentCard = getActionMap();

        getCurrentCard.put("action","guiCurrentCard");

        return doAction(getCurrentCard);

    }

    public void lookUpExpression(String focusMorph){
        //look up a word in deck, return array of expression fields

    }

    public Map<String,Object> getActionMap(){
        Map<String,Object> action = new LinkedHashMap<>();

        action.put("action","");
        action.put("version",6);

        return action;
    }



}
