package com.igh.crud.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.igh.crud.service.impl.UsuarioServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class InterceptorJwtIO extends OncePerRequestFilter{

	@Value("${jms.jwt.token.auth.path}")
	private String AUTH_PATH;
	@Value("#{'${jms.jwt.excluded.path}'.split(',')}")
	private List<String> excluded;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private UsuarioServiceImpl serviceUser;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");
		String url = request.getRequestURI();
		
		String username = null;
		String jwtToken = null;
		
		System.out.println("TOKEN FILTER :"+request.getRequestURI());
		
		if(url.equals(AUTH_PATH) || excluded(url) ) {
		}else if(requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				
			}catch(IllegalArgumentException e) {
				System.out.println("No se puede obtener el token JWT");
				throw new RuntimeException("No se puede obtener el token JWT");
			}
			catch (ExpiredJwtException  e) {
				// TODO: handle exception
				System.out.println("El token JWT ha caducado");
				throw new RuntimeException("El token JWT ha caducado");
			}
		}else {
			System.out.println("El token no comienza con Bearer String");
			throw new RuntimeException("El token no comienza con Bearer String");
		}
		if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = serviceUser.loadUserByUsername(username);
			
			if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
	private boolean excluded(String path) {
		boolean result = false;
		for(String exc:excluded) {
			if(exc.equals("#") && exc.equals(path) || path.startsWith(exc)) {
				
				result=true;
			}
		}
		
		return result;
	}
	

	

}
