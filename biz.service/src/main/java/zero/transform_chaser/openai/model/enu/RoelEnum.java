package zero.transform_chaser.openai.model.enu;

import java.io.Serializable;

public enum RoelEnum implements Serializable {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),

    FUNCTION("function"),
    ;

    private String name;

    RoelEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
