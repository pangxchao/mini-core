package com.mini.core.test.controller;

import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.util.ResponseCode;
import com.mini.core.test.repository.TextInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/text")
public class TextController   {
    private final TextInfoRepository textInfoRepository;

    @Autowired
    public TextController( TextInfoRepository textInfoRepository) {
        this.textInfoRepository = textInfoRepository;
    }

    // 测试得出结论：
    // 有大字段时，SQL中查询有大字段，但结果集中不处理的，会节省很多效率，估计主要来自硬盘
    // SQL中没有大字段的比SQL中有大字段但结果集不处理的也会节省很多效率
    @RequestMapping(path = "test")
    public ResponseEntity<ModelMap> test(JsonModel model) {
//        System.out.println();
//        System.out.println("------------------------------");
//        long start = System.currentTimeMillis();
//        var l1 = textInfoRepository.queryAll_All();
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);
//        start = end;
//        var l2 = textInfoRepository.queryAll_CON();
//        end = System.currentTimeMillis();
//        System.out.println(end - start);
//        start = end;
//        var l3 = textInfoRepository.queryAll_TIT();
//        end = System.currentTimeMillis();
//        System.out.println(end - start);

        return model.build();
    }
}
