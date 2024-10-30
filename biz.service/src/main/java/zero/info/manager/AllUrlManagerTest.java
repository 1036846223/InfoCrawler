package zero.info.manager;

import zero.info.dto.UrlContentDTO;
import zero.info.request.InfoSearchRequest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class AllUrlManagerTest {


    public static void main(String[] args) {
        InfoSearchManagerMeta infoSearchManagerMeta= new InfoSearchManagerMeta();
        InfoSearchRequest request = new InfoSearchRequest();
        List<String> urlList = new ArrayList<>();
//        String url1 = "https://www.aibase.com/zh";
        String url1 = "https://www.aibase.com/zh/news";
        urlList.add(url1);
        request.setUrlList(urlList);
        List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearch(request);
        System.out.println(urlContentDTOS);
    }


}
