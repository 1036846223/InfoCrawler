package zero.info.request;

import lombok.Data;
import web.enu.InfoSearchOperateTypeEnum;

import java.io.Serializable;
import java.util.List;

@Data
public class InfoSearchRequest implements Serializable {
    private List<String> urlList;
    /**
     * @see InfoSearchOperateTypeEnum
     */
    private Integer operateType = InfoSearchOperateTypeEnum.SEARCH.getCode();

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }
}
