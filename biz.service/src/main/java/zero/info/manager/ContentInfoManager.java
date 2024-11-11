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
import java.util.*;

@Service
@Slf4j
public class ContentInfoManager {

    @Resource
    private ContentInfoMapper contentInfoMapper;


    //todo
    public Pair<Boolean, String> addOrUpdateContentInfo(ContentInfoBO infoBO) {
        try {

            if (infoBO == null) {
                return Pair.of(false, "参数为空");
            }

            Pair<Boolean, List<ContentInfoPO>> dbPair = queryContentInfoByIdOrHashId(infoBO);
            if (dbPair == null || BooleanUtils.isNotTrue(dbPair.getLeft())) {
                log.error("queryDbError,addOrUpdateContentInfo,infoBO={}", JSON.toJSONString(infoBO));
                return Pair.of(false, "查询数据库失败");
            }

            if (Objects.equals(infoBO.getOperateType(), OperateTypeEnum.ADD.getCode())) {

                //账户名不允许重复
                if (CollectionUtils.isNotEmpty(dbPair.getRight())) {
                    return Pair.of(false, "账户名已存在");
                }
                ContentInfoPO infoPO = ofContentInfoPO(infoBO);
                if (StringUtils.isEmpty(infoPO.getHashId())) {
                    infoPO.setHashId(generateHashIdBySHA(infoBO.getUrl()));
                }
                infoPO.setStatus(StatusEnum.VALID.getCode());
                Date time = new Date();
                infoPO.setAddTime(time);
                infoPO.setUpdateTime(time);

                int num = contentInfoMapper.insert(infoPO);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(infoPO.getId()));
                } else {
                    return Pair.of(false, "添加失败");
                }
            } else if (Objects.equals(infoBO.getOperateType(), OperateTypeEnum.UPDATE.getCode())) {
                if (CollectionUtils.isEmpty(dbPair.getRight())) {
                    return Pair.of(false, "数据不存在");
                }
                long id = dbPair.getRight().get(0).getId();
                ContentInfoPO infoPO = new ContentInfoPO();
                infoPO.setId(id);
                int num = contentInfoMapper.updateById(infoPO);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(id));
                } else {
                    return Pair.of(false, "修改失败");
                }
            } else if (Objects.equals(infoBO.getOperateType(), OperateTypeEnum.DEL.getCode())) {
                if (CollectionUtils.isEmpty(dbPair.getRight())) {
                    return Pair.of(false, "数据不存在");
                }
                long id = dbPair.getRight().get(0).getId();
                ContentInfoPO infoPO = ofContentInfoPO(infoBO);
                infoPO.setId(id);
                infoPO.setStatus(StatusEnum.UN_VALID.getCode());
                //只更新必要的字段
                int num = contentInfoMapper.updateById(infoPO);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(id));
                } else {
                    return Pair.of(false, "删除失败");
                }
            }

            return Pair.of(true, "");
        } catch (Exception e) {
            log.error("addOrUpdateContentInfo_error,infoBO={}", JSON.toJSONString(infoBO), e);
            return Pair.of(false, e.getMessage());
        }
    }

    public Pair<Boolean, List<ContentInfoPO>> queryContentInfoByIdOrHashId(ContentInfoBO infoBO) {
        try {

            if (infoBO == null) {
                return Pair.of(false, null);
            }

            //优先通过id取
            if (infoBO.getId() != null && infoBO.getId() > 0) {
                Long id = infoBO.getId();
                QueryWrapper<ContentInfoPO> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id", id);
                queryWrapper.eq("status", StatusEnum.VALID.getCode());
                queryWrapper.orderByDesc("id");
                List<ContentInfoPO> dbInfoList = contentInfoMapper.selectList(queryWrapper);
                return Pair.of(true, dbInfoList);
            }

            //通过url和hashId取
            if (StringUtils.isEmpty(infoBO.getHashId()) && StringUtils.isEmpty(infoBO.getUrl())) {
                return Pair.of(false, null);
            }
            String hashId = infoBO.getHashId();
            if (StringUtils.isEmpty(hashId)) {
                hashId = generateHashIdBySHA(infoBO.getUrl());
            }
            if (StringUtils.isEmpty(hashId)) {
                return Pair.of(false, null);
            }

            QueryWrapper<ContentInfoPO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("hashId", hashId);
            queryWrapper.eq("status", StatusEnum.VALID.getCode());
            queryWrapper.orderByDesc("id");
            List<ContentInfoPO> dbInfoList = contentInfoMapper.selectList(queryWrapper);

            return Pair.of(true, dbInfoList);
        } catch (Exception e) {
            log.error("queryContentInfoByIdOrHashId_error,infoDTO={}", JSON.toJSONString(infoBO), e);
        }
        return Pair.of(false, null);
    }


    private ContentInfoPO ofContentInfoPO(ContentInfoBO infoBO) {
        ContentInfoPO infoPO = new ContentInfoPO();
        if (StringUtils.isNotEmpty(infoBO.getContent())) {
            infoPO.setContent(infoBO.getContent());
        }
        if (StringUtils.isNotEmpty(infoBO.getContentType())) {
            infoPO.setContentType(infoBO.getContentType());
        }
        if (StringUtils.isNotEmpty(infoBO.getAiType())) {
            infoPO.setAiType(infoBO.getAiType());
        }
        if (StringUtils.isNotEmpty(infoBO.getHashId())) {
            infoPO.setHashId(infoBO.getHashId());
        }
        if (StringUtils.isNotEmpty(infoBO.getUrl())) {
            infoPO.setUrl(infoBO.getUrl());
        }
        if (infoBO.getId() == null) {
            infoPO.setId(infoBO.getId());
        }
        return infoPO;
    }

    //生成hashid
    public static String generateHashIdBySHA(String input) {
        try {
            int maxLength = 10;
            // 创建 SHA-256 的 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 计算输入字符串的哈希值
            byte[] hashBytes = digest.digest(input.getBytes());
            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
                if (hexString.length() > maxLength) {
                    break;
                }
            }
            // 截取前10个字符
            return hexString.toString().substring(0, maxLength);
        } catch (NoSuchAlgorithmException e) {
            log.error("generateHashIdBySHA_error,input={}", input, e);
        }
        return null;
    }


}
