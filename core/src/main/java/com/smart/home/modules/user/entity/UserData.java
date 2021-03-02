package com.smart.home.modules.user.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UserData implements Serializable {

    private String username;
    private String headUrl;
    private Integer blackFlag;
    private Integer speakFlag;
    private Date effectiveEndDate;
    //////////////////////////
    private Long id;

    private Long userId;

    private String wxOpenid;

    private String sign;

    private String remark;

    private Integer userLevel;

    private Integer userLevelCount;

    private Integer focusCount;

    private Integer followCount;

    private Integer likeCount;

    private Integer postCount;

    private Integer commentCount;

    private Integer replyCount;

    private Integer evaluateCount;

    private Integer contributeCount;

    private Integer revision;

    private Long createdBy;

    private Date createdTime;

    private Long updatedBy;

    private Date updatedTime;

    private Integer hitSensitiveCount;
    private Integer byReportCount;
    private String reportReasonRate;
    private Integer textExceptionCount;
    private Integer imageExceptionCount;
    private Integer userAuditExceptionCount;
    private Integer blackCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public UserData withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public UserData withUserId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public UserData withWxOpenid(String wxOpenid) {
        this.setWxOpenid(wxOpenid);
        return this;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getSign() {
        return sign;
    }

    public UserData withSign(String sign) {
        this.setSign(sign);
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return remark;
    }

    public UserData withRemark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public UserData withUserLevel(Integer userLevel) {
        this.setUserLevel(userLevel);
        return this;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getUserLevelCount() {
        return userLevelCount;
    }

    public UserData withUserLevelCount(Integer userLevelCount) {
        this.setUserLevelCount(userLevelCount);
        return this;
    }

    public void setUserLevelCount(Integer userLevelCount) {
        this.userLevelCount = userLevelCount;
    }

    public Integer getFocusCount() {
        return focusCount;
    }

    public UserData withFocusCount(Integer focusCount) {
        this.setFocusCount(focusCount);
        return this;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public UserData withFollowCount(Integer followCount) {
        this.setFollowCount(followCount);
        return this;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public UserData withLikeCount(Integer likeCount) {
        this.setLikeCount(likeCount);
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public UserData withPostCount(Integer postCount) {
        this.setPostCount(postCount);
        return this;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public UserData withCommentCount(Integer commentCount) {
        this.setCommentCount(commentCount);
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public UserData withReplyCount(Integer replyCount) {
        this.setReplyCount(replyCount);
        return this;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getEvaluateCount() {
        return evaluateCount;
    }

    public UserData withEvaluateCount(Integer evaluateCount) {
        this.setEvaluateCount(evaluateCount);
        return this;
    }

    public void setEvaluateCount(Integer evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Integer getContributeCount() {
        return contributeCount;
    }

    public UserData withContributeCount(Integer contributeCount) {
        this.setContributeCount(contributeCount);
        return this;
    }

    public void setContributeCount(Integer contributeCount) {
        this.contributeCount = contributeCount;
    }

    public Integer getRevision() {
        return revision;
    }

    public UserData withRevision(Integer revision) {
        this.setRevision(revision);
        return this;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public UserData withCreatedBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public UserData withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public UserData withUpdatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public UserData withUpdatedTime(Date updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", wxOpenid=").append(wxOpenid);
        sb.append(", sign=").append(sign);
        sb.append(", remark=").append(remark);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", userLevelCount=").append(userLevelCount);
        sb.append(", focusCount=").append(focusCount);
        sb.append(", followCount=").append(followCount);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", postCount=").append(postCount);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", replyCount=").append(replyCount);
        sb.append(", evaluateCount=").append(evaluateCount);
        sb.append(", contributeCount=").append(contributeCount);
        sb.append(", revision=").append(revision);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserData other = (UserData) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getWxOpenid() == null ? other.getWxOpenid() == null : this.getWxOpenid().equals(other.getWxOpenid()))
            && (this.getSign() == null ? other.getSign() == null : this.getSign().equals(other.getSign()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getUserLevel() == null ? other.getUserLevel() == null : this.getUserLevel().equals(other.getUserLevel()))
            && (this.getUserLevelCount() == null ? other.getUserLevelCount() == null : this.getUserLevelCount().equals(other.getUserLevelCount()))
            && (this.getFocusCount() == null ? other.getFocusCount() == null : this.getFocusCount().equals(other.getFocusCount()))
            && (this.getFollowCount() == null ? other.getFollowCount() == null : this.getFollowCount().equals(other.getFollowCount()))
            && (this.getLikeCount() == null ? other.getLikeCount() == null : this.getLikeCount().equals(other.getLikeCount()))
            && (this.getPostCount() == null ? other.getPostCount() == null : this.getPostCount().equals(other.getPostCount()))
            && (this.getCommentCount() == null ? other.getCommentCount() == null : this.getCommentCount().equals(other.getCommentCount()))
            && (this.getReplyCount() == null ? other.getReplyCount() == null : this.getReplyCount().equals(other.getReplyCount()))
            && (this.getEvaluateCount() == null ? other.getEvaluateCount() == null : this.getEvaluateCount().equals(other.getEvaluateCount()))
            && (this.getContributeCount() == null ? other.getContributeCount() == null : this.getContributeCount().equals(other.getContributeCount()))
            && (this.getRevision() == null ? other.getRevision() == null : this.getRevision().equals(other.getRevision()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getWxOpenid() == null) ? 0 : getWxOpenid().hashCode());
        result = prime * result + ((getSign() == null) ? 0 : getSign().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getUserLevel() == null) ? 0 : getUserLevel().hashCode());
        result = prime * result + ((getUserLevelCount() == null) ? 0 : getUserLevelCount().hashCode());
        result = prime * result + ((getFocusCount() == null) ? 0 : getFocusCount().hashCode());
        result = prime * result + ((getFollowCount() == null) ? 0 : getFollowCount().hashCode());
        result = prime * result + ((getLikeCount() == null) ? 0 : getLikeCount().hashCode());
        result = prime * result + ((getPostCount() == null) ? 0 : getPostCount().hashCode());
        result = prime * result + ((getCommentCount() == null) ? 0 : getCommentCount().hashCode());
        result = prime * result + ((getReplyCount() == null) ? 0 : getReplyCount().hashCode());
        result = prime * result + ((getEvaluateCount() == null) ? 0 : getEvaluateCount().hashCode());
        result = prime * result + ((getContributeCount() == null) ? 0 : getContributeCount().hashCode());
        result = prime * result + ((getRevision() == null) ? 0 : getRevision().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getHitSensitiveCount() {
        return hitSensitiveCount;
    }

    public void setHitSensitiveCount(Integer hitSensitiveCount) {
        this.hitSensitiveCount = hitSensitiveCount;
    }

    public Integer getByReportCount() {
        return byReportCount;
    }

    public void setByReportCount(Integer byReportCount) {
        this.byReportCount = byReportCount;
    }

    public String getReportReasonRate() {
        return reportReasonRate;
    }

    public void setReportReasonRate(String reportReasonRate) {
        this.reportReasonRate = reportReasonRate;
    }

    public Integer getTextExceptionCount() {
        return textExceptionCount;
    }

    public void setTextExceptionCount(Integer textExceptionCount) {
        this.textExceptionCount = textExceptionCount;
    }

    public Integer getImageExceptionCount() {
        return imageExceptionCount;
    }

    public void setImageExceptionCount(Integer imageExceptionCount) {
        this.imageExceptionCount = imageExceptionCount;
    }

    public Integer getUserAuditExceptionCount() {
        return userAuditExceptionCount;
    }

    public void setUserAuditExceptionCount(Integer userAuditExceptionCount) {
        this.userAuditExceptionCount = userAuditExceptionCount;
    }

    public Integer getBlackCount() {
        return blackCount;
    }

    public void setBlackCount(Integer blackCount) {
        this.blackCount = blackCount;
    }

    public Integer getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Integer blackFlag) {
        this.blackFlag = blackFlag;
    }

    public Integer getSpeakFlag() {
        return speakFlag;
    }

    public void setSpeakFlag(Integer speakFlag) {
        this.speakFlag = speakFlag;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table public.user_data
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        userId("user_id", "userId", "BIGINT", false),
        wxOpenid("wx_openid", "wxOpenid", "VARCHAR", false),
        sign("sign", "sign", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        userLevel("user_level", "userLevel", "INTEGER", false),
        userLevelCount("user_level_count", "userLevelCount", "INTEGER", false),
        focusCount("focus_count", "focusCount", "INTEGER", false),
        followCount("follow_count", "followCount", "INTEGER", false),
        likeCount("like_count", "likeCount", "INTEGER", false),
        postCount("post_count", "postCount", "INTEGER", false),
        commentCount("comment_count", "commentCount", "INTEGER", false),
        replyCount("reply_count", "replyCount", "INTEGER", false),
        evaluateCount("evaluate_count", "evaluateCount", "INTEGER", false),
        contributeCount("contribute_count", "contributeCount", "INTEGER", false),
        revision("revision", "revision", "INTEGER", false),
        createdBy("created_by", "createdBy", "BIGINT", false),
        createdTime("created_time", "createdTime", "TIMESTAMP", false),
        updatedBy("updated_by", "updatedBy", "BIGINT", false),
        updatedTime("updated_time", "updatedTime", "TIMESTAMP", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.user_data
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}