package com.mini.test.mybatis.controller;

import com.mini.core.mvc.model.JsonModel;
import com.mini.test.mybatis.entity.UserInfo;
import com.mini.test.mybatis.form.UserSave;
import com.mini.test.mybatis.mapper.UserInfoMapper;
import com.mini.test.mybatis.mapper.UserRoleMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Locale;

@Primary
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserInfoMapper userInfoMapper;
    private final UserRoleMapper userRoleMapper;

    public UserController(UserInfoMapper userInfoMapper, UserRoleMapper userRoleMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @RequestMapping(path = "/list")
    public ResponseEntity<ModelMap> list(JsonModel model, int page, int limit) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setId(735041375153684480L);
        userInfo.setCreateTime(new Date());
        userInfo.setFullName("fullName3");
        userInfo.setEmail("323456@qq.com");
        userInfo.setName("name3");
        userInfo.setAge(30);

        System.out.println("==============================save==============================");
        System.out.println(userInfoMapper.save(userInfo));

        System.out.println("===========================replace=================================");
        System.out.println(userInfoMapper.replace(userInfo));

        System.out.println("============================update================================");
        System.out.println(userInfoMapper.update(userInfo));

        System.out.println("============================remove================================");
        System.out.println(userInfoMapper.remove(userInfo));

        System.out.println("============================insert================================");
        System.out.println(userInfoMapper.insert(userInfo));

        System.out.println("============================delete================================");
        System.out.println(userInfoMapper.delete(userInfo));

        System.out.println("============================countById================================");
        System.out.println(userInfoMapper.countById(735041375153684480L));

        System.out.println("============================countById================================");
        System.out.println(userInfoMapper.countById(userInfo));

        System.out.println("============================count================================");
        System.out.println(userInfoMapper.count());

        return model.build();
    }

    @RequestMapping(path = "/id_list")
    public ResponseEntity<ModelMap> id_list(JsonModel model, Long[] idList) {
        final UserInfo userInfo = new UserInfo();
        //userInfo.setCreateTime(new Date());
        userInfo.setFullName("fullName8");
        userInfo.setEmail("823456@qq.com");
        userInfo.setName("name8");
        userInfo.setAge(25);
        userInfo.setId(8L);
        System.out.println(userInfoMapper.insert(userInfo));
        return model.build();
    }

    @RequestMapping(path = "/info")
    public ResponseEntity<ModelMap> info(JsonModel model, @Positive(message = "{Positive}") Long id, Locale locale) {
        // 1. 方法验证的验证国际化方法默认为ValidationMessages国际化文件消息，不受spring.messages.basename属性影响
        // 2. 如果不传入消息内容时，默认的消息内容为系统原始的国际化方法内容格式化后的结果，建议不修改国际化方法文件
        // 3. 消息Key只会根据传入的消息内容Key变化
//        userInfoRepository.findById(id).ifPresentOrElse(userinfo -> {
//            System.out.println(userinfo.getRegionInfo());
//            System.out.println(userinfo);
//            model.setData(userinfo);
//        }, () -> System.out.println("用户信息为空"));
        return model.build();
    }

    @RequestMapping(path = "/save")
    public ResponseEntity<ModelMap> save(JsonModel model, @Validated UserSave userSave) {
        // 1. 保存时，UserSave 不能直接继承UserInfo，非要这样需要在每个字段上加注解，保存与数据库的对应关系
        // 2. MappedCollection 注解关联保存时，只会保存关联的实体信息，不会修改当前对象的外键值
        // 3. 测试时发现验证对象后面紧跟BindingResult，返回的是ConstraintViolationException异常
        // 4. 如果验证对象后面不跟BindingResult时，返回的是BindException异常
        // 5. springboot默认一般方法参数中验证的国际化方法文件名为ValidationMessages，而一般国际化文件名为message
        // 6. 为了使用时方便和防止找不到国际化键名时，我把一般国际化文件名配置为ValidationMessages
        // 7. 验证出异常时，一般会有返回很多code，但在国际化配置文件中添加这些键名之后，对返回结果没有影响，没有找到方法让系统自己使用这些code
        // 8. 自定义的国际化配置文件中如果找不到国际化的键名，系统会自动从hibernate框架的国际化配置文件中读取错误信息
        // 9. 一般我们把spring默认的国际化配置文件定义为ValidationMessages，然后再这这个文件中写自定义的错误消息就可以了
//        System.out.println(userSave);
//        UserInfo userInfo = userSave.toUserInfo();
//        System.out.println(userInfo);
//        userInfoRepository.insert(userInfo);
//        System.out.println(userInfo);
        return model.build();
    }

    @RequestMapping(path = "/remove")
    public ResponseEntity<ModelMap> remove(JsonModel model, @Positive Long id) {

        return model.build();
    }

    @RequestMapping(path = "/delete")
    public ResponseEntity<ModelMap> delete(JsonModel model, @Positive Long id) {

        return model.build();
    }

}
