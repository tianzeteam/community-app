package com.smart.home.modules.system.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SysConfigExample {
    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    protected String orderByClause;

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    protected boolean distinct;

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfigExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public SysConfigExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setPageInfo(Integer currentPage, Integer pageSize) {
        if(pageSize<1) throw new IllegalArgumentException("页大小不能小于1！");
        this.limit=pageSize;
        if(currentPage<1) throw new IllegalArgumentException("页数不能小于1！");
        this.offset=(currentPage-1)*pageSize;
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public static Criteria newAndCreateCriteria() {
        SysConfigExample example = new SysConfigExample();
        return example.createCriteria();
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIdLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParamKeyIsNull() {
            addCriterion("param_key is null");
            return (Criteria) this;
        }

        public Criteria andParamKeyIsNotNull() {
            addCriterion("param_key is not null");
            return (Criteria) this;
        }

        public Criteria andParamKeyEqualTo(String value) {
            addCriterion("param_key =", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyNotEqualTo(String value) {
            addCriterion("param_key <>", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyGreaterThan(String value) {
            addCriterion("param_key >", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyGreaterThanOrEqualTo(String value) {
            addCriterion("param_key >=", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyLessThan(String value) {
            addCriterion("param_key <", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyLessThanOrEqualTo(String value) {
            addCriterion("param_key <=", value, "paramKey");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamKeyLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_key <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamKeyLike(String value) {
            addCriterion("param_key like", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotLike(String value) {
            addCriterion("param_key not like", value, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyIn(List<String> values) {
            addCriterion("param_key in", values, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotIn(List<String> values) {
            addCriterion("param_key not in", values, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyBetween(String value1, String value2) {
            addCriterion("param_key between", value1, value2, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamKeyNotBetween(String value1, String value2) {
            addCriterion("param_key not between", value1, value2, "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNull() {
            addCriterion("param_value is null");
            return (Criteria) this;
        }

        public Criteria andParamValueIsNotNull() {
            addCriterion("param_value is not null");
            return (Criteria) this;
        }

        public Criteria andParamValueEqualTo(String value) {
            addCriterion("param_value =", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueNotEqualTo(String value) {
            addCriterion("param_value <>", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThan(String value) {
            addCriterion("param_value >", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueGreaterThanOrEqualTo(String value) {
            addCriterion("param_value >=", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueLessThan(String value) {
            addCriterion("param_value <", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueLessThanOrEqualTo(String value) {
            addCriterion("param_value <=", value, "paramValue");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andParamValueLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("param_value <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andParamValueLike(String value) {
            addCriterion("param_value like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotLike(String value) {
            addCriterion("param_value not like", value, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueIn(List<String> values) {
            addCriterion("param_value in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotIn(List<String> values) {
            addCriterion("param_value not in", values, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueBetween(String value1, String value2) {
            addCriterion("param_value between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andParamValueNotBetween(String value1, String value2) {
            addCriterion("param_value not between", value1, value2, "paramValue");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNull() {
            addCriterion("revision is null");
            return (Criteria) this;
        }

        public Criteria andRevisionIsNotNull() {
            addCriterion("revision is not null");
            return (Criteria) this;
        }

        public Criteria andRevisionEqualTo(Integer value) {
            addCriterion("revision =", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionNotEqualTo(Integer value) {
            addCriterion("revision <>", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThan(Integer value) {
            addCriterion("revision >", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionGreaterThanOrEqualTo(Integer value) {
            addCriterion("revision >=", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionLessThan(Integer value) {
            addCriterion("revision <", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionLessThanOrEqualTo(Integer value) {
            addCriterion("revision <=", value, "revision");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andRevisionLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("revision <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andRevisionIn(List<Integer> values) {
            addCriterion("revision in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotIn(List<Integer> values) {
            addCriterion("revision not in", values, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionBetween(Integer value1, Integer value2) {
            addCriterion("revision between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andRevisionNotBetween(Integer value1, Integer value2) {
            addCriterion("revision not between", value1, value2, "revision");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(String value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(String value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(String value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(String value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(String value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(String value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedByLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_by <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedByLike(String value) {
            addCriterion("created_by like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotLike(String value) {
            addCriterion("created_by not like", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<String> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<String> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(String value1, String value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(String value1, String value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterionForJDBCDate("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterionForJDBCDate("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andCreatedTimeLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("created_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterionForJDBCDate("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNull() {
            addCriterion("updated_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIsNotNull() {
            addCriterion("updated_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedByEqualTo(String value) {
            addCriterion("updated_by =", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotEqualTo(String value) {
            addCriterion("updated_by <>", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThan(String value) {
            addCriterion("updated_by >", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByGreaterThanOrEqualTo(String value) {
            addCriterion("updated_by >=", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThan(String value) {
            addCriterion("updated_by <", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByLessThanOrEqualTo(String value) {
            addCriterion("updated_by <=", value, "updatedBy");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedByLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_by <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedByLike(String value) {
            addCriterion("updated_by like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotLike(String value) {
            addCriterion("updated_by not like", value, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByIn(List<String> values) {
            addCriterion("updated_by in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotIn(List<String> values) {
            addCriterion("updated_by not in", values, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByBetween(String value1, String value2) {
            addCriterion("updated_by between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByNotBetween(String value1, String value2) {
            addCriterion("updated_by not between", value1, value2, "updatedBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNull() {
            addCriterion("updated_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNotNull() {
            addCriterion("updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeEqualTo(Date value) {
            addCriterionForJDBCDate("updated_time =", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("updated_time <>", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeNotEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("updated_time >", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeGreaterThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("updated_time >=", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeGreaterThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThan(Date value) {
            addCriterionForJDBCDate("updated_time <", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeLessThanColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("updated_time <=", value, "updatedTime");
            return (Criteria) this;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andUpdatedTimeLessThanOrEqualToColumn(SysConfig.Column column) {
            addCriterion(new StringBuilder("updated_time <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIn(List<Date> values) {
            addCriterionForJDBCDate("updated_time in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("updated_time not in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("updated_time between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("updated_time not between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andParamKeyLikeInsensitive(String value) {
            addCriterion("upper(param_key) like", value.toUpperCase(), "paramKey");
            return (Criteria) this;
        }

        public Criteria andParamValueLikeInsensitive(String value) {
            addCriterion("upper(param_value) like", value.toUpperCase(), "paramValue");
            return (Criteria) this;
        }

        public Criteria andCreatedByLikeInsensitive(String value) {
            addCriterion("upper(created_by) like", value.toUpperCase(), "createdBy");
            return (Criteria) this;
        }

        public Criteria andUpdatedByLikeInsensitive(String value) {
            addCriterion("upper(updated_by) like", value.toUpperCase(), "updatedBy");
            return (Criteria) this;
        }
    }

    /**
     *
     * @mbg.generated do_not_delete_during_merge Thu Feb 18 00:33:46 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {
        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        private SysConfigExample example;

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        protected Criteria(SysConfigExample example) {
            super();
            this.example = example;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public SysConfigExample example() {
            return this.example;
        }

        /**
         *
         * @mbg.generated Thu Feb 18 00:33:46 CST 2021
         */
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public interface ICriteriaAdd {
            /**
             *
             * @mbg.generated Thu Feb 18 00:33:46 CST 2021
             */
            Criteria add(Criteria add);
        }
    }

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}