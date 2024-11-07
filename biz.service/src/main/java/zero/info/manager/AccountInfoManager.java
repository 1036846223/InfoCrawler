package zero.info.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import trade.info.biz.db.entity.AccountInfo;
import trade.info.biz.db.entity.AccountRoleRel;
import trade.info.biz.db.mapper.AccountInfoMapper;
import trade.info.biz.db.mapper.AccountRoleRelMapper;
import trade.info.biz.dto.AccountInfoDTO;
import trade.info.biz.dto.SearchAccountRoleDTO;
import trade.info.biz.enu.OperateTypeEnum;
import trade.info.biz.enu.StatusEnum;
import trade.info.biz.requset.BatchSearchRoleRequest;
import trade.info.biz.response.PageResponse;
import zero.info.db.entity.ContentInfoPO;
import zero.info.db.mapper.ContentInfoMapper;
import zero.info.dto.ContentInfoBO;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountInfoManager {

    @Resource
    private ContentInfoMapper contentInfoMapper;


    //todo
    public Pair<Boolean, String> addOrUpdateAccountInfo(ContentInfoBO infoBO) {
        try {

            String hashId = infoBO.getHashId();
            if (StringUtils.isEmpty(hashId)) {

            }
            QueryWrapper<ContentInfoPO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("hashId", infoDTO.getAccount());
            queryWrapper.eq("status", StatusEnum.VALID.getCode());
            queryWrapper.orderByDesc("id");
            List<AccountInfo> dbInfoList = accountInfoMapper.selectList(queryWrapper);

            AccountInfo accountInfo = ofAccountInfo(infoDTO);

            if (infoDTO.getOperateType() == OperateTypeEnum.ADD.getCode()) {

                //账户名不允许重复
                if (CollectionUtils.isNotEmpty(dbInfoList)) {
                    return Pair.of(false, "账户名已存在");
                }
                accountInfo.setStatus(StatusEnum.VALID.getCode());
                Date time = new Date();
                accountInfo.setAddTime(time);
                accountInfo.setUpdateTime(time);

                int num = accountInfoMapper.insert(accountInfo);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(accountInfo.getId()));
                } else {
                    return Pair.of(false, "添加失败");
                }
            } else if (infoDTO.getOperateType() == OperateTypeEnum.UPDATE.getCode()) {
                if (CollectionUtils.isEmpty(dbInfoList)) {
                    return Pair.of(false, "账户不存在");
                }
                AccountInfo dbInfo = dbInfoList.get(0);
                long id = dbInfo.getId();
                accountInfo.setId(id);
                int num = accountInfoMapper.updateById(accountInfo);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(id));
                } else {
                    return Pair.of(false, "修改失败");
                }
            } else if (infoDTO.getOperateType() == OperateTypeEnum.DEL.getCode()) {
                if (CollectionUtils.isEmpty(dbInfoList)) {
                    return Pair.of(false, "账户不存在");
                }
                AccountInfo dbInfo = dbInfoList.get(0);
                long id = dbInfo.getId();
                accountInfo.setId(id);
                accountInfo.setStatus(StatusEnum.UN_VALID.getCode());
                int num = accountInfoMapper.updateById(accountInfo);
                if (num == 1) {
                    return Pair.of(true, String.valueOf(id));
                } else {
                    return Pair.of(false, "删除失败");
                }
            }

            return Pair.of(true, "");
        } catch (Exception e) {
            log.error("addOrUpdateAccountInfo_error,infoDTO={}", JSON.toJSONString(infoDTO), e);
            return Pair.of(false, e.getMessage());
        }
    }

    private AccountInfo ofAccountInfo(AccountInfoDTO infoDTO) {
        AccountInfo accountInfo = new AccountInfo();
        if (StringUtils.isNotEmpty(infoDTO.getAccount())) {
            accountInfo.setAccount(infoDTO.getAccount());
        }
        if (StringUtils.isNotEmpty(infoDTO.getPhone())) {
            accountInfo.setPhone(infoDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(infoDTO.getPassword())) {
            accountInfo.setPassword(infoDTO.getPassword());
        }
        return accountInfo;
    }

    //生成hashid
    public static String generateUniqueIdentifier(String input) {
        try {
            // 创建 SHA-256 的 MessageDigest 实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 计算输入字符串的哈希值
            byte[] hashBytes = digest.digest(input.getBytes());
            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // 截取前10个字符
            return hexString.toString().substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
