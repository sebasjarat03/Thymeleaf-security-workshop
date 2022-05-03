package com.example.demo.security;

import com.example.demo.model.UserType;

import org.jboss.jandex.TypeTarget.Usage;
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
				// entities info
				.antMatchers("/business-entity/**").permitAll()
				.antMatchers("/employee/**").permitAll()
				// vendors
				.antMatchers("/vendors*").permitAll()
				.antMatchers("/vendors/add/**").hasRole(UserType.ADMINISTRADOR.toString())
				.antMatchers("/vendors/edit/**").hasRole(UserType.ADMINISTRADOR.toString())
				.antMatchers("/vendors/del/**").hasRole(UserType.ADMINISTRADOR.toString())
				// ship method
				.antMatchers("/ship-methods*").permitAll()
				.antMatchers("/ship-methods/add/**").hasRole(UserType.ADMINISTRADOR.toString())
				.antMatchers("/ship-methods/edit/**").hasRole(UserType.ADMINISTRADOR.toString())
				.antMatchers("/ship-methods/del/**").hasRole(UserType.ADMINISTRADOR.toString())
				// purchase order details
				.antMatchers("/purchase-order-details*").permitAll()
				.antMatchers("/purchase-order-details/add/**").hasRole(UserType.OPERADOR.toString())
				.antMatchers("/purchase-order-details/edit/**").hasRole(UserType.OPERADOR.toString())
				.antMatchers("/purchase-order-details/del/**").hasRole(UserType.OPERADOR.toString())
				// purchase order headers
				.antMatchers("/purchase-order-headers*").permitAll()
				.antMatchers("/purchase-order-headers/add/**").hasRole(UserType.OPERADOR.toString())
				.antMatchers("/purchase-order-headers/edit/**").hasRole(UserType.OPERADOR.toString())
				.antMatchers("/purchase-order-headers/del/**").hasRole(UserType.OPERADOR.toString())
				.antMatchers("/**").authenticated().anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/login?error")
				.and()
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}

}