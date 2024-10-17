package com.wr.common.security.business.validator;

import com.wr.common.security.business.annotation.FieldRepeat;
import com.wr.remote.domain.TableColumn;
import com.wr.remote.system.RemoteFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-08-23 16:08:18
 * @Desc: 校验规则
 */
public class FieldRepeatValidator implements ConstraintValidator<FieldRepeat, List<String>> {
    private static final Logger log = LoggerFactory.getLogger(FieldRepeatValidator.class);

    @Autowired
    private RemoteFieldService fieldService;

    /** 属性类 */
    private TableColumn tableColumn;
    /** 需要校验的属性 */
    private String[] fields;
    /** 对应的提示信息 */
    private String[] msgList;


    @Override
    public void initialize(FieldRepeat validator) {
        log.info("验证开始===>");
        this.tableColumn = new TableColumn(validator.tableName(), validator.idField(), validator.delField());
        this.fields = validator.fields();
        this.msgList = validator.msg();
        FieldRepeatValidatorHandler.init(fieldService);
    }

    @Override
    public boolean isValid(List<String> valueList, ConstraintValidatorContext context) {
        log.info("{}",valueList);
        return FieldRepeatValidatorHandler.isValid(valueList, this.fields, this.tableColumn, this.msgList, context);
    }

}
