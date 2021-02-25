package com.smart.home.modules.article.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Article implements Serializable {

    private String headUrl;
    private String nickName;
    private Integer userLevel;
////////////////////////////////
    private Long id;

    private String coverImage;

    private String bannerImages;

    private String title;

    private String remark;

    private String tag;

    private Long channelId;

    private String details;

    private Long userId;

    private Integer state;

    private Integer auditState;

    private String rejectReason;

    private Integer recommendFlag;

    private Integer topFlag;

    private Integer testFlag;

    private String testResult;

    private Integer likeCount;

    private Integer stampCount;

    private Integer category;

    private Integer commentCount;

    private Integer visitCount;

    private Integer reportCount;

    private Integer collectCount;

    private Integer revision;

    private Long createdBy;

    private Date createdTime;

    private Long updatedBy;

    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public Article withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Article withCoverImage(String coverImage) {
        this.setCoverImage(coverImage);
        return this;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBannerImages() {
        return bannerImages;
    }

    public Article withBannerImages(String bannerImages) {
        this.setBannerImages(bannerImages);
        return this;
    }

    public void setBannerImages(String bannerImages) {
        this.bannerImages = bannerImages;
    }

    public String getTitle() {
        return title;
    }

    public Article withTitle(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public Article withRemark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public Article withTag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Article withChannelId(Long channelId) {
        this.setChannelId(channelId);
        return this;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getDetails() {
        return details;
    }

    public Article withDetails(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getUserId() {
        return userId;
    }

    public Article withUserId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getState() {
        return state;
    }

    public Article withState(Integer state) {
        this.setState(state);
        return this;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public Article withAuditState(Integer auditState) {
        this.setAuditState(auditState);
        return this;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public Article withRejectReason(String rejectReason) {
        this.setRejectReason(rejectReason);
        return this;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Integer getRecommendFlag() {
        return recommendFlag;
    }

    public Article withRecommendFlag(Integer recommendFlag) {
        this.setRecommendFlag(recommendFlag);
        return this;
    }

    public void setRecommendFlag(Integer recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public Integer getTopFlag() {
        return topFlag;
    }

    public Article withTopFlag(Integer topFlag) {
        this.setTopFlag(topFlag);
        return this;
    }

    public void setTopFlag(Integer topFlag) {
        this.topFlag = topFlag;
    }

    public Integer getTestFlag() {
        return testFlag;
    }

    public Article withTestFlag(Integer testFlag) {
        this.setTestFlag(testFlag);
        return this;
    }

    public void setTestFlag(Integer testFlag) {
        this.testFlag = testFlag;
    }

    public String getTestResult() {
        return testResult;
    }

    public Article withTestResult(String testResult) {
        this.setTestResult(testResult);
        return this;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public Article withLikeCount(Integer likeCount) {
        this.setLikeCount(likeCount);
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getStampCount() {
        return stampCount;
    }

    public Article withStampCount(Integer stampCount) {
        this.setStampCount(stampCount);
        return this;
    }

    public void setStampCount(Integer stampCount) {
        this.stampCount = stampCount;
    }

    public Integer getCategory() {
        return category;
    }

    public Article withCategory(Integer category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Article withCommentCount(Integer commentCount) {
        this.setCommentCount(commentCount);
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public Article withVisitCount(Integer visitCount) {
        this.setVisitCount(visitCount);
        return this;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public Article withReportCount(Integer reportCount) {
        this.setReportCount(reportCount);
        return this;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public Integer getRevision() {
        return revision;
    }

    public Article withRevision(Integer revision) {
        this.setRevision(revision);
        return this;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Article withCreatedBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Article withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Article withUpdatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public Article withUpdatedTime(Date updatedTime) {
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
        sb.append(", coverImage=").append(coverImage);
        sb.append(", bannerImages=").append(bannerImages);
        sb.append(", title=").append(title);
        sb.append(", remark=").append(remark);
        sb.append(", tag=").append(tag);
        sb.append(", channelId=").append(channelId);
        sb.append(", details=").append(details);
        sb.append(", userId=").append(userId);
        sb.append(", state=").append(state);
        sb.append(", auditState=").append(auditState);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append(", recommendFlag=").append(recommendFlag);
        sb.append(", topFlag=").append(topFlag);
        sb.append(", testFlag=").append(testFlag);
        sb.append(", testResult=").append(testResult);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", stampCount=").append(stampCount);
        sb.append(", category=").append(category);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", visitCount=").append(visitCount);
        sb.append(", reportCount=").append(reportCount);
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
        Article other = (Article) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCoverImage() == null ? other.getCoverImage() == null : this.getCoverImage().equals(other.getCoverImage()))
            && (this.getBannerImages() == null ? other.getBannerImages() == null : this.getBannerImages().equals(other.getBannerImages()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
            && (this.getChannelId() == null ? other.getChannelId() == null : this.getChannelId().equals(other.getChannelId()))
            && (this.getDetails() == null ? other.getDetails() == null : this.getDetails().equals(other.getDetails()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getAuditState() == null ? other.getAuditState() == null : this.getAuditState().equals(other.getAuditState()))
            && (this.getRejectReason() == null ? other.getRejectReason() == null : this.getRejectReason().equals(other.getRejectReason()))
            && (this.getRecommendFlag() == null ? other.getRecommendFlag() == null : this.getRecommendFlag().equals(other.getRecommendFlag()))
            && (this.getTopFlag() == null ? other.getTopFlag() == null : this.getTopFlag().equals(other.getTopFlag()))
            && (this.getTestFlag() == null ? other.getTestFlag() == null : this.getTestFlag().equals(other.getTestFlag()))
            && (this.getTestResult() == null ? other.getTestResult() == null : this.getTestResult().equals(other.getTestResult()))
            && (this.getLikeCount() == null ? other.getLikeCount() == null : this.getLikeCount().equals(other.getLikeCount()))
            && (this.getStampCount() == null ? other.getStampCount() == null : this.getStampCount().equals(other.getStampCount()))
            && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
            && (this.getCommentCount() == null ? other.getCommentCount() == null : this.getCommentCount().equals(other.getCommentCount()))
            && (this.getVisitCount() == null ? other.getVisitCount() == null : this.getVisitCount().equals(other.getVisitCount()))
            && (this.getReportCount() == null ? other.getReportCount() == null : this.getReportCount().equals(other.getReportCount()))
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
        result = prime * result + ((getCoverImage() == null) ? 0 : getCoverImage().hashCode());
        result = prime * result + ((getBannerImages() == null) ? 0 : getBannerImages().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        result = prime * result + ((getChannelId() == null) ? 0 : getChannelId().hashCode());
        result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getAuditState() == null) ? 0 : getAuditState().hashCode());
        result = prime * result + ((getRejectReason() == null) ? 0 : getRejectReason().hashCode());
        result = prime * result + ((getRecommendFlag() == null) ? 0 : getRecommendFlag().hashCode());
        result = prime * result + ((getTopFlag() == null) ? 0 : getTopFlag().hashCode());
        result = prime * result + ((getTestFlag() == null) ? 0 : getTestFlag().hashCode());
        result = prime * result + ((getTestResult() == null) ? 0 : getTestResult().hashCode());
        result = prime * result + ((getLikeCount() == null) ? 0 : getLikeCount().hashCode());
        result = prime * result + ((getStampCount() == null) ? 0 : getStampCount().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getCommentCount() == null) ? 0 : getCommentCount().hashCode());
        result = prime * result + ((getVisitCount() == null) ? 0 : getVisitCount().hashCode());
        result = prime * result + ((getReportCount() == null) ? 0 : getReportCount().hashCode());
        result = prime * result + ((getRevision() == null) ? 0 : getRevision().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table public.article
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        coverImage("cover_image", "coverImage", "VARCHAR", false),
        bannerImages("banner_images", "bannerImages", "VARCHAR", false),
        title("title", "title", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        tag("tag", "tag", "VARCHAR", false),
        channelId("channel_id", "channelId", "BIGINT", false),
        details("details", "details", "VARCHAR", false),
        userId("user_id", "userId", "BIGINT", false),
        state("state", "state", "INTEGER", false),
        auditState("audit_state", "auditState", "INTEGER", false),
        rejectReason("reject_reason", "rejectReason", "VARCHAR", false),
        recommendFlag("recommend_flag", "recommendFlag", "INTEGER", false),
        topFlag("top_flag", "topFlag", "INTEGER", false),
        testFlag("test_flag", "testFlag", "INTEGER", false),
        testResult("test_result", "testResult", "VARCHAR", false),
        likeCount("like_count", "likeCount", "INTEGER", false),
        stampCount("stamp_count", "stampCount", "INTEGER", false),
        category("category", "category", "INTEGER", false),
        commentCount("comment_count", "commentCount", "INTEGER", false),
        visitCount("visit_count", "visitCount", "INTEGER", false),
        reportCount("report_count", "reportCount", "INTEGER", false),
        revision("revision", "revision", "INTEGER", false),
        createdBy("created_by", "createdBy", "BIGINT", false),
        createdTime("created_time", "createdTime", "TIMESTAMP", false),
        updatedBy("updated_by", "updatedBy", "BIGINT", false),
        updatedTime("updated_time", "updatedTime", "TIMESTAMP", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
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
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.article
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
         * This method corresponds to the database table public.article
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