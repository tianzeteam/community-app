package com.smart.home.common.contants;

/**
 * @author jason
 * @date 2021/2/20
 **/
public class RoleConsts {

    /**
     * 超级管理员
     */
    public static final String SUPER_ADMIN = "superadmin";
    /**
     * 注册用户, 可参与用户侧一切活动
     */
    public static final String REGISTER = "register";
    /**
     * 运营
     */
    public static final String OPERATOR = "operator";
    /**
     * 创作者， 可以在web端投稿，任何一个用户可以在web端申请成为创作者
     */
    public static final String CREATOR = "creator";
    /**
     * 审核，可以审核文章，视频，帖子；评论的查看和封禁
     */
    public static final String AUDITOR = "auditor";
    /**
     * 管理，可进行版主，创作者，运营，审核权限（中的一种或多种）的授权
     */
    public static final String ADMIN = "admin";

}
