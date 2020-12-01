package com.mini.core.test.entity;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;

import lombok.*;
import lombok.experimental.*;

import java.io.Serializable;


@Data
@SuperBuilder(toBuilder = true)
@Table(TextInfo.TEXT_INFO)
public class TextInfo implements Serializable {

    //
    public static final String TEXT_INFO = "text_info";
    //
    public static final String TEXT_ID = "text_id";
    //
    public static final String TEXT_TITLE = "text_title";
    //
    public static final String TEXT_CONTENT = "text_content";

    // textId
    @Id
    @Column(TEXT_ID)
    private Long textId;

    // textTitle
    @Column(TEXT_TITLE)
    private String textTitle;

    // textContent
    @Column(TEXT_CONTENT)
    private String textContent;

    @Tolerate
    public TextInfo() {
    }


}
 
 
