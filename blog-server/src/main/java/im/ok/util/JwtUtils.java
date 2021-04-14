package im.ok.util;

import io.jsonwebtoken.Claims;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Feng, Ge 2021/4/14 0014 14:47
 */
@Component
@ConfigurationProperties(prefix = "imok.jwt")
public class JwtUtils {
    private String secret;
    private long expire;
    private String header;
    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        return null;
    }

    // 获取jwt的信息
    public Claims getClaimByToken(String token) {
        return null;
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
