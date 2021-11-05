package com.yyy.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //execution定义了拦截哪些类,*.*(..)代表任何类、任何方法任何参数
    //log没什么实际作用，在后面切入点可以直接用log()代表
    @Pointcut("execution(* com.yyy.blog.web.*.*(..))")
    public void log(){}

    @Before("log()")
    //这个切面方法拿到url、ip、被拦截的类及方法、请求的参数，封装到requestLog类（一个内部类，在下面）中
    //然后都打印在日志及控制台
    //JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,就可以获取到封装了该方法信息的JoinPoint对象
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //joinPoint.getSignature().getDeclaringTypeName()得到被拦截的类名
        //joinPoint.getSignature().getName()得到被拦截的方法名
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("----doAfter----");
    }
    //returning能够将目标方法的返回值传到切面增强方法里
    //这里会输出 result : index
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("result : {}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return  "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args);
        }
    }

}
