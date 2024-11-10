package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleDTO implements Serializable {
    private String time;
    private String title;
    private String introduction;
    private String url;

    public ArticleDTO(String time, String title, String introduction) {
        this.time = time;
        this.title = title;
        this.introduction = introduction;
    }

    public ArticleDTO(String time, String title, String introduction, String url) {
        this.time = time;
        this.title = title;
        this.introduction = introduction;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
