package com.project.aop;

import com.project.mapper.OperateLogMapper;
import com.project.pojo.OperateLog;
import com.project.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.Aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.project.anno.Log)")
    public void pc() {}

    @Autowired
    OperateLogMapper operateLogMapper;

    @Around("pc()")
    public Object logRecord(ProceedingJoinPoint pjp) throws Throwable {
        OperateLog operateLog = new OperateLog();
        long startTime = System.currentTimeMillis();

        Object result = pjp.proceed();

        long costTime = System.currentTimeMillis() - startTime;

        operateLog.setOperateEmpId(getCurrentUserId())
                .setOperateTime(LocalDateTime.now())
                .setClassName(pjp.getTarget().getClass().getName())
                .setMethodName(pjp.getSignature().getName())
                .setMethodParams(Arrays.toString(pjp.getArgs()))
                .setReturnValue(result == null ? "void" : result.toString())
                .setCostTime(costTime);

        operateLogMapper.insert(operateLog);
        log.info("记录操作日志: {}", log);

        return costTime;
    }

    private Integer getCurrentUserId() {
        //实现获取当前登入用户的逻辑
        return CurrentHolder.getCurrentId();
    }
}
