package com.smart.home.modules.other.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class RptDashboard implements Serializable {
    private Integer id;

    private Date createdTime;

    private Integer liveCount;

    private Long articleCount;

    private Long videoCount;

    private Long postCount;

    private Long productCommentCount;

    private Long articleCommentCount;

    private Long videoCommentCount;

    private Long postReplyCount;

    private Long productCommentReplyCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public RptDashboard withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public RptDashboard withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getLiveCount() {
        return liveCount;
    }

    public RptDashboard withLiveCount(Integer liveCount) {
        this.setLiveCount(liveCount);
        return this;
    }

    public void setLiveCount(Integer liveCount) {
        this.liveCount = liveCount;
    }

    public Long getArticleCount() {
        return articleCount;
    }

    public RptDashboard withArticleCount(Long articleCount) {
        this.setArticleCount(articleCount);
        return this;
    }

    public void setArticleCount(Long articleCount) {
        this.articleCount = articleCount;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public RptDashboard withVideoCount(Long videoCount) {
        this.setVideoCount(videoCount);
        return this;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }

    public Long getPostCount() {
        return postCount;
    }

    public RptDashboard withPostCount(Long postCount) {
        this.setPostCount(postCount);
        return this;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }

    public Long getProductCommentCount() {
        return productCommentCount;
    }

    public RptDashboard withProductCommentCount(Long productCommentCount) {
        this.setProductCommentCount(productCommentCount);
        return this;
    }

    public void setProductCommentCount(Long productCommentCount) {
        this.productCommentCount = productCommentCount;
    }

    public Long getArticleCommentCount() {
        return articleCommentCount;
    }

    public RptDashboard withArticleCommentCount(Long articleCommentCount) {
        this.setArticleCommentCount(articleCommentCount);
        return this;
    }

    public void setArticleCommentCount(Long articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    public Long getVideoCommentCount() {
        return videoCommentCount;
    }

    public RptDashboard withVideoCommentCount(Long videoCommentCount) {
        this.setVideoCommentCount(videoCommentCount);
        return this;
    }

    public void setVideoCommentCount(Long videoCommentCount) {
        this.videoCommentCount = videoCommentCount;
    }

    public Long getPostReplyCount() {
        return postReplyCount;
    }

    public RptDashboard withPostReplyCount(Long postReplyCount) {
        this.setPostReplyCount(postReplyCount);
        return this;
    }

    public void setPostReplyCount(Long postReplyCount) {
        this.postReplyCount = postReplyCount;
    }

    public Long getProductCommentReplyCount() {
        return productCommentReplyCount;
    }

    public RptDashboard withProductCommentReplyCount(Long productCommentReplyCount) {
        this.setProductCommentReplyCount(productCommentReplyCount);
        return this;
    }

    public void setProductCommentReplyCount(Long productCommentReplyCount) {
        this.productCommentReplyCount = productCommentReplyCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", liveCount=").append(liveCount);
        sb.append(", articleCount=").append(articleCount);
        sb.append(", videoCount=").append(videoCount);
        sb.append(", postCount=").append(postCount);
        sb.append(", productCommentCount=").append(productCommentCount);
        sb.append(", articleCommentCount=").append(articleCommentCount);
        sb.append(", videoCommentCount=").append(videoCommentCount);
        sb.append(", postReplyCount=").append(postReplyCount);
        sb.append(", productCommentReplyCount=").append(productCommentReplyCount);
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
        RptDashboard other = (RptDashboard) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getLiveCount() == null ? other.getLiveCount() == null : this.getLiveCount().equals(other.getLiveCount()))
            && (this.getArticleCount() == null ? other.getArticleCount() == null : this.getArticleCount().equals(other.getArticleCount()))
            && (this.getVideoCount() == null ? other.getVideoCount() == null : this.getVideoCount().equals(other.getVideoCount()))
            && (this.getPostCount() == null ? other.getPostCount() == null : this.getPostCount().equals(other.getPostCount()))
            && (this.getProductCommentCount() == null ? other.getProductCommentCount() == null : this.getProductCommentCount().equals(other.getProductCommentCount()))
            && (this.getArticleCommentCount() == null ? other.getArticleCommentCount() == null : this.getArticleCommentCount().equals(other.getArticleCommentCount()))
            && (this.getVideoCommentCount() == null ? other.getVideoCommentCount() == null : this.getVideoCommentCount().equals(other.getVideoCommentCount()))
            && (this.getPostReplyCount() == null ? other.getPostReplyCount() == null : this.getPostReplyCount().equals(other.getPostReplyCount()))
            && (this.getProductCommentReplyCount() == null ? other.getProductCommentReplyCount() == null : this.getProductCommentReplyCount().equals(other.getProductCommentReplyCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getLiveCount() == null) ? 0 : getLiveCount().hashCode());
        result = prime * result + ((getArticleCount() == null) ? 0 : getArticleCount().hashCode());
        result = prime * result + ((getVideoCount() == null) ? 0 : getVideoCount().hashCode());
        result = prime * result + ((getPostCount() == null) ? 0 : getPostCount().hashCode());
        result = prime * result + ((getProductCommentCount() == null) ? 0 : getProductCommentCount().hashCode());
        result = prime * result + ((getArticleCommentCount() == null) ? 0 : getArticleCommentCount().hashCode());
        result = prime * result + ((getVideoCommentCount() == null) ? 0 : getVideoCommentCount().hashCode());
        result = prime * result + ((getPostReplyCount() == null) ? 0 : getPostReplyCount().hashCode());
        result = prime * result + ((getProductCommentReplyCount() == null) ? 0 : getProductCommentReplyCount().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table public.rpt_dashboard
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        createdTime("created_time", "createdTime", "DATE", false),
        liveCount("live_count", "liveCount", "INTEGER", false),
        articleCount("article_count", "articleCount", "BIGINT", false),
        videoCount("video_count", "videoCount", "BIGINT", false),
        postCount("post_count", "postCount", "BIGINT", false),
        productCommentCount("product_comment_count", "productCommentCount", "BIGINT", false),
        articleCommentCount("article_comment_count", "articleCommentCount", "BIGINT", false),
        videoCommentCount("video_comment_count", "videoCommentCount", "BIGINT", false),
        postReplyCount("post_reply_count", "postReplyCount", "BIGINT", false),
        productCommentReplyCount("product_comment_reply_count", "productCommentReplyCount", "BIGINT", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
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
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.rpt_dashboard
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
         * This method corresponds to the database table public.rpt_dashboard
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