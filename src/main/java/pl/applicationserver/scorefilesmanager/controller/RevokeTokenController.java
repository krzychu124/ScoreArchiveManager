package pl.applicationserver.scorefilesmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping(value = "/oauth")
public class RevokeTokenController {

    private TokenStore tokenStore;

    public RevokeTokenController(DataSource dataSource) {
        this.tokenStore = new JdbcTokenStore(dataSource);
    }

    @GetMapping(value = "/revoke-token")
    public @ResponseBody
    ResponseEntity<HttpStatus> logout(@RequestHeader(value="Authorization") String authHeader) {
        if (authHeader != null) {
            try {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
