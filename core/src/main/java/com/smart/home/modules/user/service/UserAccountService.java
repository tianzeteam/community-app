package com.smart.home.modules.user.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.smart.home.cache.UserTokenCache;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.common.enums.AccountStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.AuthorizationException;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.*;
import com.smart.home.modules.system.entity.SysMenu;
import com.smart.home.modules.system.service.SysFileService;
import com.smart.home.modules.system.service.SysMenuService;
import com.smart.home.modules.system.service.SysRoleService;
import com.smart.home.modules.user.dao.UserAccountMapper;
import com.smart.home.modules.user.dao.UserRoleMappingMapper;
import com.smart.home.modules.user.entity.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Log4j2
@Service
public class UserAccountService {

    @Resource
    private UserAccountMapper mapper;
    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private UserCommunityAuthService userCommunityAuthService;
    @Autowired
    private SysFileService sysFileService;

    @Transactional(rollbackFor = Exception.class)
    public int insert(UserAccount entity, List<Integer> roleIdList) throws ServiceException {
        if (StringUtils.isBlank(entity.getUsername())) {
            // 当用户名为空的时候，把手机号码赋值给用户名
            Asserts.notBlank(entity.getMobile(), "mobile");
            entity.setUsername(entity.getMobile());
        }
        UserAccountExample example = new UserAccountExample();
        example.createCriteria().andUsernameEqualTo(entity.getUsername());
        if (mapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该用户已经存在");
        }
        // 生成密码盐，防止暴力破解密码
        String salt = UUIDUtil.uuid();
        String password = encryptPassword(entity.getPassword(), salt);
        entity.setState(AccountStatusEnum.NORMAL.getStatus());
        entity.setSalt(salt);
        entity.setRevision(0);
        entity.setPassword(password);
        entity.setCreatedTime(new Date());
        int affectRow = mapper.insertSelective(entity);
        if (affectRow > 0 && !CollectionUtils.isEmpty(roleIdList)) {
            assignRoles(entity.getId(), roleIdList);
        }
        return affectRow;
    }

    /**
     * 根据原始密码和密码盐加密密码
     * @param password 原始密码
     * @param salt 密码盐
     * @return
     */
    public String encryptPassword(String password, String salt) {
        return SummaryUtils.md5(password+salt);
    }

    public int update(UserAccount entity) {
        // 不允许修改的设置成null，防止前端不遵守规范没传null
        entity.setUsername(null);
        entity.setMobile(null);
        entity.setPassword(null);
        entity.setSalt(null);
        entity.setAccessToken(null);
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            mapper.deleteByPrimaryKey(id);
        }
        return 1;
    }

    public List<UserAccount> selectByPage(UserAccount entity, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserAccountExample example = new UserAccountExample();
        UserAccountExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(entity.getName())) {
            criteria.andNameLike(entity.getName());
        }
        if (StringUtils.isNotBlank(entity.getNickName())) {
            criteria.andNickNameLike(entity.getNickName());
        }
        if (StringUtils.isNotBlank(entity.getUsername())) {
            criteria.andUsernameEqualTo(entity.getUsername());
        }
        if (StringUtils.isNotBlank(entity.getMobile())) {
            criteria.andMobileEqualTo(entity.getMobile());
        }
        return mapper.selectByExample(example);
    }

    /**
     * 角色授权
     * @param userId
     * @param roleIdList
     */
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Integer> roleIdList) {
        // 先删除全部，再重新授权
        userRoleMappingMapper.deleteByUserId(userId);
        for (Integer roleId : roleIdList) {
            String roleCode = sysRoleService.findRoleCodeById(roleId);
            UserRoleMapping userRoleMapping = new UserRoleMapping();
            userRoleMapping.withUserId(userId).withRoleId(roleId).withRoleCode(roleCode);
            userRoleMappingMapper.insert(userRoleMapping);
        }
    }

    private String assignDefaultRole(long userId, String roleCode) {
        Integer roleId = sysRoleService.findRoleIdByCode(roleCode);
        UserRoleMapping userRoleMapping = new UserRoleMapping();
        userRoleMapping.withUserId(userId).withRoleId(roleId).withRoleCode(roleCode);
        userRoleMappingMapper.insert(userRoleMapping);
        return initUserPermits(userId);
    }

    public List<UserRoleMapping> findUserRoles(Long userId) {
        UserRoleMappingExample example = new UserRoleMappingExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserRoleMapping> list = userRoleMappingMapper.selectByExample(example);
        return list;
    }

    public List<String> findUserRoleCodeList(Long userId) {
        return userRoleMappingMapper.findUserRoleCodeList(userId);
    }

    public UserAccount findUserByUsername(String username) {
        UserAccountExample example = new UserAccountExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UserAccount> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public UserAccount findUserByMobile(String mobile) {
        UserAccountExample example = new UserAccountExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<UserAccount> list = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 验证用户登陆
     * @param username
     * @param password
     * @return
     */
    public UserAccount doAuthentication(String username, String password) throws ServiceException {
        UserAccount userAccount = findUserByUsername(username);
        int state = verifyWhenLogin(userAccount);
        if (AccountStatusEnum.NORMAL.getStatus() == state) {
            password = encryptPassword(password, userAccount.getSalt());
            if (StringUtils.equals(password, userAccount.getPassword())) {
                String token = userAccount.getAccessToken();
                if (StringUtils.isBlank(token)) {
                    token = generateNewAccessToken(userAccount.getId());
                    userAccount.setAccessToken(token);
                }
                UserTokenCache.put(token, userAccount);
                userAccount.setRoleCodeList(findUserRoleCodeList(userAccount.getId()));
                loadCommunityAuth(userAccount);
                return userAccount;
            }
        }
        throw new AuthorizationException("用户名或者密码错误");
    }

    public void doLogout(Long userId) {
        UserAccount userAccount = findUserByUserId(userId);
        String token = userAccount.getAccessToken();
        UserTokenCache.remove(token);
        mapper.clearToken(userAccount.getId());
    }

    private int verifyWhenLogin(UserAccount userAccount) {
        if (Objects.isNull(userAccount)) {
            throw new AuthorizationException("用户名或者密码错误");
        }
        int state = userAccount.getState();
        if (AccountStatusEnum.LOCKED.getStatus() == state) {
            throw new AuthorizationException("账户已锁住");
        }
        if (AccountStatusEnum.PAUSED.getStatus() == state) {
            throw new AuthorizationException("账户已暂停");
        }
        return state;
    }

    /**
     * 验证码校验通过后， 拿手机号直接登陆
     * @param mobile
     * @return
     */
    public UserAccount loginViaMobile(String mobile) throws ServiceException {
        UserAccount userAccount = findUserByMobile(mobile);
        if (Objects.isNull(userAccount)) {
            // 如果不存在，则创建
            userAccount = createUserAccountWithoutPassword(mobile, RoleConsts.REGISTER);
        }
        int state = verifyWhenLogin(userAccount);
        if (AccountStatusEnum.NORMAL.getStatus() == state) {
            String token = generateNewAccessToken(userAccount.getId());
            userAccount.setAccessToken(token);
            userAccount.setRoleCodeList(findUserRoleCodeList(userAccount.getId()));
            UserTokenCache.put(token, userAccount);
            loadCommunityAuth(userAccount);
            return userAccount;
        }
        throw new AuthorizationException("用户名或者密码错误");
    }

    /**
     * 重启后重新初始化用户数据
     * @param userId 这个是从合法的token中解析出来的用户主键ID
     * @return
     */
    public UserAccount findUserByUserId(Long userId) {
        return mapper.selectByPrimaryKey(userId);
    }

    private String generateNewAccessToken(Long id) {
        String token = JwtUtil.createToken(id.toString(), String.valueOf(System.currentTimeMillis()));
        mapper.updateNewToken(id, token);
        return token;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public UserAccount createUserByWxOpenid(String openid, String mobile) {
        // TODO
        // UserTokenCache.put(userAccount.getAccessToken(), userAccount);
        return null;
    }

    private UserAccount createUserAccountWithoutPassword(String mobile, String defaultRoleCode) {
        UserAccount userAccount = new UserAccount();
        userAccount.setState(AccountStatusEnum.NORMAL.getStatus());
        userAccount.setRevision(0);
        userAccount.setUsername(mobile);
        userAccount.setMobile(mobile);
        userAccount.setNickName(mobile.substring(0, 7)+"****");
        userAccount.setCreatedTime(new Date());
        mapper.insertSelective(userAccount);
        long userId = userAccount.getId();
        String token = JwtUtil.createToken(String.valueOf(userId),String.valueOf(System.currentTimeMillis()));
        mapper.updateNewToken(userId, token);
        if (StringUtils.isNotBlank(defaultRoleCode)) {
            // 赋予默认的注册用户角色
            assignDefaultRole(userId, defaultRoleCode);
        }
        userDataService.initUserData(userId);
        return userAccount;
    }

    /**
     * 查询user_account和user_data数据基础信息
     * @param userId
     * @return
     */
    public UserAccount queryUserAccountAndData(Long userId) {
        UserAccount userAccount = this.mapper.selectByPrimaryKey(userId);
        UserData userData = userDataService.findByUserId(userId);
        if (Objects.isNull(userData)) {
            userData = userDataService.initUserData(userId);
        }
        userAccount.setUserData(userData);
        return userAccount;
    }

    public void updateHeadUrl(Long userId, String headUrl) {
        UserAccount userAccount = findUserByUserId(userId);
        String oldHeadUrl = userAccount.getHeadUrl();
        if(StringUtils.isNotBlank(oldHeadUrl) && !StringUtils.equals(oldHeadUrl, headUrl)) {
            // 删除旧头像
            sysFileService.deleteByNewName(FileUtils.getFileNameFromUrl(oldHeadUrl));
        }
        mapper.updateHeadUrl(userId, headUrl);
        sysFileService.sync(FileUtils.getFileNameFromUrl(headUrl));
    }

    public void updateNickName(Long userId, String nickName) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userId);
        userAccount.setNickName(nickName);
        userAccount.setUpdatedTime(new Date());
        userAccount.setUpdatedBy(userId);
        this.mapper.updateByPrimaryKeySelective(userAccount);
    }

    /**
     * 设置密码
     * @param userId
     * @param password
     */
    public void initPassword(Long userId, String password) throws ServiceException {
        UserAccount userAccount = findUserByUserId(userId);
        if (StringUtils.isNotBlank(userAccount.getPassword())) {
            throw new ServiceException("已经设置过密码了");
        }
        if (password.length() < 6) {
            throw new ServiceException("密码不能低于6位");
        }
        if (password.length() > 32) {
            throw new ServiceException("密码不能超过32位");
        }
        String salt = userAccount.getSalt();
        if (StringUtils.isBlank(salt)) {
            salt = UUIDUtil.uuid();
        }
        password = encryptPassword(password, salt);
        userAccount.setPassword(password);
        userAccount.setSalt(salt);
        mapper.updateByPrimaryKeySelective(userAccount);
    }

    public Map<String, Map<String, Integer>> selectMyAdminPermit(Long loginUserId) {
        String permitJson = mapper.findPermitsById(loginUserId);
        Map<String, Map<String, Integer>> rootMap = new HashMap<>();
        if (StringUtils.isBlank(permitJson)) {
            initUserPermits(loginUserId);
        } else {
            rootMap = JSON.parseObject(permitJson, Map.class);
        }
        return rootMap;
    }

    public List<UserAccount> selectByIdAndNicknameAndPermit(List<Long> idList, String nickName, String permitCode, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserAccount> resultList = mapper.selectByIdAndNicknameAndPermit(idList, nickName, permitCode);
        for (UserAccount userAccount : resultList) {
            String permitJson = userAccount.getPermits();
            Map<String, Map<String, Integer>> rootMap = new HashMap<>();
            if (StringUtils.isBlank(permitJson)) {
                userAccount.setPermits(initUserPermits(userAccount.getId()));
            }
        }
        return resultList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUserAdminPermit(Long userId, Map<String, Integer> permits) {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        map.put("permits", permits);
        mapper.savePermits(userId, JSON.toJSONString(map));
        // 动态赋角色
        boolean hasAdminRole = false;
        if (new Integer(1).equals(permits.get("configCenter"))
                || new Integer(1).equals(permits.get("dashboard"))
                || new Integer(1).equals(permits.get("configCenter"))
                || new Integer(1).equals(permits.get("userManage"))
                || new Integer(1).equals(permits.get("auditManage"))
                || new Integer(1).equals(permits.get("staticManage"))
                || new Integer(1).equals(permits.get("communityManage"))
                || new Integer(1).equals(permits.get("productMaintain"))
                || new Integer(1).equals("permissionManage")) {
            hasAdminRole = true;
        }
        if (hasAdminRole) {
            sysRoleService.assignRole(RoleConsts.ADMIN, userId);
        } else {
            sysRoleService.removeRole(RoleConsts.ADMIN, userId);
        }
    }

    public String findNicknameByUserId(Long userId) {
        return mapper.findNicknameByUserId(userId);
    }

    /**
     * 加载社区权限
     * @param userAccount
     * @throws ServiceException
     */
    public void loadCommunityAuth(UserAccount userAccount) throws ServiceException {
        // 加载社区权限
        UserCommunityAuth userCommunityAuth = userCommunityAuthService.findByUserId(userAccount.getId());
        if (Objects.isNull(userCommunityAuth)) {
            userCommunityAuth = userCommunityAuthService.initDataWithAdminFlag(userAccount.getId(), YesNoEnum.NO.getCode(), userAccount.getId());
        } else {
            // 看看是否被封禁了
            Integer banFlag = userCommunityAuth.getBlackFlag();
            if (YesNoEnum.YES.getCode() == banFlag) {
                // 如果被封禁了，继续看下是否解封了
                Date effectvieEndDate = userCommunityAuth.getEffectiveEndDate();
                if (Objects.isNull(effectvieEndDate)) {
                    // 说明永久封禁
                    throw new ServiceException("您已被永久封禁");
                }
                Date now = new Date();
                if (now.getTime() < effectvieEndDate.getTime()) {
                    // 还没解封
                    throw new ServiceException("当前账户处于封禁状态，解封时间："+ DateUtils.getDateTimeString(effectvieEndDate));
                } else {
                    // 动态解封
                    userCommunityAuthService.releaseBlack(Arrays.asList(userAccount.getId()));
                    userCommunityAuth.setBlackFlag(YesNoEnum.NO.getCode());
                    userCommunityAuth.setEffectiveStartDate(null);
                    userCommunityAuth.setEffectiveEndDate(null);
                }
            }
        }
        userAccount.setUserCommunityAuth(userCommunityAuth);
    }

    private String initUserPermits(long userId) {
        Map<String, Map<String, Integer>> rootMap = new HashMap<>();
        List<SysMenu> list = sysMenuService.selectAllValidByPid(0);
        Map<String, Integer> map = new HashMap<>();
        for (SysMenu sysMenu : list) {
            map.put(sysMenu.getPermit(), 0);
        }
        rootMap.put("permits", map);
        String permitJson = JSON.toJSONString(rootMap);
        mapper.savePermits(userId, permitJson);
        return permitJson;
    }

}
