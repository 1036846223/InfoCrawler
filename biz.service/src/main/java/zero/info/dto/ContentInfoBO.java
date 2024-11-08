package zero.info.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zero.info.enu.*;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
public class ContentInfoBO implements Serializable {


    private Long id;
    /**
     * @see OperateTypeEnum
     */
    private Integer operateType;

    //'内容类型'
    private String contentType;
    //'原始内容URL'
    private String url;

    //'hash字符-唯一标识'
    private String hashId;
    //'AI工具类型'
    /**
     * @see ContentTypeEnum
     */
    private String aiType;

    //'AI 转换后文字'
    private String content;
    /**
     * 是否有效[0不可用，1可用]
     *
     * @see StatusEnum
     */
    private Integer status;

}
