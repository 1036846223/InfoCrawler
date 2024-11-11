package zero.info.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import zero.info.db.entity.ContentInfoPO;
import zero.info.db.mapper.ContentInfoMapper;
import zero.info.dto.ContentInfoBO;
import zero.info.enu.ContentTypeEnum;
import zero.info.enu.OperateTypeEnum;
import zero.info.enu.StatusEnum;
import zero.info.enu.WebSiteParseTypeEnum;
import zero.info.handler.WebSiteParseChoose;
import zero.post.dto.ArticleDTO;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//AI内容 周期性manager
@Service
@Slf4j
public class AIContentTimeManager {

    @Resource
    private ContentInfoManager contentInfoManager;
    @Resource
    private WebSiteParseChoose webSiteParseChoose;

    //todo
    public Pair<Boolean, String> dataCollection() {
        try {
            //从指定的网站取出全部的url存下来
            //todo 只有一个,先写死
            WebSiteParseTypeEnum parseTypeEnum = WebSiteParseTypeEnum.AI_BASE_COM;
            List<ArticleDTO> articleDTOList = webSiteParseChoose.defaultSearch(parseTypeEnum);
            if (CollectionUtils.isEmpty(articleDTOList)) {
                return Pair.of(false, "articleDTOList is empty");
            }
            //先单个插入
            for (ArticleDTO articleDTO : articleDTOList) {
                ContentInfoBO contentInfoBO = new ContentInfoBO();
                contentInfoBO.setUrl(articleDTO.getUrl());
                contentInfoBO.setContentType(ContentTypeEnum.convertWebSiteParseTypeEnum(parseTypeEnum).name());
                Pair<Boolean, String> dbTempPair = contentInfoManager.addOrUpdateContentInfo(contentInfoBO);
                if (BooleanUtils.isTrue(dbTempPair.getLeft())) {
                    log.info("insertDBSuccess,dataCollection");
                } else {
                    log.error("insertDBError,dataCollection,contentInfoBO={},dbTempPair={}", JSON.toJSONString(contentInfoBO), JSON.toJSONString(dbTempPair));
                }
            }

            return Pair.of(true, "");
        } catch (Exception e) {
            log.error("dataCollection_error", e);
            return Pair.of(false, e.getMessage());
        }

    }

    public Pair<Boolean, String> summarizeAndCleanData() {
        try {
            //从指定的网站取出全部的url存下来

            return Pair.of(true, "");
        } catch (Exception e) {
            log.error("summarizeAndCleanData_error", e);
            return Pair.of(false, e.getMessage());
        }

    }

    public List<ArticleDTO> webSiteParse(WebSiteParseTypeEnum webSiteParseTypeEnum) {
        try {
//            List<ArticleDTO> dtoList = webSiteParseChoose.defaultSearch(webSiteParseTypeEnum);
//
//            return dtoList;
        } catch (Exception e) {
            log.error("webSiteParse_error,webSiteParseTypeEnum={}", JSON.toJSONString(webSiteParseTypeEnum), e);
        }
        return new ArrayList<>();
    }


}
