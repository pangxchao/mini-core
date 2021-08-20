package com.mini.core.test.domain;

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
@TableName("region_info")
public class RegionInfo implements Serializable  {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @TableId("region_id")
    private Long id;

    /**
    * 
    */
    @TableField("region_name")
    private String name;

    /**
    * 
    */
    @TableField("region_parent_id")
    private Long parentId;

}
