package com.ufma.PortalEgresso.security;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/**";
    // Chave estática em Base64 (512 bits)
    public static final String SECRET_KEY_BASE64 = "rlMT7aEZSePCUiVpkMBZLr0vtrCPB6uuDNpKQ2AoHgMoUir/xm7PwvXlgqDmVLR4//+yADBCM9savCMKUCxaAw==";
    // Converte a chave Base64 para SecretKey
    public static final SecretKey KEY = Keys.hmacShaKeyFor(
            Base64.getDecoder().decode(SECRET_KEY_BASE64)
    );
    public static final String HEADER_NAME = "Authorization";
    public static final Long EXPIRATION_TIME = 1000L * 60 * 30; // 30 minutos
}