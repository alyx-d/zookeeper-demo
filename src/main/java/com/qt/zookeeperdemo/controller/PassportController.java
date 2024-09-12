package com.qt.zookeeperdemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session) {
        if (isLoginInternal()) {
            return "have login";
        }
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return "username or password is blank";
        }
        var token = new UsernamePasswordAuthenticationToken(username, password);
        var auth = authenticationManager.authenticate(token);
        var context = SecurityContextHolder.getContextHolderStrategy().createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        return "ok";
    }

    @GetMapping("/isLogin")
    public boolean isLogin(HttpServletRequest request, HttpSession session) throws ServletException {
        return isLoginInternal();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "ok";
    }

    private boolean isLoginInternal() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}
