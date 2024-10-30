package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {
    private String time;
    private String title;
    private String introduction;

    public Article(String time, String title, String introduction) {
        this.time = time;
        this.title = title;
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Article{" +
                "time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
