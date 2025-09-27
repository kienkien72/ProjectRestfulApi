package com.example.Manage.Student.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Manage.Student.Service.AuthService;
// import com.example.Manage.Student.Service.CheckTokenBlackList;
import com.example.Manage.Student.Service.CheckTokenBlackList;

import java.io.IOException;
import java.util.Collections;

@Component // Đánh dấu đây là một bean để Spring quản lý
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Filter chạy 1 lần cho mỗi request

    @Autowired
    private JwtUtil jwtUtil; // Dùng để thao tác với token

    @Autowired
    private CheckTokenBlackList checkTokenBlackList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, // Request từ client
            HttpServletResponse response, // Response gửi lại
            FilterChain filterChain) // Chuỗi filter tiếp theo
            throws ServletException, IOException {

        // 🔹 Lấy giá trị của header "Authorization" từ request
        String authHeader = request.getHeader("Authorization");

        // 🔹 Kiểm tra xem header có tồn tại và bắt đầu bằng "Bearer " hay không
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 🔹 Cắt chuỗi để lấy ra token thật (bỏ chữ "Bearer ")
            String token = authHeader.substring(7);

            // KT blacklist
            if (checkTokenBlackList.isBlackListToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token has been blacklisted");
                return;
            }

            // 🔹 Kiểm tra token có hợp lệ không
            if (jwtUtil.validateToken(token)) {
                // 🔹 Lấy username từ token
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                // Spring Security yêu cầu ROLE_ prefix
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                // 🔹 Tạo đối tượng Authentication để Spring Security hiểu là user này đã xác
                // thực
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                        null, Collections.singletonList(authority));

                // 🔹 Gắn thêm thông tin chi tiết của request (IP, session ID, ...)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 🔹 Đặt Authentication vào SecurityContext để lưu cho request hiện tại
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 🔹 Cho request đi tiếp qua filter tiếp theo (hoặc tới controller)
        filterChain.doFilter(request, response);
    }
}
