package co.prior.iam.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import co.prior.iam.model.JwtConstants;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${security.jwt.access.secret}")
    private String accessSecret;

    @Value("${security.jwt.access.expirationTime}")
    private int accessExpirationTime;
    
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomDetailsService userDetailsService;
    
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest((HttpServletRequest) request);
		
        String jwt = getAuthentication(wrappedRequest);

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt, accessSecret)) {
            long userId = jwtTokenProvider.getUserIdFromJWT(jwt, accessSecret);

            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            		userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAuthentication(ResettableStreamHttpServletRequest request) {
    	String token = request.getHeader(JwtConstants.HEADER_STRING.value());
        if (StringUtils.hasText(token) && token.startsWith(JwtConstants.TOKEN_PREFIX.value())) {
            return token.substring(7);
        }
        
        return null;
    }
    
    private class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {
    	private byte[] byteArr;
    	private HttpServletRequest request;
    	private ResettableServletInputStream servletStream;
    	
    	public ResettableStreamHttpServletRequest(HttpServletRequest request) {
    		super(request);
    		this.request = request;
    		this.servletStream = new ResettableServletInputStream();
    	}
    	
    	@SuppressWarnings("unused")
		public void resetInputStream() {
    		servletStream.stream = new ByteArrayInputStream(byteArr);
    	}
    	
    	@Override
    	public ServletInputStream getInputStream() throws IOException {
    		if (byteArr == null) {
    			byteArr = StreamUtils.copyToByteArray(this.request.getInputStream());
    			servletStream.stream = new ByteArrayInputStream(byteArr);
    		}
    		return servletStream;
    	}
    	
    	@Override
    	public BufferedReader getReader() throws IOException {
    		if (byteArr == null) {
    			byteArr = StreamUtils.copyToByteArray(this.request.getInputStream());
    			servletStream.stream = new ByteArrayInputStream(byteArr);
    		}
    		return new BufferedReader(new InputStreamReader(servletStream));
    	}
    	
    	private class ResettableServletInputStream extends ServletInputStream {
    		private InputStream stream;
    		@Override
    		public int read() throws IOException {
    			return stream.read();
    		}
			@Override
			public boolean isFinished() {
				return false;
			}
			@Override
			public boolean isReady() {
				return false;
			}
			@Override
			public void setReadListener(ReadListener listener) {
			}
    	}
    }
    
}
