package com.yxt.itv.library.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JhoneLee on 2017/10/9.
 *
 */
//代表Annotation属性的位置   FIELD 属性 TYPE类上，CONSTRUCTOR 构造方法上
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) //CLASS编译时  RUNTIME 运行时  SOURCE 源码时
public @interface BindId {
    int value();
}
