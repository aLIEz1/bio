package com.lm.bio.cloud.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * EnableAuthorizationServer开启授权服务器的功能
 *
 * @author zhangfuqi
 * @date 2020/12/30
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("bio-api")
                .secret(passwordEncoder.encode("bio-secret"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(7 * 24 * 3600)
                .refreshTokenValiditySeconds(30 * 24 * 3600)
                .and()
                .withClient("inside-app")
                .secret(passwordEncoder.encode("inside-secret"))
                .authorizedGrantTypes("client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(Integer.MAX_VALUE);
        super.configure(clients);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jtwTokenStore())
                .tokenEnhancer(jwtAccessTokenConverter());
        super.configure(endpoints);
    }

    private TokenStore jtwTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 加载私钥
     *
     * @return JwtAccessTokenConverter
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        ClassPathResource resource = new ClassPathResource("bio.jks");
        KeyStoreKeyFactory keyFactory = new KeyStoreKeyFactory(resource, "bio123456".toCharArray());
        tokenConverter.setKeyPair(keyFactory.getKeyPair("bio", "bio123456".toCharArray()));
        return tokenConverter;
    }
}
