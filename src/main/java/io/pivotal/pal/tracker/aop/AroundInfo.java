package io.pivotal.pal.tracker.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AroundInfo {
//    @Around("execution(* *..*Service.*(..))")
    @Around("execution(* *..TestService.*(..))")
    public Object aroundLog(ProceedingJoinPoint jp) throws Throwable {
        Object ret = null ; /* modify null clear */
        try {
            System.out.println("Before by @Around         : " + jp.getSignature());
            ret = jp.proceed();
            System.out.println("AfterReturning by @Around : " + jp.getSignature() + " ret: " + ret);
        }catch (Throwable t) {
            System.out.println("*** AfterThrowing *** by @Around  : " + jp.getSignature() + " t: " + t);
            System.out.println(t.toString()); /* add by watai */
            //            throw t; /* delete by watai */
        } finally {
            System.out.println("After by @Around          : " + jp.getSignature());
        }
        return ret;
    }
}
