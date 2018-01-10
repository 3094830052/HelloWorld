package com.hxts.unicorn.platform.interfaces.annotation;

import java.lang.annotation.*;

/**
 * 
 * <让springMVC支持多对象接收json>
 * <功能详细描述>
 * 
 * @author  xiashinian 工号
 * @version  [版本号, 2017年10月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiObject {
	String value() default "";
}
