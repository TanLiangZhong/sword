package com.lz.sword.common.domain.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 @version 1.0: 2020/5/6-16:40
 @Author Genie
 @Description:
 */
@Data
@ApiModel("客户信息-Bo")
public class CustomerBo {

	@ApiModelProperty("关键字")
	private String keyword;

}
