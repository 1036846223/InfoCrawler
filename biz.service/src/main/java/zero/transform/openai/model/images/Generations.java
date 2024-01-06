package zero.transform.openai.model.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zero.transform.openai.model.enu.ResponseFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Generations {
    private String prompt;
    private int n;
    private String size;
    private String response_format;

    public static Generations ofURL(String prompt,int n,String size) {
        return new Generations(prompt,n,size, ResponseFormat.URL.getValue());
    }

    public static Generations ofB64_JSON(String prompt,int n,String size) {
        return new Generations(prompt,n,size, ResponseFormat.B64_JSON.getValue());
    }
}
