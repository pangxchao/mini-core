package com.mini.core.test.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;


@Data
@Table("text_info")
public class TextInfo implements Serializable {
    // textId
    @Id
    @Column("text_id")
    private Long id;

    // textTitle
    @Column("text_title")
    private String title;

    // textContent
    @Column("text_content")
    private String content;

}
 
 
