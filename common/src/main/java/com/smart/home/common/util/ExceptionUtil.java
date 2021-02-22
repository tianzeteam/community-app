package com.smart.home.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * @author jason
 * @date 2020/10/26
 **/
public class ExceptionUtil {

    /**
     * 获取全部错误堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    /**
     * spring反射调用的时候如果代码中有异常抛出的话是InvocationTargetException类型，而这个类型并没有覆写getMessage()方法
     * 我们需要调用InvocationTargetException 的getTargetException方法得到原始异常
     * @param throwable
     * @return
     */
    public static String getMessage(Throwable throwable) {
        if (throwable instanceof InvocationTargetException) {
            return ((InvocationTargetException)throwable).getTargetException().getMessage();
        }
        return throwable.getMessage();
    }
}
