package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContentDTO implements Serializable {
    private String text;
    private String pic;

}
