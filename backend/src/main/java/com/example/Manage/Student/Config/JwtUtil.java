package com.example.Manage.Student.Config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component // Bean tiện ích xử lý JWT
public class JwtUtil {

    // 🔹 Khóa bí mật để ký token (ít nhất 32 ký tự)
    private final String SECRET_KEY_STRING = "9f1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8091a2b3c4d5e6f7081920a1b2c3";
    private SecretKey secretKey;
    // 🔹 Thời hạn token: 5 giờ
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 5;

    // 🔹 Tạo SecretKey từ chuỗi bí mật khi bean được khởi tạo
    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));
    }

    // 🔹 Sinh token mới từ username và role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Ai là chủ thể token (username)
                .claim("role", role) // Gắn thêm quyền
                .setIssuedAt(new Date()) // Thời điểm phát hành
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Hết hạn
                .signWith(secretKey, SignatureAlgorithm.HS256) // Ký token
                .compact(); // Tạo chuỗi token hoàn chỉnh
    }

    // 🔹 Lấy username từ token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 🔹 Lấy role từ token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Date extractExpiry(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // 🔹 Kiểm tra token hợp lệ không (có parse được không, có hết hạn không)
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🔹 Parse token và trả về Claims (payload)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Dùng khóa bí mật để xác minh chữ ký
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
