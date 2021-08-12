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
@TableName("role_info")
public class RoleInfo implements Serializable  {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @TableId("role_id")
    private Long id;

    /**
    * 
    */
    @TableField("role_name")
    private String name;

}
