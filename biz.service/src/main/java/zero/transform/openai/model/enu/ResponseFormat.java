package zero.transform.openai.model.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseFormat {
    URL("url"),
    B64_JSON("b64_json"),
    ;
    private String value;
}
