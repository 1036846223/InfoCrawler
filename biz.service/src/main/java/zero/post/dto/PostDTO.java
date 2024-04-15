package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDTO implements Serializable {
    private Long id;
    private ContentDTO content;
    private String url;
    private String name;
    private Long sendTime;

}
