package com.swpu.sharebook.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.swpu.sharebook.shiro.jwt.JWTUtil;
import com.swpu.sharebook.shiro.jwt.JwtToken;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author KeennessNewBie
 * @Date 2020/1/23 10:50
 * @Message
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.error("isAccessAllowed");
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.error("onAccessDenied");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpServletRequest req = (HttpServletRequest) request;

        log.error(req.getRequestURI());
        String jwt = req.getHeader("jwt");
        if (StringUtils.isEmpty(jwt)) {
            log.debug("access failed,because jwt is null");
            noLogin(response);
            return false;
        } else {
            try {
                String info = JWTUtil.parsingToken(jwt);
                getSubject(request, response).login(new JwtToken(info));
            } catch (Exception e) {
                log.error(e.getMessage());
                onLoginFail(response, e);
                return false;
            }
        }
        return true;
    }

    private void onLoginFail(ServletResponse response, Exception e) throws IOException {
        String msg = e.getMessage();
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (e instanceof ExpiredJwtException) {
            msg = "登录已过期";
            log.debug(msg);
        }
        httpResponse.getWriter().write(JSON.toJSONString(ResponseResult.ERROR(HttpServletResponse.SC_UNAUTHORIZED, msg)));
    }

    private void noLogin(ServletResponse response) throws IOException {
        response.getWriter().write(JSON.toJSONString(ResponseResult.ERROR(500, "未登录！")));
    }
}
