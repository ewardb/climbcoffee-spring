package spring.security4.modules.system.dto.output;

import com.google.common.collect.Lists;
import lombok.Data;
import spring.security4.modules.system.entity.Menu;

import java.util.List;

/**
 *  <p> 菜单树节点 </p>
 *
 * @description :
 * @author : zhengqing
 * @date : 2019/8/19 18:54
 */
@Data
public class MenuTreeNode extends Menu {

    List<MenuTreeNode> children = Lists.newArrayList();

}
