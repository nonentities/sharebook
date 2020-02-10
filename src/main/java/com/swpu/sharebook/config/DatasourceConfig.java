package com.swpu.sharebook.config;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;
//@Configuration
////扫描 Mapper 接口并容器管理
//@ConfigurationProperties(prefix = "druid")
public class DatasourceConfig {
	// 精确到 master 目录，以便跟其他数据源隔离
  static final String PACKAGE = "com.swpu.sharebook.mapper";
  static final String MAPPER_LOCATION = "classpath:mapper/*.xml";
  private String url;
  private String user;
  private String password;
  private String driverClass;
  private Integer maxActive;
  private Integer minIdle;
  private Integer initialSize;
  private Long maxWait;
  private Long timeBetweenEvictionRunsMillis;
  private Long minEvictableIdleTimeMillis;
  private Boolean testWhileIdle;
  private Boolean testOnBorrow;
  private Boolean testOnReturn;
  
  @Bean(name = "dataSource")
  @Primary
  public DataSource dataSource() {
  	//jdbc配置
      DruidDataSource dataSource = new DruidDataSource();
      dataSource.setDriverClassName(driverClass);
      dataSource.setUrl(url);
      dataSource.setUsername(user);
      dataSource.setPassword(password);
      //连接池配置
      dataSource.setMaxActive(maxActive);
      dataSource.setMinIdle(minIdle);
      dataSource.setInitialSize(initialSize);
      dataSource.setMaxWait(maxWait);
      dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
      dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
      dataSource.setTestWhileIdle(testWhileIdle);
      dataSource.setTestOnBorrow(testOnBorrow);
      dataSource.setTestOnReturn(testOnReturn);
      dataSource.setValidationQuery("SELECT 'x'");
      
      dataSource.setPoolPreparedStatements(true);
      dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
      
      try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}
      return dataSource;
  }

  @Bean(name = "transactionManager")
  @Primary
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
  }
  @Bean(name = "sqlSessionFactory")
  @Primary
  public SqlSessionFactory ds1SqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
          throws Exception {
      final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
      sessionFactory.setDataSource(dataSource);
      sessionFactory.setTypeAliasesPackage("com.swpu.sharebook.entity");
      sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
              .getResources(DatasourceConfig.MAPPER_LOCATION));
      return sessionFactory.getObject();
  }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    public Long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public Boolean getTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(Boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
