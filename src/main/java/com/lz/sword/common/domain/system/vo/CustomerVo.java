package com.lz.sword.common.domain.system.vo;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 @version 1.0: 2020/5/6-16:55
 @Author Genie
 @Description: 客户信息
 */
@Data
@ApiModel("客户信息-Vo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerVo {

	@ApiModelProperty("客户ID")
	private Long customerId;

	@ApiModelProperty("客户名称")
	private String name;

	@ApiModelProperty("客户地址")
	private String address;

	@ApiModelProperty("联系方式")
	private String telephony;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("创建时间")
	private LocalDateTime gmtCreated;

	@ApiModelProperty("创建人")
	private Long creator;

	@ApiModelProperty("更新时间")
	private LocalDateTime gmtModified;

	@ApiModelProperty("更新人")
	private Long updater;

}
