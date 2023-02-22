package com.yl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper //底层基于接口创建实现类
public interface UserMapper extends BaseMapper<User> {

    /**
     *基于用户名查询用户信息*/

    @Select("select id,username,password,status " +
            " from tb_users " +
            " where username = #{username} ")
    User selectUserByUsername(String username);

    /**基于用户名查询用户权限, 涉及到的表:
     * 1. tb_user_roles(用户角色关系表, 可以在此表中基于用户ID找到用户角色)
     * 2. tb_role_menus(用户菜单关系表, 可以基于角色ID找到菜单ID)
     * 3. tb_menus(菜单表, 菜单为资源的外在表现形式, 在此表中可以基于菜单ID找到权限标识
     * 基于如上三张表获取用户权限, 解决方案:
     * 1. 三次单表查询
     * 2. 嵌套查询
     * 3. 多表联查*/

    @Select("select distinct m.permission " +
            "from tb_user_roles ur join tb_role_menus rm on ur.role_id=rm.role_id" +
            "     join tb_menus m on rm.menu_id=m.id " +
            "where ur.user_id=#{userId}")
    List<String> selectUserPermissions(Long userId);

}
