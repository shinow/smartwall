package com.itekchina.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
//@EnableWebSecurity
//@EnableResourceServer
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("john.carnell").password("password1").roles("USER").and()
				.withUser("william.woodward").password("password2").roles("USER", "ADMIN");
	}
}
