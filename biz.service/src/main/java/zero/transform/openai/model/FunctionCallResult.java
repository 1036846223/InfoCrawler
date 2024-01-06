package zero.transform.openai.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionCallResult {

    String name;

    String arguments;
}
