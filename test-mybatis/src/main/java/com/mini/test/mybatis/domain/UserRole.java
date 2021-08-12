package com.mini.test.mybatis.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


/**
* 
* @author pangchao
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserRole implements Serializable  {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @TableId("user_role_id")
    private Long id;

    /**
    * 
    */
    @TableField("user_role_user_id")
    private Long userId;

    /**
    * 
    */
    @TableField("user_role_role_id")
    private Long roleId;

}
