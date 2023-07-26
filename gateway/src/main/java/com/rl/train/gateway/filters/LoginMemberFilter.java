package com.rl.train.gateway.filters;


import com.rl.train.gateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class LoginMemberFilter implements Ordered, GlobalFilter {
    private static final Logger LOGGER =  LoggerFactory.getLogger(LoginMemberFilter.class);
    
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/admin")
            || path.contains("/hello")
            || path.contains("/member/member/login")
            || path.contains("/member/member/send-code")){
            LOGGER.info("不需要登录验证: {}", path);
            return chain.filter(exchange);
        }
        // 获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        if (token == null || token.isEmpty()){
            LOGGER.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 检验token是否有效，包括token是否被修改过，是否过期
        boolean validate = JwtUtil.validate(token);
        if (validate){
            LOGGER.info("token有效，应该放行该请求");
            return chain.filter(exchange);
        }else {
            LOGGER.warn("token无效， 请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
