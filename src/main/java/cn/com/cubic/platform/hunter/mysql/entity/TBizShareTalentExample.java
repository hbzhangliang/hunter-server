package cn.com.cubic.platform.hunter.mysql.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TBizShareTalentExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBizShareTalentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andShareTypeIsNull() {
            addCriterion("share_type is null");
            return (Criteria) this;
        }

        public Criteria andShareTypeIsNotNull() {
            addCriterion("share_type is not null");
            return (Criteria) this;
        }

        public Criteria andShareTypeEqualTo(String value) {
            addCriterion("share_type =", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeNotEqualTo(String value) {
            addCriterion("share_type <>", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeGreaterThan(String value) {
            addCriterion("share_type >", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeGreaterThanOrEqualTo(String value) {
            addCriterion("share_type >=", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeLessThan(String value) {
            addCriterion("share_type <", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeLessThanOrEqualTo(String value) {
            addCriterion("share_type <=", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeLike(String value) {
            addCriterion("share_type like", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeNotLike(String value) {
            addCriterion("share_type not like", value, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeIn(List<String> values) {
            addCriterion("share_type in", values, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeNotIn(List<String> values) {
            addCriterion("share_type not in", values, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeBetween(String value1, String value2) {
            addCriterion("share_type between", value1, value2, "shareType");
            return (Criteria) this;
        }

        public Criteria andShareTypeNotBetween(String value1, String value2) {
            addCriterion("share_type not between", value1, value2, "shareType");
            return (Criteria) this;
        }

        public Criteria andTalentIdIsNull() {
            addCriterion("talent_id is null");
            return (Criteria) this;
        }

        public Criteria andTalentIdIsNotNull() {
            addCriterion("talent_id is not null");
            return (Criteria) this;
        }

        public Criteria andTalentIdEqualTo(Long value) {
            addCriterion("talent_id =", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdNotEqualTo(Long value) {
            addCriterion("talent_id <>", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdGreaterThan(Long value) {
            addCriterion("talent_id >", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("talent_id >=", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdLessThan(Long value) {
            addCriterion("talent_id <", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdLessThanOrEqualTo(Long value) {
            addCriterion("talent_id <=", value, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdIn(List<Long> values) {
            addCriterion("talent_id in", values, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdNotIn(List<Long> values) {
            addCriterion("talent_id not in", values, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdBetween(Long value1, Long value2) {
            addCriterion("talent_id between", value1, value2, "talentId");
            return (Criteria) this;
        }

        public Criteria andTalentIdNotBetween(Long value1, Long value2) {
            addCriterion("talent_id not between", value1, value2, "talentId");
            return (Criteria) this;
        }

        public Criteria andShareValueIsNull() {
            addCriterion("share_value is null");
            return (Criteria) this;
        }

        public Criteria andShareValueIsNotNull() {
            addCriterion("share_value is not null");
            return (Criteria) this;
        }

        public Criteria andShareValueEqualTo(String value) {
            addCriterion("share_value =", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueNotEqualTo(String value) {
            addCriterion("share_value <>", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueGreaterThan(String value) {
            addCriterion("share_value >", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueGreaterThanOrEqualTo(String value) {
            addCriterion("share_value >=", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueLessThan(String value) {
            addCriterion("share_value <", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueLessThanOrEqualTo(String value) {
            addCriterion("share_value <=", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueLike(String value) {
            addCriterion("share_value like", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueNotLike(String value) {
            addCriterion("share_value not like", value, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueIn(List<String> values) {
            addCriterion("share_value in", values, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueNotIn(List<String> values) {
            addCriterion("share_value not in", values, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueBetween(String value1, String value2) {
            addCriterion("share_value between", value1, value2, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareValueNotBetween(String value1, String value2) {
            addCriterion("share_value not between", value1, value2, "shareValue");
            return (Criteria) this;
        }

        public Criteria andShareLabelIsNull() {
            addCriterion("share_label is null");
            return (Criteria) this;
        }

        public Criteria andShareLabelIsNotNull() {
            addCriterion("share_label is not null");
            return (Criteria) this;
        }

        public Criteria andShareLabelEqualTo(String value) {
            addCriterion("share_label =", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelNotEqualTo(String value) {
            addCriterion("share_label <>", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelGreaterThan(String value) {
            addCriterion("share_label >", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelGreaterThanOrEqualTo(String value) {
            addCriterion("share_label >=", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelLessThan(String value) {
            addCriterion("share_label <", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelLessThanOrEqualTo(String value) {
            addCriterion("share_label <=", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelLike(String value) {
            addCriterion("share_label like", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelNotLike(String value) {
            addCriterion("share_label not like", value, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelIn(List<String> values) {
            addCriterion("share_label in", values, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelNotIn(List<String> values) {
            addCriterion("share_label not in", values, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelBetween(String value1, String value2) {
            addCriterion("share_label between", value1, value2, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andShareLabelNotBetween(String value1, String value2) {
            addCriterion("share_label not between", value1, value2, "shareLabel");
            return (Criteria) this;
        }

        public Criteria andDetailIsNull() {
            addCriterion("detail is null");
            return (Criteria) this;
        }

        public Criteria andDetailIsNotNull() {
            addCriterion("detail is not null");
            return (Criteria) this;
        }

        public Criteria andDetailEqualTo(String value) {
            addCriterion("detail =", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotEqualTo(String value) {
            addCriterion("detail <>", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThan(String value) {
            addCriterion("detail >", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailGreaterThanOrEqualTo(String value) {
            addCriterion("detail >=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThan(String value) {
            addCriterion("detail <", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLessThanOrEqualTo(String value) {
            addCriterion("detail <=", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailLike(String value) {
            addCriterion("detail like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotLike(String value) {
            addCriterion("detail not like", value, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailIn(List<String> values) {
            addCriterion("detail in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotIn(List<String> values) {
            addCriterion("detail not in", values, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailBetween(String value1, String value2) {
            addCriterion("detail between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andDetailNotBetween(String value1, String value2) {
            addCriterion("detail not between", value1, value2, "detail");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("flag is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("flag is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Boolean value) {
            addCriterion("flag =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Boolean value) {
            addCriterion("flag <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Boolean value) {
            addCriterion("flag >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("flag >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Boolean value) {
            addCriterion("flag <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("flag <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Boolean> values) {
            addCriterion("flag in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Boolean> values) {
            addCriterion("flag not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("flag between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("flag not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNull() {
            addCriterion("modify_by is null");
            return (Criteria) this;
        }

        public Criteria andModifyByIsNotNull() {
            addCriterion("modify_by is not null");
            return (Criteria) this;
        }

        public Criteria andModifyByEqualTo(String value) {
            addCriterion("modify_by =", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotEqualTo(String value) {
            addCriterion("modify_by <>", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThan(String value) {
            addCriterion("modify_by >", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByGreaterThanOrEqualTo(String value) {
            addCriterion("modify_by >=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThan(String value) {
            addCriterion("modify_by <", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLessThanOrEqualTo(String value) {
            addCriterion("modify_by <=", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByLike(String value) {
            addCriterion("modify_by like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotLike(String value) {
            addCriterion("modify_by not like", value, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByIn(List<String> values) {
            addCriterion("modify_by in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotIn(List<String> values) {
            addCriterion("modify_by not in", values, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByBetween(String value1, String value2) {
            addCriterion("modify_by between", value1, value2, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyByNotBetween(String value1, String value2) {
            addCriterion("modify_by not between", value1, value2, "modifyBy");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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