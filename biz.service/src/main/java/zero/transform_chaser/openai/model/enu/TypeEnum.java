package zero.transform_chaser.openai.model.enu;

import java.io.Serializable;

public enum TypeEnum implements Serializable {

    JSON_OBJECT("json_object"),
    TEXT("text");
    ;

    private String name;

    TypeEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
