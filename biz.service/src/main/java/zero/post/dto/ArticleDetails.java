package zero.post.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleDetails implements Serializable {
    private String time;
    private String title;
    private String textContent;
    private String url;
    //热度
    private String heat;


    public ArticleDetails(String time, String title, String textContent, String url, String heat) {
        this.time = time;
        this.title = title;
        this.textContent = textContent;
        this.url = url;
        this.heat = heat;
    }

    public ArticleDetails() {

    }


}
