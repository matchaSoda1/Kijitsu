package domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import settings.CardDefaultSettings;

import static settings.CardDefaultSettings.*;

import java.util.*;

@JsonPropertyOrder({ "deckName", "modelName", "fields", "tags", "picture"})

public class Card {

    private String deckName;
    private String noteType;
    private List<String> tags;
    private Fields textFields;
    private List<Picture> pictures;

    public Card(String deckName, String noteType) {
        this.deckName = deckName;
        this.noteType = noteType;
        this.textFields = new Fields();
        this.pictures = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.tags.addAll(Arrays.asList(CardDefaultSettings.tags));
    }
    public Card() {
        this("","");
    }

    public Card(String field1Text) {
        this(CardDefaultSettings.deckName, CardDefaultSettings.noteType);
        this.textFields.put(targetField,field1Text);
    }
    public Card(String field1Text, Picture picture) {
        this(field1Text);
        this.pictures.add(picture);
    }

    public Card(String deckName, String noteType, String field1Name, String field1Text) {
        this(deckName,noteType);
        this.textFields.put(field1Name,field1Text);
    }
    public Card(String deckName, String noteType, String field1Name, String field1Text, Picture picture) {
        this(deckName,noteType,field1Name,field1Text);
        this.pictures.add(picture);
    }

    public String getDeckName() {
        return deckName;
    }
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    @JsonProperty("modelName") //Jackson object mapper grabs names from getters. This annotation makes it so that 'modelName' appears in JSON request rather than 'NoteType'
    public String getNoteType() {
        return noteType;
    }
    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<String> getTags(){
        return this.tags;
    }
    public void setTags(String... tags) {
        for (String tag: tags) {
            this.tags.add(tag);
        }
    }

    @JsonIgnore //Jackson object mapper gets any getters. this annotation makes it so that it doesn't grab it in the JSON string
    public Fields getTextFields() {
        return textFields;
    }
    public void setTextFields(String fieldName, String fieldText) {
        this.textFields.put(fieldName,fieldText);
    }
    public void setTextFields(Fields textFields) {
        this.textFields = textFields;
    }
    @JsonProperty("fields") //Makes it so that the Json property named 'fields' is this one!
    public Map<String,String> getTextFieldsMap() {
        return textFields.getFields();
    }

    @JsonIgnore
    public void showTextAndPictures(){
        System.out.println(getTextFields().toString());
        System.out.println(getPicture());
    }
    @JsonProperty("picture")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Picture> getPicture() {
        return pictures;
    }
    public void setPicture(Picture picture) {
        if (!this.pictures.isEmpty()) {
            pictures.clear();
        }
        this.pictures.add(picture);
    }
    public String toString(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

















}
