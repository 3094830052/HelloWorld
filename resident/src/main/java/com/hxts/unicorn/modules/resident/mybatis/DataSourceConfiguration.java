/*
 * 文 件 名:  DataSourceConfiguration.java
 * 版    权:  武汉虹信通信技术有限责任公司. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  LENOVO
 * 修改时间:  2017年9月25日
 * 修改内容:  <修改内容>
 */
package com.hxts.unicorn.modules.resident.mybatis;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年9月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Configuration(PkgMacro.Name + "DataSourceConfiguration")
@PropertySource("classpath:com/hxts/unicorn/modules/" + PkgMacro.Name + "/mybatis/dbConfig.properties")
public class DataSourceConfiguration {
	@Value("${"+ PkgMacro.Name + ".jdbc.driver}")
	private String driver;
	@Value("${"+ PkgMacro.Name + ".jdbc.url}")
	private String url;
	@Value("${" + PkgMacro.Name + ".jdbc.username}")
	private String username;
	@Value("${" + PkgMacro.Name + ".jdbc.password}")
	private String password;
	//@Value("${jdbc.maxActive}")
	//private int maxActive;
	@Value("${" + PkgMacro.Name + ".jdbc.maxIdel}")
	private int maxIdel;
	//@Value("${jdbc.maxWait}")
	//private long maxWait;
	
	@Bean(name=PkgMacro.Name + "DataSource")
    public DataSource dataSource(){
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
			dataSource.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        dataSource.setMaxPoolSize(10);
        dataSource.setInitialPoolSize(5);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
       // dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdleTime(maxIdel);
        //dataSource.setMaxWait(maxWait);
        //dataSource.setValidationQuery("SELECT 1");
        //dataSource.setTestOnBorrow(true);
        return dataSource;
    }


}
