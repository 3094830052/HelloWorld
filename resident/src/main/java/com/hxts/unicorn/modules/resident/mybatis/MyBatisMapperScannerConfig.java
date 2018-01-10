/*
 * 文 件 名:  MyBatisMapperScannerConfig.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月25日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.modules.resident.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration(PkgMacro.Name + "MapperScannerConfig")
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

	@Bean(name=PkgMacro.Name + "MapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(PkgMacro.Name + "SqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.hxts.unicorn.modules." + PkgMacro.Name + ".dao");
        return mapperScannerConfigurer;
    }

}
