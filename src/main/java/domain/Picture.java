package domain;
import settings.CardDefaultSettings;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public final class Picture {

    private String url;
    private String filename;
    private List<String> fields;

    public Picture(String url, String fieldName) {
        this.url = url;
        this.filename = getImageFilename(this.url);
        this.fields = new ArrayList<>();
        this.fields.add(fieldName);
    }

    public Picture(String url, String fileName, String fieldName) {
        this(url,fieldName);
        this.filename = filename;
    }

    public Picture(String url) {
        this(url, CardDefaultSettings.pictureField);
    }

    public void addAdditionalField(String field) {
        this.fields.add(field);
    }

    public String getUrl() {
        return url;
    }

    public String getFilename() {
        return filename;
    }

    public List<String> getFields() {
        return fields;
    }

    public Map<String,Object> toMap() {
        Map<String,Object> asMap = new LinkedHashMap<>();
        asMap.put("url",this.url);
        asMap.put("filename",this.filename);
        asMap.put("fields",this.fields);
        return asMap;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageFilename(String url) {
        String[] splitUrl = url.split("/");
        String filename = splitUrl[splitUrl.length - 1]; //filename should be the very last thing in an url...

        //in case file name contains extension but doesn't end with it...
        String[] imgExtensions = {"jpg", "png"};

        for (String extensionName : imgExtensions) {
            if (!filename.endsWith(extensionName) && filename.contains(extensionName)) {
                filename = filename.split(extensionName)[0] + extensionName;
            }
        }

        return filename;
    }

    public String toString(){
        return "filename:" + filename + ",\nURL: " + this.url;
    }

}
