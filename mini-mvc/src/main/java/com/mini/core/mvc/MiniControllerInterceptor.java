package com.mini.core.mvc;

import com.mini.core.mvc.model.IModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Aspect
@Component
public class MiniControllerInterceptor {
    @Around(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object transactionalMultiAspectAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        requireNonNull(proceedingJoinPoint, "TransactionMultiInterceptor Error: ProceedingJoinPoint is null");
        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        requireNonNull(methodSignature, "TransactionMultiInterceptor Error: MethodSignature is null");
        final Method method = requireNonNull(methodSignature.getMethod());
        // 获取IModel 参数
        final IModel<?, ?> model = Optional.ofNullable(proceedingJoinPoint.getArgs())
                .stream().flatMap(Arrays::stream).filter(it -> it instanceof IModel)
                .findAny().map(IModel.class::cast).orElse(null);
        Object result = proceedingJoinPoint.proceed();
        if (result == null && model != null) {
            model.show();
        }
        return result;
    }
}
