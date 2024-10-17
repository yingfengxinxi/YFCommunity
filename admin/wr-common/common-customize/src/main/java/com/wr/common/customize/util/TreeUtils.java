package com.wr.common.customize.util;

import com.wr.common.core.business.annotation.ChildrenKey;
import com.wr.common.core.business.annotation.ParentKey;
import com.wr.common.core.business.annotation.PrimaryKey;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-06-06 14:08:26
 * @Desc: 构建树结构工具类
 */
public class TreeUtils extends BaseUtils {

    /**
     * 构建前端所需要树结构，主键为 Long 时
     * @param tList 数据集
     * @return java.util.List<T> 树结构列表
     */
    public static <T> List<T> buildLongTree(List<T> tList) {
        try {
            List<T> returnList = new ArrayList<>();
            //主键id集合
            List<Long> tempList = new ArrayList<>();
            for (T t : tList) {
                Long primaryId = (Long) AnnotationUtil.getFieldValue(t, PrimaryKey.class);
                tempList.add(primaryId);
            }
            for (T t : tList) {
                // 如果是顶级节点, 遍历该父节点的所有子节点
                Long parentId = (Long) AnnotationUtil.getFieldValue(t, ParentKey.class);
                if (!tempList.contains(parentId)) {
                    recursionLong(tList, t);
                    returnList.add(t);
                }
            }
            if (returnList.isEmpty()) {
                returnList = tList;
            }
            return returnList;
        } catch (Exception e) {
            log.error("树结构转换失败：{}", e.getMessage());
            return tList;
        }
    }

    /**
     * 构建前端所需要树结构，主键为 String 时
     * @param tList 数据集
     * @return java.util.List<T> 树结构列表
     */
    public static <T> List<T> buildStringTree(List<T> tList) {
        try {
            List<T> returnList = new ArrayList<>();
            List<String> tempList = new ArrayList<>();
            for (T t : tList) {
                String primaryId = (String) AnnotationUtil.getFieldValue(t, PrimaryKey.class);
                tempList.add(primaryId);
            }
            for (T t : tList) {
                // 如果是顶级节点, 遍历该父节点的所有子节点
                String parentId = (String) AnnotationUtil.getFieldValue(t, ParentKey.class);
                if (!tempList.contains(parentId)) {
                    recursionString(tList, t);
                    returnList.add(t);
                }
            }
            if (returnList.isEmpty()) {
                returnList = tList;
            }
            return returnList;
        } catch (IllegalAccessException e) {
            log.error("树结构转换失败：{}", e.getMessage());
            return tList;
        }
    }

    /**
     * 递归设置子集数据，主键为 Long 时
     * @param list 数据集合
     * @param o 对象
     */
    private static <T> void recursionLong(List<T> list, Object o) throws IllegalAccessException {
        // 得到子节点列表
        List<T> childList = getLongChildList(list, o);
        invokeChildrenList(o, childList);

        for (Object oChild : childList) {
            if (getLongChildList(list, oChild).size() > 0) {
                recursionLong(list, oChild);
            }
        }
    }

    /**
     * 递归设置子集数据，主键为 String 时
     * @param list 数据集合
     * @param o 对象
     */
    private static <T> void recursionString(List<T> list, Object o) throws IllegalAccessException {
        // 得到子节点列表
        List<T> childList = getStringChildList(list, o);
        invokeChildrenList(o, childList);

        for (Object oChild : childList) {
            if (getStringChildList(list, oChild).size() > 0) {
                recursionString(list, oChild);
            }
        }
    }

    /**
     * 得到子节点列表，主键为 Long 时
     * @param list 数据
     * @param object entity
     * @return java.util.List<T>
     */
    private static <T> List<T> getLongChildList(List<T> list, Object object) throws IllegalAccessException {
        Long primaryId = (Long) AnnotationUtil.getFieldValue(object, PrimaryKey.class);

        List<T> objects = new ArrayList<>();
        for (T o : list) {
            Long parentId = (Long) AnnotationUtil.getFieldValue(o, ParentKey.class);
            if (StringUtils.isNotNull(parentId) && parentId.longValue() == primaryId.longValue()) {
                objects.add(o);
            }
        }
        return objects;
    }

    /**
     * 得到子节点列表，主键为 String 时
     * @param list 数据
     * @param object entity
     * @return java.util.List<T>
     */
    private static <T> List<T> getStringChildList(List<T> list, Object object) throws IllegalAccessException {
        String primaryId = (String) AnnotationUtil.getFieldValue(object, PrimaryKey.class);

        List<T> objects = new ArrayList<>();
        for (T o : list) {
            String parentId = (String) AnnotationUtil.getFieldValue(o, ParentKey.class);
            if (StringUtils.isNotNull(parentId) && parentId.equals(primaryId)) {
                objects.add(o);
            }
        }
        return objects;
    }

    /**
     * 通过反射设置子集数据
     * @param o 对象
     * @param childList 子集数据
     */
    private static <T> void invokeChildrenList(Object o, List<T> childList) {
        Class<?> clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(ChildrenKey.class)){
                field.setAccessible(true);
                ReflectUtils.invokeSetter(o, field.getName(), childList);
                break;
            }
        }
    }

}
