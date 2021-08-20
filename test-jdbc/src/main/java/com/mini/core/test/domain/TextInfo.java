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
@TableName("text_info")
public class TextInfo implements Serializable  {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @TableId("text_id")
    private Long id;

    /**
    * 
    */
    @TableField("text_title")
    private String title;

    /**
    * 
    */
    @TableField("text_content")
    private String content;

}
