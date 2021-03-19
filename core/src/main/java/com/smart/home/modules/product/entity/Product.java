package com.smart.home.modules.product.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
public class Product implements Serializable {

    private Integer queryType;
    private String sortType;
    private List<ProductShopMapping> productShopMappingList;
    private List<ProductParamValue> productParamValueList;
    private List<String> tagList;

    private Integer id;

    private String coverImage;

    private String bannerImages;

    private String productName;

    private String remark;

    private Date onlineDate;

    private String specification;

    private Integer brandId;

    private String brandName;

    private Integer onlineFlag;

    private Integer recommendFlag;

    private String details;

    private Integer categoryOneId;

    private String categoryOneName;

    private Integer categoryTwoId;

    private String categoryTwoName;

    private Integer categoryThreeId;

    private String categoryThreeName;

    private BigDecimal averageScore;

    private Integer oneStarCount;
    private Integer twoStarCount;
    private Integer threeStarCount;
    private Integer fourStarCount;
    private Integer fiveStarCount;

    private Integer praiseRate;

    private Integer commentCount;

    private Integer collectCount;

    private Integer testCount;

    private BigDecimal hotRate;

    private String tag;

    private Integer revision;

    private Long createdBy;

    private Date createdTime;

    private Long updatedBy;

    private Date updatedTime;
    /**
     * 支持的平台
     */
    private String supportPlatform;
    /**
     * 参数json
     */
    private String params;
    /**
     * 商店json
     */
    private String shops;
    /**
     * 删除标记：0未删除1已删除
     */
    private Integer deleteFlag;
    /**
     * 排序，越大越靠前
     */
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public Product withId(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public Product withCoverImage(String coverImage) {
        this.setCoverImage(coverImage);
        return this;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getBannerImages() {
        return bannerImages;
    }

    public Product withBannerImages(String bannerImages) {
        this.setBannerImages(bannerImages);
        return this;
    }

    public void setBannerImages(String bannerImages) {
        this.bannerImages = bannerImages;
    }

    public String getProductName() {
        return productName;
    }

    public Product withProductName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemark() {
        return remark;
    }

    public Product withRemark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public Product withOnlineDate(Date onlineDate) {
        this.setOnlineDate(onlineDate);
        return this;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public String getSpecification() {
        return specification;
    }

    public Product withSpecification(String specification) {
        this.setSpecification(specification);
        return this;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Product withBrandId(Integer brandId) {
        this.setBrandId(brandId);
        return this;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public Product withBrandName(String brandName) {
        this.setBrandName(brandName);
        return this;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getOnlineFlag() {
        return onlineFlag;
    }

    public Product withOnlineFlag(Integer onlineFlag) {
        this.setOnlineFlag(onlineFlag);
        return this;
    }

    public void setOnlineFlag(Integer onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public Integer getRecommendFlag() {
        return recommendFlag;
    }

    public Product withRecommendFlag(Integer recommendFlag) {
        this.setRecommendFlag(recommendFlag);
        return this;
    }

    public void setRecommendFlag(Integer recommendFlag) {
        this.recommendFlag = recommendFlag;
    }

    public String getDetails() {
        return details;
    }

    public Product withDetails(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getCategoryOneId() {
        return categoryOneId;
    }

    public Product withCategoryOneId(Integer categoryOneId) {
        this.setCategoryOneId(categoryOneId);
        return this;
    }

    public void setCategoryOneId(Integer categoryOneId) {
        this.categoryOneId = categoryOneId;
    }

    public String getCategoryOneName() {
        return categoryOneName;
    }

    public Product withCategoryOneName(String categoryOneName) {
        this.setCategoryOneName(categoryOneName);
        return this;
    }

    public void setCategoryOneName(String categoryOneName) {
        this.categoryOneName = categoryOneName;
    }

    public Integer getCategoryTwoId() {
        return categoryTwoId;
    }

    public Product withCategoryTwoId(Integer categoryTwoId) {
        this.setCategoryTwoId(categoryTwoId);
        return this;
    }

    public void setCategoryTwoId(Integer categoryTwoId) {
        this.categoryTwoId = categoryTwoId;
    }

    public String getCategoryTwoName() {
        return categoryTwoName;
    }

    public Product withCategoryTwoName(String categoryTwoName) {
        this.setCategoryTwoName(categoryTwoName);
        return this;
    }

    public void setCategoryTwoName(String categoryTwoName) {
        this.categoryTwoName = categoryTwoName;
    }

    public Integer getCategoryThreeId() {
        return categoryThreeId;
    }

    public Product withCategoryThreeId(Integer categoryThreeId) {
        this.setCategoryThreeId(categoryThreeId);
        return this;
    }

    public void setCategoryThreeId(Integer categoryThreeId) {
        this.categoryThreeId = categoryThreeId;
    }

    public String getCategoryThreeName() {
        return categoryThreeName;
    }

    public Product withCategoryThreeName(String categoryThreeName) {
        this.setCategoryThreeName(categoryThreeName);
        return this;
    }

    public void setCategoryThreeName(String categoryThreeName) {
        this.categoryThreeName = categoryThreeName;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public Product withAverageScore(BigDecimal averageScore) {
        this.setAverageScore(averageScore);
        return this;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getPraiseRate() {
        return praiseRate;
    }

    public Product withPraiseRate(Integer praiseRate) {
        this.setPraiseRate(praiseRate);
        return this;
    }

    public void setPraiseRate(Integer praiseRate) {
        this.praiseRate = praiseRate;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public Product withCommentCount(Integer commentCount) {
        this.setCommentCount(commentCount);
        return this;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getTestCount() {
        return testCount;
    }

    public Product withTestCount(Integer testCount) {
        this.setTestCount(testCount);
        return this;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }

    public BigDecimal getHotRate() {
        return hotRate;
    }

    public Product withHotRate(BigDecimal hotRate) {
        this.setHotRate(hotRate);
        return this;
    }

    public void setHotRate(BigDecimal hotRate) {
        this.hotRate = hotRate;
    }

    public String getTag() {
        return tag;
    }

    public Product withTag(String tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getRevision() {
        return revision;
    }

    public Product withRevision(Integer revision) {
        this.setRevision(revision);
        return this;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Product withCreatedBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public Product withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Product withUpdatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public Product withUpdatedTime(Date updatedTime) {
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
        sb.append(", productName=").append(productName);
        sb.append(", remark=").append(remark);
        sb.append(", onlineDate=").append(onlineDate);
        sb.append(", specification=").append(specification);
        sb.append(", brandId=").append(brandId);
        sb.append(", brandName=").append(brandName);
        sb.append(", onlineFlag=").append(onlineFlag);
        sb.append(", recommendFlag=").append(recommendFlag);
        sb.append(", details=").append(details);
        sb.append(", categoryOneId=").append(categoryOneId);
        sb.append(", categoryOneName=").append(categoryOneName);
        sb.append(", categoryTwoId=").append(categoryTwoId);
        sb.append(", categoryTwoName=").append(categoryTwoName);
        sb.append(", categoryThreeId=").append(categoryThreeId);
        sb.append(", categoryThreeName=").append(categoryThreeName);
        sb.append(", averageScore=").append(averageScore);
        sb.append(", praiseRate=").append(praiseRate);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", testCount=").append(testCount);
        sb.append(", hotRate=").append(hotRate);
        sb.append(", tag=").append(tag);
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
        Product other = (Product) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCoverImage() == null ? other.getCoverImage() == null : this.getCoverImage().equals(other.getCoverImage()))
            && (this.getBannerImages() == null ? other.getBannerImages() == null : this.getBannerImages().equals(other.getBannerImages()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getOnlineDate() == null ? other.getOnlineDate() == null : this.getOnlineDate().equals(other.getOnlineDate()))
            && (this.getSpecification() == null ? other.getSpecification() == null : this.getSpecification().equals(other.getSpecification()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getBrandName() == null ? other.getBrandName() == null : this.getBrandName().equals(other.getBrandName()))
            && (this.getOnlineFlag() == null ? other.getOnlineFlag() == null : this.getOnlineFlag().equals(other.getOnlineFlag()))
            && (this.getRecommendFlag() == null ? other.getRecommendFlag() == null : this.getRecommendFlag().equals(other.getRecommendFlag()))
            && (this.getDetails() == null ? other.getDetails() == null : this.getDetails().equals(other.getDetails()))
            && (this.getCategoryOneId() == null ? other.getCategoryOneId() == null : this.getCategoryOneId().equals(other.getCategoryOneId()))
            && (this.getCategoryOneName() == null ? other.getCategoryOneName() == null : this.getCategoryOneName().equals(other.getCategoryOneName()))
            && (this.getCategoryTwoId() == null ? other.getCategoryTwoId() == null : this.getCategoryTwoId().equals(other.getCategoryTwoId()))
            && (this.getCategoryTwoName() == null ? other.getCategoryTwoName() == null : this.getCategoryTwoName().equals(other.getCategoryTwoName()))
            && (this.getCategoryThreeId() == null ? other.getCategoryThreeId() == null : this.getCategoryThreeId().equals(other.getCategoryThreeId()))
            && (this.getCategoryThreeName() == null ? other.getCategoryThreeName() == null : this.getCategoryThreeName().equals(other.getCategoryThreeName()))
            && (this.getAverageScore() == null ? other.getAverageScore() == null : this.getAverageScore().equals(other.getAverageScore()))
            && (this.getPraiseRate() == null ? other.getPraiseRate() == null : this.getPraiseRate().equals(other.getPraiseRate()))
            && (this.getCommentCount() == null ? other.getCommentCount() == null : this.getCommentCount().equals(other.getCommentCount()))
            && (this.getTestCount() == null ? other.getTestCount() == null : this.getTestCount().equals(other.getTestCount()))
            && (this.getHotRate() == null ? other.getHotRate() == null : this.getHotRate().equals(other.getHotRate()))
            && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
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
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getOnlineDate() == null) ? 0 : getOnlineDate().hashCode());
        result = prime * result + ((getSpecification() == null) ? 0 : getSpecification().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getBrandName() == null) ? 0 : getBrandName().hashCode());
        result = prime * result + ((getOnlineFlag() == null) ? 0 : getOnlineFlag().hashCode());
        result = prime * result + ((getRecommendFlag() == null) ? 0 : getRecommendFlag().hashCode());
        result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
        result = prime * result + ((getCategoryOneId() == null) ? 0 : getCategoryOneId().hashCode());
        result = prime * result + ((getCategoryOneName() == null) ? 0 : getCategoryOneName().hashCode());
        result = prime * result + ((getCategoryTwoId() == null) ? 0 : getCategoryTwoId().hashCode());
        result = prime * result + ((getCategoryTwoName() == null) ? 0 : getCategoryTwoName().hashCode());
        result = prime * result + ((getCategoryThreeId() == null) ? 0 : getCategoryThreeId().hashCode());
        result = prime * result + ((getCategoryThreeName() == null) ? 0 : getCategoryThreeName().hashCode());
        result = prime * result + ((getAverageScore() == null) ? 0 : getAverageScore().hashCode());
        result = prime * result + ((getPraiseRate() == null) ? 0 : getPraiseRate().hashCode());
        result = prime * result + ((getCommentCount() == null) ? 0 : getCommentCount().hashCode());
        result = prime * result + ((getTestCount() == null) ? 0 : getTestCount().hashCode());
        result = prime * result + ((getHotRate() == null) ? 0 : getHotRate().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
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

    public Integer getOneStarCount() {
        return oneStarCount;
    }

    public void setOneStarCount(Integer oneStarCount) {
        this.oneStarCount = oneStarCount;
    }

    public Integer getTwoStarCount() {
        return twoStarCount;
    }

    public void setTwoStarCount(Integer twoStarCount) {
        this.twoStarCount = twoStarCount;
    }

    public Integer getThreeStarCount() {
        return threeStarCount;
    }

    public void setThreeStarCount(Integer threeStarCount) {
        this.threeStarCount = threeStarCount;
    }

    public Integer getFourStarCount() {
        return fourStarCount;
    }

    public void setFourStarCount(Integer fourStarCount) {
        this.fourStarCount = fourStarCount;
    }

    public Integer getFiveStarCount() {
        return fiveStarCount;
    }

    public void setFiveStarCount(Integer fiveStarCount) {
        this.fiveStarCount = fiveStarCount;
    }

    public String getSupportPlatform() {
        return supportPlatform;
    }

    public void setSupportPlatform(String supportPlatform) {
        this.supportPlatform = supportPlatform;
    }

    public List<ProductShopMapping> getProductShopMappingList() {
        return productShopMappingList;
    }

    public void setProductShopMappingList(List<ProductShopMapping> productShopMappingList) {
        this.productShopMappingList = productShopMappingList;
    }

    public List<ProductParamValue> getProductParamValueList() {
        return productParamValueList;
    }

    public void setProductParamValueList(List<ProductParamValue> productParamValueList) {
        this.productParamValueList = productParamValueList;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getShops() {
        return shops;
    }

    public void setShops(String shops) {
        this.shops = shops;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table public.product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        coverImage("cover_image", "coverImage", "VARCHAR", false),
        bannerImages("banner_images", "bannerImages", "VARCHAR", false),
        productName("product_name", "productName", "VARCHAR", false),
        remark("remark", "remark", "VARCHAR", false),
        onlineDate("online_date", "onlineDate", "DATE", false),
        specification("specification", "specification", "VARCHAR", false),
        brandId("brand_id", "brandId", "INTEGER", false),
        brandName("brand_name", "brandName", "VARCHAR", false),
        onlineFlag("online_flag", "onlineFlag", "INTEGER", false),
        recommendFlag("recommend_flag", "recommendFlag", "INTEGER", false),
        details("details", "details", "VARCHAR", false),
        categoryOneId("category_one_id", "categoryOneId", "INTEGER", false),
        categoryOneName("category_one_name", "categoryOneName", "VARCHAR", false),
        categoryTwoId("category_two_id", "categoryTwoId", "INTEGER", false),
        categoryTwoName("category_two_name", "categoryTwoName", "VARCHAR", false),
        categoryThreeId("category_three_id", "categoryThreeId", "INTEGER", false),
        categoryThreeName("category_three_name", "categoryThreeName", "VARCHAR", false),
        averageScore("average_score", "averageScore", "NUMERIC", false),
        praiseRate("praise_rate", "praiseRate", "INTEGER", false),
        commentCount("comment_count", "commentCount", "INTEGER", false),
        testCount("test_count", "testCount", "INTEGER", false),
        hotRate("hot_rate", "hotRate", "NUMERIC", false),
        tag("tag", "tag", "VARCHAR", false),
        revision("revision", "revision", "INTEGER", false),
        createdBy("created_by", "createdBy", "BIGINT", false),
        createdTime("created_time", "createdTime", "TIMESTAMP", false),
        updatedBy("updated_by", "updatedBy", "BIGINT", false),
        updatedTime("updated_time", "updatedTime", "TIMESTAMP", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
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
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table public.product
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
         * This method corresponds to the database table public.product
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