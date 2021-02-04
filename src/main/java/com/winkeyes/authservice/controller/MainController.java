package com.winkeyes.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winkeyes.authservice.model.AuthRequest;
import com.winkeyes.authservice.model.AuthResponse;
import com.winkeyes.authservice.service.JwtMethods;
import com.winkeyes.authservice.service.MyUserDetailsService;

@RestController
@CrossOrigin
public class MainController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailsService userDetailsService;

	@Autowired
	JwtMethods jwtMethods;

	 
	
	
	@GetMapping("/validate/{token}")
	public boolean validate(@PathVariable String token) {
		return jwtMethods.validateToken(token);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST )
	public ResponseEntity<?> createToken(@RequestBody AuthRequest req) throws Exception {
		
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		}catch (BadCredentialsException e) {
			throw new Exception("Worng input ",e);
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
		String token = jwtMethods.generateToken(userDetails);

		return ResponseEntity.ok(new AuthResponse(token));
	}
}
