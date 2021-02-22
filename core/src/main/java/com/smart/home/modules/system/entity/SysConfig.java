package com.smart.home.modules.system.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created By MBG-GUI-EXTENSION https://github.com/spawpaw/mybatis-generator-gui-extension
 * Description:
 * 系统配置
 *
 * @author 
 */
public class SysConfig implements Serializable {
    /**
     * 主键ID
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private Integer id;

    /**
     * 参数KEY
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private String paramKey;

    /**
     * 参数VALUE
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private String paramValue;

    private String remark;

    /**
     * 乐观锁
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private Integer revision;

    /**
     * 创建人
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private String createdBy;

    /**
     * 创建时间
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private Date createdTime;

    /**
     * 更新人
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private String updatedBy;

    /**
     * 更新时间
     *
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private Date updatedTime;

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method returns the value of the database column public.sys_config.id
     *
     * @return the value of public.sys_config.id
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withId(Integer id) {
        this.setId(id);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.id
     *
     * @param id the value for public.sys_config.id
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method returns the value of the database column public.sys_config.param_key
     *
     * @return the value of public.sys_config.param_key
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withParamKey(String paramKey) {
        this.setParamKey(paramKey);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.param_key
     *
     * @param paramKey the value for public.sys_config.param_key
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * This method returns the value of the database column public.sys_config.param_value
     *
     * @return the value of public.sys_config.param_value
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withParamValue(String paramValue) {
        this.setParamValue(paramValue);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.param_value
     *
     * @param paramValue the value for public.sys_config.param_value
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * This method returns the value of the database column public.sys_config.revision
     *
     * @return the value of public.sys_config.revision
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Integer getRevision() {
        return revision;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withRevision(Integer revision) {
        this.setRevision(revision);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.revision
     *
     * @param revision the value for public.sys_config.revision
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    /**
     * This method returns the value of the database column public.sys_config.created_by
     *
     * @return the value of public.sys_config.created_by
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withCreatedBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.created_by
     *
     * @param createdBy the value for public.sys_config.created_by
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method returns the value of the database column public.sys_config.created_time
     *
     * @return the value of public.sys_config.created_time
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withCreatedTime(Date createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.created_time
     *
     * @param createdTime the value for public.sys_config.created_time
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method returns the value of the database column public.sys_config.updated_by
     *
     * @return the value of public.sys_config.updated_by
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withUpdatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.updated_by
     *
     * @param updatedBy the value for public.sys_config.updated_by
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method returns the value of the database column public.sys_config.updated_time
     *
     * @return the value of public.sys_config.updated_time
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfig withUpdatedTime(Date updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    /**
     * This method sets the value of the database column public.sys_config.updated_time
     *
     * @param updatedTime the value for public.sys_config.updated_time
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", paramKey=").append(paramKey);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", revision=").append(revision);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedBy=").append(updatedBy);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
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
        SysConfig other = (SysConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParamKey() == null ? other.getParamKey() == null : this.getParamKey().equals(other.getParamKey()))
            && (this.getParamValue() == null ? other.getParamValue() == null : this.getParamValue().equals(other.getParamValue()))
            && (this.getRevision() == null ? other.getRevision() == null : this.getRevision().equals(other.getRevision()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParamKey() == null) ? 0 : getParamKey().hashCode());
        result = prime * result + ((getParamValue() == null) ? 0 : getParamValue().hashCode());
        result = prime * result + ((getRevision() == null) ? 0 : getRevision().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        paramKey("param_key", "paramKey", "VARCHAR", false),
        paramValue("param_value", "paramValue", "VARCHAR", false),
        revision("revision", "revision", "INTEGER", false),
        createdBy("created_by", "createdBy", "VARCHAR", false),
        createdTime("created_time", "createdTime", "DATE", false),
        updatedBy("updated_by", "updatedBy", "VARCHAR", false),
        updatedTime("updated_time", "updatedTime", "DATE", false);

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private final String column;

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private final boolean isColumnNameDelimited;

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private final String javaProperty;

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private final String jdbcType;

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String value() {
            return this.column;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String getValue() {
            return this.column;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
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