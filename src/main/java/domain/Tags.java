package domain;

import java.util.ArrayList;
import java.util.List;

public class Tags {
    List<String> tags;

    public Tags(String... tags){
        this.tags = new ArrayList<>();
        for (String tag: tags) {
            this.tags.add(tag);
        }
    }

    public Tags(){
        this("");
    }

    public List<String> getTags(){
        return this.tags;
    }
}
