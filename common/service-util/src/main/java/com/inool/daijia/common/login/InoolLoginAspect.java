package com.inool.daijia.common.login;

import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.inool.daijia.common.constant.RedisConstant;
import org.springframework.util.StringUtils;
import com.inool.daijia.common.util.AuthContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Aspect
@Order(100)
public class InoolLoginAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param joinPoint
     * @param inoolLogin
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.inool.daijia.*.controller.*.*(..)) && @annotation(inoolLogin)")
    public Object process(ProceedingJoinPoint joinPoint, InoolLogin inoolLogin) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String token = request.getHeader("token");

        if(!StringUtils.hasText(token)) {
            throw new InoolException(ResultCodeEnum.LOGIN_AUTH);
        }
        String userId = (String)redisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_KEY_PREFIX+token);
        if(StringUtils.hasText(userId)) {
            AuthContextHolder.setUserId(Long.parseLong(userId));
        }
        return joinPoint.proceed();
    }

}