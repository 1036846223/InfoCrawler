package zero.info.db.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import zero.info.enu.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("content_info")
public class ContentInfoPO implements Serializable {


    //id 会自动回填
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //'内容类型'
    private String contentType;
    //'原始内容URL'
    private String url;
    //'AI工具类型'
    /**
     * @see ContentTypeEnum
     */
    private String aiType;
    //'hash字符-唯一标识'
    private String hashId;

    //'AI 转换后文字'
    private String content;
    /**
     * 是否有效[0不可用，1可用]
     *
     * @see StatusEnum
     */
    private Integer status;

    private Date addTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
