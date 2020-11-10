package com.mini.core.mvc.test.controller;

import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.model.PageModel;
import com.mini.core.mvc.model.StreamModel;
import com.mini.core.mvc.test.entity.UserInfo;
import com.mini.core.mvc.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(path = "page")
    public ModelAndView page(PageModel model, UserInfo userInfo) {
        System.out.println("---------page-----------");

        return model.build();
    }

    @RequestMapping(path = "/json")
    @Transactional(rollbackFor = Throwable.class)
    public void json(JsonModel model, UserInfo userInfo) {
        model.setData(userRepository.findAll(PageRequest.of(0, 10)));



//        UserInfo user = UserInfo.builder()
//                .id(PKGenerator.id())
//                .age(20)
//                .fullName("FUll Name")
//                .name("Name")
//                .email("Email")
//                .createTime(new Date())
//                .build();
//        userRepository.insert(user);
//        System.out.println(user);
//
//        var list = userRepository.find();
//        System.out.println(list);

        // return model.build();
    }

    @RequestMapping(path = "stream")
    public ResponseEntity<Resource> stream(StreamModel model, UserInfo userInfo) {
        System.out.println("---------stream-----------");
        return model.build();
    }
}
