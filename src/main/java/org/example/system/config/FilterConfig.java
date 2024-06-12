package org.example.system.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.example.system.domain.LoginUser;
import org.example.system.utils.ErrorResponse;
import org.example.system.utils.JWTUtils;
import org.example.system.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;

import java.io.IOException;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * FilterConfig 是一个用于验证用户身份的拦截器。
 *
 * @version 1.0
 * @author David
 */
@Component
public class FilterConfig implements HandlerInterceptor {

    public FilterConfig() {
        System.out.println("FilterConfig initialized");
    }

    @Resource
    private RedisCache redisCache;

    /**
     * 在请求处理之前进行身份验证和令牌检查。
     *
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     * @param handler  处理器对象
     * @return 如果验证通过，返回 true；否则返回 false
     * @throws Exception 如果验证过程中出现异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("Request authorized");
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse("Missing or invalid Authorization header", null));
            return false; // 终止请求
        }

        String token = authHeader.substring(7); // 提取实际的令牌

//        System.out.println("------------Claims claims = JWTUtils.validateJWT(token);-----------------");
        Claims claims;
        try {
            claims = JWTUtils.validateJWT(token);
            if (LocalDateTime.now().isAfter(claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse("Invalid token", "Token expired"));
                return false; // 终止请求
            }
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse("Invalid token", e.getMessage()));
            return false; // 终止请求
        }

//        System.out.println("------------user.getUserName().equals(claims.getSubject())-----------------");
        LoginUser user;
        try {
            user = redisCache.getCacheObject(token);
            if (user == null || !user.getUserName().equals(claims.getSubject())) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse("Invalid token", "User not found"));
                return false; // 终止请求
            }
            request.setAttribute("loginUser", user);
        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorResponse("Invalid token", e.getMessage()));
            return false; // 终止请求
        }

        if (redisCache.getExpire(token) <= 1200) {
            String newToken = JWTUtils.createJWT(user.getUserName());
            redisCache.setCacheObject(newToken, user, 1, TimeUnit.HOURS);
            response.setHeader("Authorization", newToken);
        }

        response.setHeader("Accept","application/json");
        response.setCharacterEncoding("UTF-8");

        return true; // 继续处理请求
    }

    /**
     * 发送错误响应。
     *
     * @param response      HttpServletResponse 对象
     * @param status        HTTP 状态码
     * @param errorResponse 错误响应对象
     * @throws IOException 如果写入响应时发生错误
     */
    private void sendErrorResponse(HttpServletResponse response, int status, ErrorResponse errorResponse) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    /**
     * 在请求处理完成后执行。
     *
     * @param request      HttpServletRequest 对象
     * @param response     HttpServletResponse 对象
     * @param handler      处理器对象
     * @param modelAndView 模型和视图对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /**
     * 在请求处理完成后执行清理工作。
     *
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     * @param handler  处理器对象
     * @param ex       异常对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("Request completed");
    }
}
