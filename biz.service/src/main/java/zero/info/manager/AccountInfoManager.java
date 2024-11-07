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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountInfoManager {

    @Resource
    private ContentInfoMapper contentInfoMapper;


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


    public PageResponse<SearchAccountRoleDTO> roleList(@RequestBody BatchSearchRoleRequest request) {
        try {

            String account = request.getAccount();
            //手机号
            String phone = request.getPhone();

            //todo 暂不支持roleType+其他的查询能力 -- 多表联查
            //暂只支持以account或phone 为主的查询
            Integer roleType = request.getRoleType();
            //从0开始
            Long pageNo = request.getPageNo();
            Long pageSize = request.getPageSize();

            QueryWrapper<AccountInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status", StatusEnum.VALID.getCode());
            if (StringUtils.isNotEmpty(account)) {
                queryWrapper.like("account", "%s" + account + "%s");
            }
            if (StringUtils.isNotEmpty(phone)) {
                queryWrapper.like("phone", "%s" + phone + "%s");
            }

            // 使用 count 方法获取满足条件的记录总数
            int count = accountInfoMapper.selectCount(queryWrapper);

            if (count == 0) {
                return PageResponse.success();
            }

            queryWrapper.orderByDesc("id");

            Page<AccountInfo> page = new Page<>(pageNo, pageSize);
            //先查账号信息
            Page<AccountInfo> dbInfoPage = (Page<AccountInfo>) accountInfoMapper.selectPage(page, queryWrapper);
            if (dbInfoPage == null || CollectionUtils.isEmpty(dbInfoPage.getRecords())) {
                return PageResponse.success();
            }

//            再查关联关系
            List<Long> accountIdList = dbInfoPage.getRecords().stream().map(AccountInfo::getId).collect(Collectors.toList());
            QueryWrapper<AccountRoleRel> roleRelQueryWrapper = new QueryWrapper<AccountRoleRel>().in("account_id", accountIdList);
            if (roleType != null) {
                roleRelQueryWrapper.eq("role_type", roleType);
            }
            roleRelQueryWrapper.eq("status", StatusEnum.VALID.getCode());

            List<AccountRoleRel> roleRelList = accountRoleRelMapper.selectList(roleRelQueryWrapper);
            Map<Long, List<AccountRoleRel>> roleRelMap = new HashMap<>();
            roleRelList.stream().forEach(e -> {
                if (roleRelMap.containsKey(e.getAccountId())) {
                    roleRelMap.get(e.getAccountId()).add(e);
                } else {
                    List<AccountRoleRel> list = new ArrayList<>();
                    list.add(e);
                    roleRelMap.put(e.getAccountId(), list);
                }
            });

            List<SearchAccountRoleDTO> roleDTOList = dbInfoPage.getRecords().stream().map(e -> {
                        return buildSearchAccountRoleDTO(e, roleRelMap.get(e.getId()));
                    }).filter(Objects::nonNull)
                    .sorted(Comparator.comparing(SearchAccountRoleDTO::getAccountId).reversed())
                    .collect(Collectors.toList());
            PageResponse<SearchAccountRoleDTO> pageResponse = PageResponse.success(roleDTOList);
            pageResponse.setTotal((long) count);
            pageResponse.setPageNo(pageNo);
            pageResponse.setPageSize(pageSize);

            log.info("roleList_res,req={},res={}", JSON.toJSONString(request), JSON.toJSONString(pageResponse));
            return pageResponse;
        } catch (Exception e) {
            log.error("roleList_error,request={}", JSON.toJSONString(request), e);
        }
        return PageResponse.fail();
    }


    public SearchAccountRoleDTO buildSearchAccountRoleDTO(AccountInfo accountInfo, List<AccountRoleRel> roleRelList) {
        try {
            if (accountInfo == null) {
                return null;
            }
            SearchAccountRoleDTO roleDTO = new SearchAccountRoleDTO();
            roleDTO.setAccount(accountInfo.getAccount());
            roleDTO.setPhone(accountInfo.getPhone());
            roleDTO.setAccountId(accountInfo.getId());
            roleDTO.setAddTime(accountInfo.getAddTime());
            roleDTO.setUpdateTime(accountInfo.getUpdateTime());
            if (CollectionUtils.isNotEmpty(roleRelList)) {
                roleDTO.setRoleTypeList(roleRelList.stream().map(AccountRoleRel::getRoleType).collect(Collectors.toList()));
            }
            return roleDTO;
        } catch (Exception e) {
            log.error("buildSearchAccountRoleDTO_error,accountInfo={},roleRelList={}",
                    JSON.toJSONString(accountInfo), JSON.toJSONString(roleRelList), e);
        }
        return null;
    }


}
