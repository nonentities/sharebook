package com.swpu.sharebook.shiro.config;

import com.swpu.sharebook.redis.config.RedisCacheConfig;
import com.swpu.sharebook.shiro.cache.ShiroCacheManager;
import com.swpu.sharebook.shiro.filter.JwtFilter;
import com.swpu.sharebook.shiro.realm.JwtRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author KeennessNewBie
 * @Date 2020/1/23 10:41
 * @Message
 */
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    private CacheManager redisCacheManager;

    @Bean
    public Realm jwtRealm() {
        JwtRealm realm = new JwtRealm();
        return realm;
    }

    @Bean
    public SessionManager sessionManager() {
        return new DefaultWebSessionManager() {
            @Override
            public void setSessionValidationSchedulerEnabled(boolean sessionValidationSchedulerEnabled) {
           //     log.error("sessionValidationSchedulerEnabled");
                super.setSessionValidationSchedulerEnabled(false);
            }
        };
    }


    @Bean
    public SubjectFactory subjectFactory() {

        DefaultWebSubjectFactory defaultWebSubjectFactory = new DefaultWebSubjectFactory() {
            @Override
            public Subject createSubject(SubjectContext context) {
                context.setSessionCreationEnabled(false);
                return super.createSubject(context);
            }
        };
        return defaultWebSubjectFactory;
    }
    @Bean
    public SubjectDAO subjectDAO() {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = new DefaultSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(evaluator);
        return subjectDAO;
    }
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(jwtRealm());
        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setSubjectDAO(subjectDAO());
        securityManager.setCacheManager(shiroCacheManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        shiroFilter.setLoginUrl("/usersControl/unlogin");

        Map<String, Filter> filters = new LinkedHashMap();
        JwtFilter filter = new JwtFilter();
        filter.setLoginUrl("/usersControl/unlogin");
        filters.put("jwtFilter", filter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterChain = new LinkedHashMap<>();
        filterChain.put("/usersControl/register", "anon");
        filterChain.put("/usersControl/getVerifyCodeBySession", "anon");
        filterChain.put("/bookBaseControllectBook", "anon");
        filterChain.put("/bookBaseControl/getBookByKey", "anon");
        filterChain.put("/**", "jwtFilter");

        shiroFilter.setFilterChainDefinitionMap(filterChain);

        shiroFilter.setSecurityManager(securityManager);
        return shiroFilter;
    }
    @Bean
    public ShiroCacheManager shiroCacheManager() {
        return new ShiroCacheManager(redisCacheManager);
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }

}
