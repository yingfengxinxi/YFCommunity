package com.wr.estate.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusOwnerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BusOwnerExample() {
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

        public Criteria andOwnerIdIsNull() {
            addCriterion("owner_id is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNotNull() {
            addCriterion("owner_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdEqualTo(Long value) {
            addCriterion("owner_id =", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotEqualTo(Long value) {
            addCriterion("owner_id <>", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThan(Long value) {
            addCriterion("owner_id >", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("owner_id >=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThan(Long value) {
            addCriterion("owner_id <", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThanOrEqualTo(Long value) {
            addCriterion("owner_id <=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIn(List<Long> values) {
            addCriterion("owner_id in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotIn(List<Long> values) {
            addCriterion("owner_id not in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdBetween(Long value1, Long value2) {
            addCriterion("owner_id between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotBetween(Long value1, Long value2) {
            addCriterion("owner_id not between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdIsNull() {
            addCriterion("community_id is null");
            return (Criteria) this;
        }

        public Criteria andCommunityIdIsNotNull() {
            addCriterion("community_id is not null");
            return (Criteria) this;
        }

        public Criteria andCommunityIdEqualTo(Long value) {
            addCriterion("community_id =", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdNotEqualTo(Long value) {
            addCriterion("community_id <>", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdGreaterThan(Long value) {
            addCriterion("community_id >", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("community_id >=", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdLessThan(Long value) {
            addCriterion("community_id <", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdLessThanOrEqualTo(Long value) {
            addCriterion("community_id <=", value, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdIn(List<Long> values) {
            addCriterion("community_id in", values, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdNotIn(List<Long> values) {
            addCriterion("community_id not in", values, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdBetween(Long value1, Long value2) {
            addCriterion("community_id between", value1, value2, "communityId");
            return (Criteria) this;
        }

        public Criteria andCommunityIdNotBetween(Long value1, Long value2) {
            addCriterion("community_id not between", value1, value2, "communityId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdIsNull() {
            addCriterion("building_id is null");
            return (Criteria) this;
        }

        public Criteria andBuildingIdIsNotNull() {
            addCriterion("building_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuildingIdEqualTo(Long value) {
            addCriterion("building_id =", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdNotEqualTo(Long value) {
            addCriterion("building_id <>", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdGreaterThan(Long value) {
            addCriterion("building_id >", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdGreaterThanOrEqualTo(Long value) {
            addCriterion("building_id >=", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdLessThan(Long value) {
            addCriterion("building_id <", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdLessThanOrEqualTo(Long value) {
            addCriterion("building_id <=", value, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdIn(List<Long> values) {
            addCriterion("building_id in", values, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdNotIn(List<Long> values) {
            addCriterion("building_id not in", values, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdBetween(Long value1, Long value2) {
            addCriterion("building_id between", value1, value2, "buildingId");
            return (Criteria) this;
        }

        public Criteria andBuildingIdNotBetween(Long value1, Long value2) {
            addCriterion("building_id not between", value1, value2, "buildingId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNull() {
            addCriterion("unit_id is null");
            return (Criteria) this;
        }

        public Criteria andUnitIdIsNotNull() {
            addCriterion("unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andUnitIdEqualTo(Long value) {
            addCriterion("unit_id =", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotEqualTo(Long value) {
            addCriterion("unit_id <>", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThan(Long value) {
            addCriterion("unit_id >", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdGreaterThanOrEqualTo(Long value) {
            addCriterion("unit_id >=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThan(Long value) {
            addCriterion("unit_id <", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdLessThanOrEqualTo(Long value) {
            addCriterion("unit_id <=", value, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdIn(List<Long> values) {
            addCriterion("unit_id in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotIn(List<Long> values) {
            addCriterion("unit_id not in", values, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdBetween(Long value1, Long value2) {
            addCriterion("unit_id between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andUnitIdNotBetween(Long value1, Long value2) {
            addCriterion("unit_id not between", value1, value2, "unitId");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNull() {
            addCriterion("room_id is null");
            return (Criteria) this;
        }

        public Criteria andRoomIdIsNotNull() {
            addCriterion("room_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoomIdEqualTo(Long value) {
            addCriterion("room_id =", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotEqualTo(Long value) {
            addCriterion("room_id <>", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThan(Long value) {
            addCriterion("room_id >", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdGreaterThanOrEqualTo(Long value) {
            addCriterion("room_id >=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThan(Long value) {
            addCriterion("room_id <", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdLessThanOrEqualTo(Long value) {
            addCriterion("room_id <=", value, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdIn(List<Long> values) {
            addCriterion("room_id in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotIn(List<Long> values) {
            addCriterion("room_id not in", values, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdBetween(Long value1, Long value2) {
            addCriterion("room_id between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andRoomIdNotBetween(Long value1, Long value2) {
            addCriterion("room_id not between", value1, value2, "roomId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Long value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Long value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Long value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Long value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Long value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Long value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Long> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Long> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Long value1, Long value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Long value1, Long value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationIsNull() {
            addCriterion("owner_relation is null");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationIsNotNull() {
            addCriterion("owner_relation is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationEqualTo(String value) {
            addCriterion("owner_relation =", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationNotEqualTo(String value) {
            addCriterion("owner_relation <>", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationGreaterThan(String value) {
            addCriterion("owner_relation >", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationGreaterThanOrEqualTo(String value) {
            addCriterion("owner_relation >=", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationLessThan(String value) {
            addCriterion("owner_relation <", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationLessThanOrEqualTo(String value) {
            addCriterion("owner_relation <=", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationLike(String value) {
            addCriterion("owner_relation like", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationNotLike(String value) {
            addCriterion("owner_relation not like", value, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationIn(List<String> values) {
            addCriterion("owner_relation in", values, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationNotIn(List<String> values) {
            addCriterion("owner_relation not in", values, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationBetween(String value1, String value2) {
            addCriterion("owner_relation between", value1, value2, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerRelationNotBetween(String value1, String value2) {
            addCriterion("owner_relation not between", value1, value2, "ownerRelation");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIsNull() {
            addCriterion("owner_name is null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIsNotNull() {
            addCriterion("owner_name is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameEqualTo(String value) {
            addCriterion("owner_name =", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotEqualTo(String value) {
            addCriterion("owner_name <>", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThan(String value) {
            addCriterion("owner_name >", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("owner_name >=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThan(String value) {
            addCriterion("owner_name <", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThanOrEqualTo(String value) {
            addCriterion("owner_name <=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLike(String value) {
            addCriterion("owner_name like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotLike(String value) {
            addCriterion("owner_name not like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIn(List<String> values) {
            addCriterion("owner_name in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotIn(List<String> values) {
            addCriterion("owner_name not in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameBetween(String value1, String value2) {
            addCriterion("owner_name between", value1, value2, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotBetween(String value1, String value2) {
            addCriterion("owner_name not between", value1, value2, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneIsNull() {
            addCriterion("owner_phone is null");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneIsNotNull() {
            addCriterion("owner_phone is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneEqualTo(String value) {
            addCriterion("owner_phone =", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneNotEqualTo(String value) {
            addCriterion("owner_phone <>", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneGreaterThan(String value) {
            addCriterion("owner_phone >", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("owner_phone >=", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneLessThan(String value) {
            addCriterion("owner_phone <", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneLessThanOrEqualTo(String value) {
            addCriterion("owner_phone <=", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneLike(String value) {
            addCriterion("owner_phone like", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneNotLike(String value) {
            addCriterion("owner_phone not like", value, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneIn(List<String> values) {
            addCriterion("owner_phone in", values, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneNotIn(List<String> values) {
            addCriterion("owner_phone not in", values, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneBetween(String value1, String value2) {
            addCriterion("owner_phone between", value1, value2, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andOwnerPhoneNotBetween(String value1, String value2) {
            addCriterion("owner_phone not between", value1, value2, "ownerPhone");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("card_type like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("card_type not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("card_no is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("card_no is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("card_no =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("card_no <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("card_no >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("card_no >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("card_no <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("card_no <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("card_no like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("card_no not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("card_no in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("card_no not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("card_no between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("card_no not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardFontIsNull() {
            addCriterion("card_font is null");
            return (Criteria) this;
        }

        public Criteria andCardFontIsNotNull() {
            addCriterion("card_font is not null");
            return (Criteria) this;
        }

        public Criteria andCardFontEqualTo(String value) {
            addCriterion("card_font =", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontNotEqualTo(String value) {
            addCriterion("card_font <>", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontGreaterThan(String value) {
            addCriterion("card_font >", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontGreaterThanOrEqualTo(String value) {
            addCriterion("card_font >=", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontLessThan(String value) {
            addCriterion("card_font <", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontLessThanOrEqualTo(String value) {
            addCriterion("card_font <=", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontLike(String value) {
            addCriterion("card_font like", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontNotLike(String value) {
            addCriterion("card_font not like", value, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontIn(List<String> values) {
            addCriterion("card_font in", values, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontNotIn(List<String> values) {
            addCriterion("card_font not in", values, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontBetween(String value1, String value2) {
            addCriterion("card_font between", value1, value2, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardFontNotBetween(String value1, String value2) {
            addCriterion("card_font not between", value1, value2, "cardFont");
            return (Criteria) this;
        }

        public Criteria andCardBackIsNull() {
            addCriterion("card_back is null");
            return (Criteria) this;
        }

        public Criteria andCardBackIsNotNull() {
            addCriterion("card_back is not null");
            return (Criteria) this;
        }

        public Criteria andCardBackEqualTo(String value) {
            addCriterion("card_back =", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackNotEqualTo(String value) {
            addCriterion("card_back <>", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackGreaterThan(String value) {
            addCriterion("card_back >", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackGreaterThanOrEqualTo(String value) {
            addCriterion("card_back >=", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackLessThan(String value) {
            addCriterion("card_back <", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackLessThanOrEqualTo(String value) {
            addCriterion("card_back <=", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackLike(String value) {
            addCriterion("card_back like", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackNotLike(String value) {
            addCriterion("card_back not like", value, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackIn(List<String> values) {
            addCriterion("card_back in", values, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackNotIn(List<String> values) {
            addCriterion("card_back not in", values, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackBetween(String value1, String value2) {
            addCriterion("card_back between", value1, value2, "cardBack");
            return (Criteria) this;
        }

        public Criteria andCardBackNotBetween(String value1, String value2) {
            addCriterion("card_back not between", value1, value2, "cardBack");
            return (Criteria) this;
        }

        public Criteria andFaceUrlIsNull() {
            addCriterion("face_url is null");
            return (Criteria) this;
        }

        public Criteria andFaceUrlIsNotNull() {
            addCriterion("face_url is not null");
            return (Criteria) this;
        }

        public Criteria andFaceUrlEqualTo(String value) {
            addCriterion("face_url =", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlNotEqualTo(String value) {
            addCriterion("face_url <>", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlGreaterThan(String value) {
            addCriterion("face_url >", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("face_url >=", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlLessThan(String value) {
            addCriterion("face_url <", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlLessThanOrEqualTo(String value) {
            addCriterion("face_url <=", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlLike(String value) {
            addCriterion("face_url like", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlNotLike(String value) {
            addCriterion("face_url not like", value, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlIn(List<String> values) {
            addCriterion("face_url in", values, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlNotIn(List<String> values) {
            addCriterion("face_url not in", values, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlBetween(String value1, String value2) {
            addCriterion("face_url between", value1, value2, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andFaceUrlNotBetween(String value1, String value2) {
            addCriterion("face_url not between", value1, value2, "faceUrl");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressIsNull() {
            addCriterion("domicile_address is null");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressIsNotNull() {
            addCriterion("domicile_address is not null");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressEqualTo(String value) {
            addCriterion("domicile_address =", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressNotEqualTo(String value) {
            addCriterion("domicile_address <>", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressGreaterThan(String value) {
            addCriterion("domicile_address >", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressGreaterThanOrEqualTo(String value) {
            addCriterion("domicile_address >=", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressLessThan(String value) {
            addCriterion("domicile_address <", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressLessThanOrEqualTo(String value) {
            addCriterion("domicile_address <=", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressLike(String value) {
            addCriterion("domicile_address like", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressNotLike(String value) {
            addCriterion("domicile_address not like", value, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressIn(List<String> values) {
            addCriterion("domicile_address in", values, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressNotIn(List<String> values) {
            addCriterion("domicile_address not in", values, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressBetween(String value1, String value2) {
            addCriterion("domicile_address between", value1, value2, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andDomicileAddressNotBetween(String value1, String value2) {
            addCriterion("domicile_address not between", value1, value2, "domicileAddress");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusIsNull() {
            addCriterion("politics_status is null");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusIsNotNull() {
            addCriterion("politics_status is not null");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusEqualTo(String value) {
            addCriterion("politics_status =", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusNotEqualTo(String value) {
            addCriterion("politics_status <>", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusGreaterThan(String value) {
            addCriterion("politics_status >", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusGreaterThanOrEqualTo(String value) {
            addCriterion("politics_status >=", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusLessThan(String value) {
            addCriterion("politics_status <", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusLessThanOrEqualTo(String value) {
            addCriterion("politics_status <=", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusLike(String value) {
            addCriterion("politics_status like", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusNotLike(String value) {
            addCriterion("politics_status not like", value, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusIn(List<String> values) {
            addCriterion("politics_status in", values, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusNotIn(List<String> values) {
            addCriterion("politics_status not in", values, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusBetween(String value1, String value2) {
            addCriterion("politics_status between", value1, value2, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andPoliticsStatusNotBetween(String value1, String value2) {
            addCriterion("politics_status not between", value1, value2, "politicsStatus");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(String value) {
            addCriterion("nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(String value) {
            addCriterion("nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(String value) {
            addCriterion("nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(String value) {
            addCriterion("nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(String value) {
            addCriterion("nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(String value) {
            addCriterion("nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLike(String value) {
            addCriterion("nation like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotLike(String value) {
            addCriterion("nation not like", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<String> values) {
            addCriterion("nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<String> values) {
            addCriterion("nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(String value1, String value2) {
            addCriterion("nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(String value1, String value2) {
            addCriterion("nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNull() {
            addCriterion("native_place is null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIsNotNull() {
            addCriterion("native_place is not null");
            return (Criteria) this;
        }

        public Criteria andNativePlaceEqualTo(String value) {
            addCriterion("native_place =", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotEqualTo(String value) {
            addCriterion("native_place <>", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThan(String value) {
            addCriterion("native_place >", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceGreaterThanOrEqualTo(String value) {
            addCriterion("native_place >=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThan(String value) {
            addCriterion("native_place <", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLessThanOrEqualTo(String value) {
            addCriterion("native_place <=", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceLike(String value) {
            addCriterion("native_place like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotLike(String value) {
            addCriterion("native_place not like", value, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceIn(List<String> values) {
            addCriterion("native_place in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotIn(List<String> values) {
            addCriterion("native_place not in", values, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceBetween(String value1, String value2) {
            addCriterion("native_place between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andNativePlaceNotBetween(String value1, String value2) {
            addCriterion("native_place not between", value1, value2, "nativePlace");
            return (Criteria) this;
        }

        public Criteria andReligionIsNull() {
            addCriterion("religion is null");
            return (Criteria) this;
        }

        public Criteria andReligionIsNotNull() {
            addCriterion("religion is not null");
            return (Criteria) this;
        }

        public Criteria andReligionEqualTo(String value) {
            addCriterion("religion =", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionNotEqualTo(String value) {
            addCriterion("religion <>", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionGreaterThan(String value) {
            addCriterion("religion >", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionGreaterThanOrEqualTo(String value) {
            addCriterion("religion >=", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionLessThan(String value) {
            addCriterion("religion <", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionLessThanOrEqualTo(String value) {
            addCriterion("religion <=", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionLike(String value) {
            addCriterion("religion like", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionNotLike(String value) {
            addCriterion("religion not like", value, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionIn(List<String> values) {
            addCriterion("religion in", values, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionNotIn(List<String> values) {
            addCriterion("religion not in", values, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionBetween(String value1, String value2) {
            addCriterion("religion between", value1, value2, "religion");
            return (Criteria) this;
        }

        public Criteria andReligionNotBetween(String value1, String value2) {
            addCriterion("religion not between", value1, value2, "religion");
            return (Criteria) this;
        }

        public Criteria andEduLevelIsNull() {
            addCriterion("edu_level is null");
            return (Criteria) this;
        }

        public Criteria andEduLevelIsNotNull() {
            addCriterion("edu_level is not null");
            return (Criteria) this;
        }

        public Criteria andEduLevelEqualTo(String value) {
            addCriterion("edu_level =", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotEqualTo(String value) {
            addCriterion("edu_level <>", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThan(String value) {
            addCriterion("edu_level >", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelGreaterThanOrEqualTo(String value) {
            addCriterion("edu_level >=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThan(String value) {
            addCriterion("edu_level <", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLessThanOrEqualTo(String value) {
            addCriterion("edu_level <=", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelLike(String value) {
            addCriterion("edu_level like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotLike(String value) {
            addCriterion("edu_level not like", value, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelIn(List<String> values) {
            addCriterion("edu_level in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotIn(List<String> values) {
            addCriterion("edu_level not in", values, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelBetween(String value1, String value2) {
            addCriterion("edu_level between", value1, value2, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andEduLevelNotBetween(String value1, String value2) {
            addCriterion("edu_level not between", value1, value2, "eduLevel");
            return (Criteria) this;
        }

        public Criteria andMilitaryIsNull() {
            addCriterion("military is null");
            return (Criteria) this;
        }

        public Criteria andMilitaryIsNotNull() {
            addCriterion("military is not null");
            return (Criteria) this;
        }

        public Criteria andMilitaryEqualTo(String value) {
            addCriterion("military =", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotEqualTo(String value) {
            addCriterion("military <>", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryGreaterThan(String value) {
            addCriterion("military >", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryGreaterThanOrEqualTo(String value) {
            addCriterion("military >=", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryLessThan(String value) {
            addCriterion("military <", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryLessThanOrEqualTo(String value) {
            addCriterion("military <=", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryLike(String value) {
            addCriterion("military like", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotLike(String value) {
            addCriterion("military not like", value, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryIn(List<String> values) {
            addCriterion("military in", values, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotIn(List<String> values) {
            addCriterion("military not in", values, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryBetween(String value1, String value2) {
            addCriterion("military between", value1, value2, "military");
            return (Criteria) this;
        }

        public Criteria andMilitaryNotBetween(String value1, String value2) {
            addCriterion("military not between", value1, value2, "military");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusIsNull() {
            addCriterion("marriage_status is null");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusIsNotNull() {
            addCriterion("marriage_status is not null");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusEqualTo(String value) {
            addCriterion("marriage_status =", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusNotEqualTo(String value) {
            addCriterion("marriage_status <>", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusGreaterThan(String value) {
            addCriterion("marriage_status >", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusGreaterThanOrEqualTo(String value) {
            addCriterion("marriage_status >=", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusLessThan(String value) {
            addCriterion("marriage_status <", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusLessThanOrEqualTo(String value) {
            addCriterion("marriage_status <=", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusLike(String value) {
            addCriterion("marriage_status like", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusNotLike(String value) {
            addCriterion("marriage_status not like", value, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusIn(List<String> values) {
            addCriterion("marriage_status in", values, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusNotIn(List<String> values) {
            addCriterion("marriage_status not in", values, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusBetween(String value1, String value2) {
            addCriterion("marriage_status between", value1, value2, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andMarriageStatusNotBetween(String value1, String value2) {
            addCriterion("marriage_status not between", value1, value2, "marriageStatus");
            return (Criteria) this;
        }

        public Criteria andNationalityIsNull() {
            addCriterion("nationality is null");
            return (Criteria) this;
        }

        public Criteria andNationalityIsNotNull() {
            addCriterion("nationality is not null");
            return (Criteria) this;
        }

        public Criteria andNationalityEqualTo(String value) {
            addCriterion("nationality =", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotEqualTo(String value) {
            addCriterion("nationality <>", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityGreaterThan(String value) {
            addCriterion("nationality >", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityGreaterThanOrEqualTo(String value) {
            addCriterion("nationality >=", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLessThan(String value) {
            addCriterion("nationality <", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLessThanOrEqualTo(String value) {
            addCriterion("nationality <=", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityLike(String value) {
            addCriterion("nationality like", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotLike(String value) {
            addCriterion("nationality not like", value, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityIn(List<String> values) {
            addCriterion("nationality in", values, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotIn(List<String> values) {
            addCriterion("nationality not in", values, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityBetween(String value1, String value2) {
            addCriterion("nationality between", value1, value2, "nationality");
            return (Criteria) this;
        }

        public Criteria andNationalityNotBetween(String value1, String value2) {
            addCriterion("nationality not between", value1, value2, "nationality");
            return (Criteria) this;
        }

        public Criteria andPopulationIsNull() {
            addCriterion("population is null");
            return (Criteria) this;
        }

        public Criteria andPopulationIsNotNull() {
            addCriterion("population is not null");
            return (Criteria) this;
        }

        public Criteria andPopulationEqualTo(String value) {
            addCriterion("population =", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationNotEqualTo(String value) {
            addCriterion("population <>", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationGreaterThan(String value) {
            addCriterion("population >", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationGreaterThanOrEqualTo(String value) {
            addCriterion("population >=", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationLessThan(String value) {
            addCriterion("population <", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationLessThanOrEqualTo(String value) {
            addCriterion("population <=", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationLike(String value) {
            addCriterion("population like", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationNotLike(String value) {
            addCriterion("population not like", value, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationIn(List<String> values) {
            addCriterion("population in", values, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationNotIn(List<String> values) {
            addCriterion("population not in", values, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationBetween(String value1, String value2) {
            addCriterion("population between", value1, value2, "population");
            return (Criteria) this;
        }

        public Criteria andPopulationNotBetween(String value1, String value2) {
            addCriterion("population not between", value1, value2, "population");
            return (Criteria) this;
        }

        public Criteria andCareerIsNull() {
            addCriterion("career is null");
            return (Criteria) this;
        }

        public Criteria andCareerIsNotNull() {
            addCriterion("career is not null");
            return (Criteria) this;
        }

        public Criteria andCareerEqualTo(String value) {
            addCriterion("career =", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerNotEqualTo(String value) {
            addCriterion("career <>", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerGreaterThan(String value) {
            addCriterion("career >", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerGreaterThanOrEqualTo(String value) {
            addCriterion("career >=", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerLessThan(String value) {
            addCriterion("career <", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerLessThanOrEqualTo(String value) {
            addCriterion("career <=", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerLike(String value) {
            addCriterion("career like", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerNotLike(String value) {
            addCriterion("career not like", value, "career");
            return (Criteria) this;
        }

        public Criteria andCareerIn(List<String> values) {
            addCriterion("career in", values, "career");
            return (Criteria) this;
        }

        public Criteria andCareerNotIn(List<String> values) {
            addCriterion("career not in", values, "career");
            return (Criteria) this;
        }

        public Criteria andCareerBetween(String value1, String value2) {
            addCriterion("career between", value1, value2, "career");
            return (Criteria) this;
        }

        public Criteria andCareerNotBetween(String value1, String value2) {
            addCriterion("career not between", value1, value2, "career");
            return (Criteria) this;
        }

        public Criteria andQqNumberIsNull() {
            addCriterion("qq_number is null");
            return (Criteria) this;
        }

        public Criteria andQqNumberIsNotNull() {
            addCriterion("qq_number is not null");
            return (Criteria) this;
        }

        public Criteria andQqNumberEqualTo(String value) {
            addCriterion("qq_number =", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberNotEqualTo(String value) {
            addCriterion("qq_number <>", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberGreaterThan(String value) {
            addCriterion("qq_number >", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberGreaterThanOrEqualTo(String value) {
            addCriterion("qq_number >=", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberLessThan(String value) {
            addCriterion("qq_number <", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberLessThanOrEqualTo(String value) {
            addCriterion("qq_number <=", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberLike(String value) {
            addCriterion("qq_number like", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberNotLike(String value) {
            addCriterion("qq_number not like", value, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberIn(List<String> values) {
            addCriterion("qq_number in", values, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberNotIn(List<String> values) {
            addCriterion("qq_number not in", values, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberBetween(String value1, String value2) {
            addCriterion("qq_number between", value1, value2, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andQqNumberNotBetween(String value1, String value2) {
            addCriterion("qq_number not between", value1, value2, "qqNumber");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNull() {
            addCriterion("data_source is null");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNotNull() {
            addCriterion("data_source is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourceEqualTo(String value) {
            addCriterion("data_source =", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotEqualTo(String value) {
            addCriterion("data_source <>", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThan(String value) {
            addCriterion("data_source >", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThanOrEqualTo(String value) {
            addCriterion("data_source >=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThan(String value) {
            addCriterion("data_source <", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThanOrEqualTo(String value) {
            addCriterion("data_source <=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLike(String value) {
            addCriterion("data_source like", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotLike(String value) {
            addCriterion("data_source not like", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceIn(List<String> values) {
            addCriterion("data_source in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotIn(List<String> values) {
            addCriterion("data_source not in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceBetween(String value1, String value2) {
            addCriterion("data_source between", value1, value2, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotBetween(String value1, String value2) {
            addCriterion("data_source not between", value1, value2, "dataSource");
            return (Criteria) this;
        }

        public Criteria andSelfIntroIsNull() {
            addCriterion("self_intro is null");
            return (Criteria) this;
        }

        public Criteria andSelfIntroIsNotNull() {
            addCriterion("self_intro is not null");
            return (Criteria) this;
        }

        public Criteria andSelfIntroEqualTo(String value) {
            addCriterion("self_intro =", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroNotEqualTo(String value) {
            addCriterion("self_intro <>", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroGreaterThan(String value) {
            addCriterion("self_intro >", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroGreaterThanOrEqualTo(String value) {
            addCriterion("self_intro >=", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroLessThan(String value) {
            addCriterion("self_intro <", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroLessThanOrEqualTo(String value) {
            addCriterion("self_intro <=", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroLike(String value) {
            addCriterion("self_intro like", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroNotLike(String value) {
            addCriterion("self_intro not like", value, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroIn(List<String> values) {
            addCriterion("self_intro in", values, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroNotIn(List<String> values) {
            addCriterion("self_intro not in", values, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroBetween(String value1, String value2) {
            addCriterion("self_intro between", value1, value2, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andSelfIntroNotBetween(String value1, String value2) {
            addCriterion("self_intro not between", value1, value2, "selfIntro");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(String value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(String value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(String value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(String value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(String value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(String value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLike(String value) {
            addCriterion("audit_status like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotLike(String value) {
            addCriterion("audit_status not like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<String> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<String> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(String value1, String value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(String value1, String value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNull() {
            addCriterion("audit_time is null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIsNotNull() {
            addCriterion("audit_time is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTimeEqualTo(Date value) {
            addCriterion("audit_time =", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotEqualTo(Date value) {
            addCriterion("audit_time <>", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThan(Date value) {
            addCriterion("audit_time >", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("audit_time >=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThan(Date value) {
            addCriterion("audit_time <", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeLessThanOrEqualTo(Date value) {
            addCriterion("audit_time <=", value, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeIn(List<Date> values) {
            addCriterion("audit_time in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotIn(List<Date> values) {
            addCriterion("audit_time not in", values, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeBetween(Date value1, Date value2) {
            addCriterion("audit_time between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andAuditTimeNotBetween(Date value1, Date value2) {
            addCriterion("audit_time not between", value1, value2, "auditTime");
            return (Criteria) this;
        }

        public Criteria andRejectReasonIsNull() {
            addCriterion("reject_reason is null");
            return (Criteria) this;
        }

        public Criteria andRejectReasonIsNotNull() {
            addCriterion("reject_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRejectReasonEqualTo(String value) {
            addCriterion("reject_reason =", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonNotEqualTo(String value) {
            addCriterion("reject_reason <>", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonGreaterThan(String value) {
            addCriterion("reject_reason >", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reject_reason >=", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonLessThan(String value) {
            addCriterion("reject_reason <", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonLessThanOrEqualTo(String value) {
            addCriterion("reject_reason <=", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonLike(String value) {
            addCriterion("reject_reason like", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonNotLike(String value) {
            addCriterion("reject_reason not like", value, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonIn(List<String> values) {
            addCriterion("reject_reason in", values, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonNotIn(List<String> values) {
            addCriterion("reject_reason not in", values, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonBetween(String value1, String value2) {
            addCriterion("reject_reason between", value1, value2, "rejectReason");
            return (Criteria) this;
        }

        public Criteria andRejectReasonNotBetween(String value1, String value2) {
            addCriterion("reject_reason not between", value1, value2, "rejectReason");
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

        public Criteria andUpdateByIsNull() {
            addCriterion("update_by is null");
            return (Criteria) this;
        }

        public Criteria andUpdateByIsNotNull() {
            addCriterion("update_by is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateByEqualTo(String value) {
            addCriterion("update_by =", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotEqualTo(String value) {
            addCriterion("update_by <>", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThan(String value) {
            addCriterion("update_by >", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByGreaterThanOrEqualTo(String value) {
            addCriterion("update_by >=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThan(String value) {
            addCriterion("update_by <", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLessThanOrEqualTo(String value) {
            addCriterion("update_by <=", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByLike(String value) {
            addCriterion("update_by like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotLike(String value) {
            addCriterion("update_by not like", value, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByIn(List<String> values) {
            addCriterion("update_by in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotIn(List<String> values) {
            addCriterion("update_by not in", values, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByBetween(String value1, String value2) {
            addCriterion("update_by between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateByNotBetween(String value1, String value2) {
            addCriterion("update_by not between", value1, value2, "updateBy");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
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