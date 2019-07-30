package com.tensquare.jwt;

import io.jsonwebtoken.*;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        try {
            Claims claims = Jwts.parser().setSigningKey("itcast")
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NjQ0NDU3NTUsImV4cCI6MTU2NDQ0NTc4NSwicm9sZSI6ImFkbWluIn0.p0RiBRJ3RpFJhZkCbFg6Ac9E5TZ5IwMGhfxj_rhP164")
                    .getBody();

            System.out.println("用户id"+claims.getId());
            System.out.println("用户名："+claims.getSubject());
            System.out.println("登录时间"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
            System.out.println("过期时间"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
            System.out.println("用户角色"+claims.get("role"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
