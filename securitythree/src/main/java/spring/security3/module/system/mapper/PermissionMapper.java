package spring.security3.module.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring.security3.module.system.entity.Permission;

import java.util.List;

/**
 * <p> 系统管理-权限表  Mapper 接口 </p>
 *
 * @author : zhengqing
 * @date : 2019-08-19
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色ID查询用户权限
     *
     * @param roleId:
     * @return: java.util.List<com.zhengqing.modules.system.entity.Menu>
     */
    @Select("SELECT sm.* FROM t_sys_permission sm\n" +
            "\t    LEFT JOIN t_sys_role_permission srm ON sm.id = srm.permission_id\n" +
            "\t    WHERE srm.role_id = #{roleId}")
    List<Permission> selectPermissionByRoleId(@Param("roleId") Integer roleId);

}
