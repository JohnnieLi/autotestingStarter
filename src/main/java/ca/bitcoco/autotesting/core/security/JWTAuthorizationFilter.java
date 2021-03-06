package ca.bitcoco.autotesting.core.security;

import ca.bitcoco.autotesting.user.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ca.bitcoco.autotesting.core.security.SecurityUtils.*;

//Authorization(validate) token

/**
 * JWT Authorization filter, used for validate token then set SecurityContextHolder
 *
 * @author Jiangqi Li
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String username = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            User applicationUser = UserDetailsServiceImpl.findUserByUsername(username);

            if (applicationUser != null) {
                //Grant user authorities based on the application roles
                String ROLE_ = "ROLE_";
                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ANONYMOUS","ROLE_USERS");
                return new UsernamePasswordAuthenticationToken(applicationUser, null, authorities);
            }
            return null;
        }
        return null;
    }
}
