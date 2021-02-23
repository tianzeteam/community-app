package com.smart.home.modules.community.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CommunityPostReply implements Serializable {
    private Long id;

    private Long userId;

    private Long postId;

    private Long postReplyId;

    private Integer replyType;

    private String contents;

    private Integer likeCount;

    private Date createdTime;

    private Integer stampCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public CommunityPostReply withId(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public CommunityPostReply withUserId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public CommunityPostReply withPostId(Long postId) {
        this.setPostId(postId);
        return this;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getPostReplyId() {
        return postReplyId;
    }

    public CommunityPostReply withPostReplyId(Long postReplyId) {
        this.setPostReplyId(postReplyId);
        return this;
    }

    public void setPostReplyId(Long postReplyId) {
        this.postReplyId = postReplyId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public CommunityPostReply withReplyType(Integer replyType) {
        this.setReplyType(replyType);
        return this;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getContents() {
        return contents;
    }

    public CommunityPostReply withContents(String contents) {
        this.setContents(contents);
        return this;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public CommunityPostReply withLikeCount(Integer likeCount) {
        this.setLikeCount(likeCount);
        return this;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public CommunityPostReply withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getStampCount() {
        return stampCount;
    }

    public CommunityPostReply withStampCount(Integer stampCount) {
        this.setStampCount(stampCount);
        return this;
    }

    public void setStampCount(Integer stampCount) {
        this.stampCount = stampCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", postId=").append(postId);
        sb.append(", postReplyId=").append(postReplyId);
        sb.append(", replyType=").append(replyType);
        sb.append(", contents=").append(contents);
        sb.append(", likeCount=").append(likeCount);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", stampCount=").append(stampCount);
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
        CommunityPostReply other = (CommunityPostReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPostId() == null ? other.getPostId() == null : this.getPostId().equals(other.getPostId()))
            && (this.getPostReplyId() == null ? other.getPostReplyId() == null : this.getPostReplyId().equals(other.getPostReplyId()))
            && (this.getReplyType() == null ? other.getReplyType() == null : this.getReplyType().equals(other.getReplyType()))
            && (this.getContents() == null ? other.getContents() == null : this.getContents().equals(other.getContents()))
            && (this.getLikeCount() == null ? other.getLikeCount() == null : this.getLikeCount().equals(other.getLikeCount()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getStampCount() == null ? other.getStampCount() == null : this.getStampCount().equals(other.getStampCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPostId() == null) ? 0 : getPostId().hashCode());
        result = prime * result + ((getPostReplyId() == null) ? 0 : getPostReplyId().hashCode());
        result = prime * result + ((getReplyType() == null) ? 0 : getReplyType().hashCode());
        result = prime * result + ((getContents() == null) ? 0 : getContents().hashCode());
        result = prime * result + ((getLikeCount() == null) ? 0 : getLikeCount().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getStampCount() == null) ? 0 : getStampCount().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table public.community_post_reply
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        userId("user_id", "userId", "BIGINT", false),
        postId("post_id", "postId", "BIGINT", false),
        postReplyId("post_reply_id", "postReplyId", "BIGINT", false),
        replyType("reply_type", "replyType", "INTEGER", false),
        contents("contents", "contents", "VARCHAR", false),
        likeCount("like_count", "likeCount", "INTEGER", false),
        createdTime("created_time", "createdTime", "TIMESTAMP", false),
        stampCount("stamp_count", "stampCount", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
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
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.community_post_reply
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
         * This method corresponds to the database table public.community_post_reply
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