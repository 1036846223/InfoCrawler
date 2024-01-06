package zero.transform.openai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import zero.transform.openai.model.enu.RoelEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    /**
     * 目前支持三种角色参考官网，进行情景输入：https://platform.openai.com/docs/guides/chat/introduction
     */
    private String role;
    private String content;
    private String name;

    @JsonProperty("function_call")
    private FunctionCallResult functionCall;

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static Message of(String content) {

        return new Message(RoelEnum.USER.getName(), content);
    }

    public static Message ofSystem(String content) {

        return new Message(RoelEnum.SYSTEM.getName(), content);
    }

    public static Message ofAssistant(String content) {

        return new Message(RoelEnum.ASSISTANT.getName(), content);
    }

    public static Message ofFunction(String function) {

        return new Message(RoelEnum.FUNCTION.getName(), function);
    }


}
