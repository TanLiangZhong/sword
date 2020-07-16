package com.lz.sword.common.domain;

import com.lz.sword.common.constant.ResultMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 业务响应实体
 *
 * @author liangzhong.tan
 * @version 1.0 2020-4-1 16:33:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("业务响应")
public class RestVo<T> implements Serializable {

    @ApiModelProperty("返回编码")
    private String code;

    @ApiModelProperty("是否成功")
    private boolean success = true;

    @ApiModelProperty("返回消息")
    private String msg;

    @ApiModelProperty("返回结果")
    private T data;

    public RestVo(T data) {
        this.code = ResultMsg.SUCCESS.getCode();
        this.msg = ResultMsg.SUCCESS.getText();
        this.data = data;
    }

    public RestVo(ResultMsg resultMsg) {
        this.code = resultMsg.getCode();
        this.msg = resultMsg.getText();
    }

    public static <T> RestVo<T> success() {
        return new RestVo<>(ResultMsg.SUCCESS);
    }

    public static <T> RestVo<T> success(T data) {
        RestVo<T> restVo = new RestVo<>(ResultMsg.SUCCESS);
        restVo.setData(data);
        return restVo;
    }

    public static <T> RestVo<T> error() {
        RestVo<T> restVo = new RestVo<>(ResultMsg.ERROR);
        restVo.setSuccess(false);
        return restVo;
    }

    public static <T> RestVo<T> fail() {
        RestVo<T> restVo = new RestVo<>(ResultMsg.FAIL);
        restVo.setSuccess(false);
        return restVo;
    }

    public static <T> RestVo<T> fail(String msg) {
        RestVo<T> restVo = new RestVo<>(ResultMsg.FAIL);
        restVo.setMsg(msg);
        restVo.setSuccess(false);
        return restVo;
    }

    public static <T> RestVo<T> fail(ResultMsg resultMsg) {
        RestVo<T> restVo = new RestVo<>(resultMsg);
        restVo.setSuccess(false);
        return restVo;
    }

}
