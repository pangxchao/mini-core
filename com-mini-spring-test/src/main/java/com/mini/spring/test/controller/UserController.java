package com.mini.spring.test.controller;

import com.mini.spring.model.MapJsonModel;
import com.mini.util.lang.StringUtil;
import com.mini.util.validate.ValidateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/user")
public class UserController {
    /**
     * -用户注册接口
     * @param model    返回数据
     * @param phone    手机号
     * @param name     姓名
     * @param password 密码
     * @param code     验证码
     * @param adCode   地区码
     * @param school   单位
     * @param email    邮箱
     */
    @RequestMapping({"register.htm"})
    public MapJsonModel register(MapJsonModel model, String phone, String name, String password, String code, long adCode,
            String school, String email) {
        // 验证手机号是否为正确的手机号格式
        ValidateUtil.mobile(phone, 1,  "请输入正确的手机号码");
        // 验证用户输入的姓名是否为空
        ValidateUtil.blank(name, 1, "请输入姓名");
        // 验证密码是否为空
        ValidateUtil.blank(password, 1, "请输入正确的密码");
        // 验证验证码是否为空
        ValidateUtil.blank(code, 1, "请输入正确的验证码");
        // 验证地区码是否为空
        ValidateUtil.validate(adCode > 0, 1, "请选择所在地区");
        // 验证学校名称是否为空
        ValidateUtil.blank(school, 1, "请输入学校名称");
        // 验证邮箱地址格式是否正确
        if (!StringUtil.isBlank(email)) {
            ValidateUtil.email(email, 1, "请输入正确的邮箱地址");
        }

        //// 从数据库查询用户发送手机验证码的数据
        //PhoneVerifyExtend verify = PhoneVerifyDao.findByPhone(commonDao, phone);
        //// 验证用户是否发送过验证码，如果发送验证码的数据为空，则未发送过验证码
        //model.validateNull(verify, 1, "请先发送手机验证码");
        //// 验证用户验证码是否在有效时间之内
        //model.validate(verify.getTime() >= (System.currentTimeMillis() - 1000 * 60 * 5), 1, "验证码已过期，请重新发送");
        //// 验证验证码输入是否正确
        //model.validate(code.equals(verify.getCode()), 1, "请输入正确的验证码");
        //// 验证用户名是否重复（手机号是否重复）
        //model.validate(UserInfoDao.findByAccount(dao, phone) == null, 1, "该用户已经存在");
        //// 根据名称查询学校信息
        //SchoolInfoExtend schoolInfo = SchoolInfoDao.findByName(commonDao, school);
        //// 都验证通过时，保存用户信息，并返回注册成功
        //String seed = PKGenerator.genseed(6); // 生成密码种子
        //// 生成用户VIP到期时间
        ////long expire = DateUtil.getDateOfDay(new Date(), 15).getTime();
        //dao.transaction(() -> { // 数据库事务
        //    long userId = PKGenerator.key();
        //    if (schoolInfo == null) { // 如果学校不存时， 则添加学校记录
        //        SchoolInfoDao.insert(commonDao, PKGenerator.key(), school, 1);
        //    } else { // 如果学校信息存在时， 则将学校的使用次数 +1
        //        SchoolInfoDao.updateCountById(commonDao, schoolInfo.getId());
        //    }
        //    // 添加用户记录
        //    UserInfoDao.register(dao, userId, phone, phone, 1, name, MD5Util.encode(password.toUpperCase() + seed), seed, email, 0, 0, null, school, adCode);
        //    //            // 添加用户VIP到期时间
        //    //            UserPayDao.insert(dao, userId, expire);
        //    //            // 添加用户VIP购买明细
        //    //            UserPayDetailDao.insert(dao, PKGenerator.key(), userId, 0, "注册赠送", System.currentTimeMillis(), 0, expire);
        //
        //    return commonDao.transaction(() -> {
        //        // 清除用户手机验证码
        //        PhoneVerifyDao.deleteById(commonDao, verify.getId());
        //        // 关注系统默认知识圈
        //        return knowledgeDao.transaction(() -> {
        //            long coterieId = R.Init.getSupportCoterieId();
        //            if (coterieId > 0) {
        //                CoterieMemberDao.insert(knowledgeDao, PKGenerator.key(), userId, coterieId, CoterieMemberExtend.IDENTITY_FANS, 0);
        //                CoterieInfoDao.updateFansCount(knowledgeDao, coterieId, +1);
        //            }
        //            return true;
        //        });
        //    });
        //});
        //model.setError(0).setMessage("注册成功");

        return model;
    }

}
