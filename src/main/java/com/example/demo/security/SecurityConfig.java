package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	/*
	 * @Override
	 * protected void configure(HttpSecurity httpSecurity) throws Exception {
	 * 
	 * httpSecurity.authorizeRequests().antMatchers("/secure/**").authenticated().
	 * anyRequest().authenticated().and()
	 * .formLogin().and().logout()
	 * .invalidateHttpSession(true).clearAuthentication(true)
	 * // .logoutRequestMatcher(new
	 * // AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
	 * .permitAll().and().exceptionHandling().accessDeniedHandler(
	 * accessDeniedHandler);
	 * }
	 */

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeRequests()
				// .antMatchers("/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/logout/**").permitAll()

				.antMatchers("/**").authenticated().anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error")
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}

}