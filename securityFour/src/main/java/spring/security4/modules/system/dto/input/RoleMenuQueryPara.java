package spring.security4.modules.system.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import spring.security4.modules.common.dto.input.BasePageQuery;

/**
 * 系统管理 - 角色-菜单关联表 查询参数
 *
 * @author: zhengqing
 * @description:
 * @date: 2019-08-20
 */
@Data
@ApiModel(description = "系统管理 - 角色-菜单关联表 查询参数")
public class RoleMenuQueryPara extends BasePageQuery {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;
    @ApiModelProperty(value = "菜单ids")
    private String menuIds;
}
