package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDTO implements Serializable {
    private Long id;
    private String content;
    private String nickName;
    private Long sendTime;

}
