package com.yl.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_users")
public class User implements Serializable {

    private static final long serialVersionUID = 4831304712151465443L;
    @TableField("id")
    private Long id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("status")
    private String status;

}
