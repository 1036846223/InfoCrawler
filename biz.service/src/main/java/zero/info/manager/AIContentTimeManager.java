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
import zero.info.enu.OperateTypeEnum;
import zero.info.enu.StatusEnum;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//AI内容 周期性manager
@Service
@Slf4j
public class AIContentTimeManager {

    @Resource
    private ContentInfoManager contentInfoManager;


    //todo
    public Pair<Boolean, String> dataCollection() {
        try {
            //从指定的网站取出全部的url存下来


            return Pair.of(true, "");
        } catch (Exception e) {
            log.error("addOrUpdateContentInfo_error", e);
            return Pair.of(false, e.getMessage());
        }
    }


}
