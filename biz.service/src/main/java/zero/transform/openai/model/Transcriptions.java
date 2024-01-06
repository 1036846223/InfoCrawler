package zero.transform.openai.model;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;

public class Transcriptions extends HashMap<String, RequestBody> {

    public Transcriptions(String model, String prompt){
        this.put("model",RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), model));
        this.put("prompt",RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), prompt));
    }

    public static Transcriptions of(String model, String prompt) {
        return new Transcriptions(model,prompt);
    }
}
