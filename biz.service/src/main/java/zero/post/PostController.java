package zero.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import zero.info.enu.HttpResponseCodeEnum;
import zero.info.manager.InfoSearchManagerSyn;
import zero.info.response.HttpResponse;
import zero.post.dto.ContentDTO;
import zero.post.dto.PostDTO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/post")
@Slf4j
@RestController
public class PostController {
    @Resource
    private InfoSearchManagerSyn infoSearchManagerSyn;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public HttpResponse<List<PostDTO>> postUrl() {
        try {
            List<PostDTO> postDTOS = new ArrayList<>();
            PostDTO postDTO = new PostDTO();
            ContentDTO contentDTO = new ContentDTO();
            contentDTO.setText("谁有形势与政策这本书呀？");
            postDTO.setContent(contentDTO);
            postDTO.setId(7L);
            postDTO.setName("dylan");
            postDTO.setSendTime(new Date().getTime());
            postDTOS.add(postDTO);
            return HttpResponse.success(postDTOS);
        } catch (Exception e) {
            log.error("infoSingleSearch_error", e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }

}
