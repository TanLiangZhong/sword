package com.lz.sword.common.exception;

import com.lz.sword.common.constant.ResultMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @author liangzhong.tan
 * @version 1.0 2020/4/4 12:10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private ResultMsg resultMsg;

    public BusinessException(ResultMsg result) {
        super(result.getText());
        this.resultMsg = result;
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }

    public BusinessException(ResultMsg result, String message) {
        super(message);
        this.resultMsg = result;
    }

    public static BusinessException of(ResultMsg msg) {
        return new BusinessException(msg);
    }

    public static BusinessException of(String message) {
        return new BusinessException(ResultMsg.FAIL, message);
    }

}
