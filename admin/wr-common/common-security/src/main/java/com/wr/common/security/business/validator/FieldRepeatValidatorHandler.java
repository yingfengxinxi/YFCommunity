package com.wr.common.security.business.validator;

import com.wr.common.core.domain.R;
import com.wr.remote.domain.TableColumn;
import com.wr.remote.system.RemoteFieldService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:52:03
 * @Desc:
 */
public class FieldRepeatValidatorHandler {
    private static final Logger log = LoggerFactory.getLogger(FieldRepeatValidatorHandler.class);
    private static final String ZERO = "0";

    private static RemoteFieldService fieldService;

    public static void init(RemoteFieldService fieldService){
        FieldRepeatValidatorHandler.fieldService = fieldService;
    }

    public static boolean isValid(List<String> valueList, String[] fields, TableColumn tableColumn, String[] msgList,
                                  ConstraintValidatorContext context){
        boolean flag = true;
        // 新增
        if(null == valueList.get(0) || ZERO.equals(valueList.get(0))){
            log.info("新增操作验证=====>");
            for (int i = 0; i < fields.length; i++) {
                flag = insert(tableColumn, fields[i], valueList.get(i+1));
                if(!flag){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(msgList[i]).addConstraintViolation();
                    break;
                }
            }
            return flag;
        }
        log.info("修改操作验证=====>");
        // 修改
        for (int i = 0; i < fields.length; i++) {
            flag = update(tableColumn, fields[i], valueList.get(i+1), valueList.get(0));
            if(!flag){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(msgList[i]).addConstraintViolation();
                break;
            }
        }
        return flag;
    }


    public static Boolean insert(TableColumn tableColumn, String field, String value){
        R<String> result = getPrimaryKeyResult(tableColumn, field, value);
        return StringUtils.isEmpty(result.getData()) || ZERO.equals(result.getData());
    }

    public static Boolean update(TableColumn tableColumn, String field, String value, String id){
        R<String> result = getPrimaryKeyResult(tableColumn, field, value);
        if(StringUtils.isEmpty(result.getData()) || ZERO.equals(result.getData())){
            return true;
        }
        return id.equals(result.getData());
    }

    private static R<String> getPrimaryKeyResult(TableColumn tableColumn, String field, String value){
        tableColumn.setFiled(field);
        tableColumn.setFieldValue(value);
        return fieldService.selectFiledId(tableColumn);
    }
}
