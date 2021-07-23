package com.mini.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.mini.core.mybatis.injector.methods.CountByIdMethod;
import com.mini.core.mybatis.injector.methods.RemoveMethod;
import com.mini.core.mybatis.injector.methods.ReplaceMethod;
import com.mini.core.mybatis.injector.methods.SaveMethod;

import java.util.List;

public class MiniSqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        var list = super.getMethodList(mapperClass);
        list.add(new CountByIdMethod());
        list.add(new ReplaceMethod());
        list.add(new RemoveMethod());
        list.add(new SaveMethod());
        return list;
    }
}
