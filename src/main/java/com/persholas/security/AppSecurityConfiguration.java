package com.persholas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AppUserDetailsService appUserDetailsService;
    @Autowired
    public AppSecurityConfiguration(AppUserDetailsService appUserDetailsService)
    {
        this.appUserDetailsService = appUserDetailsService;
    }

    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(4));
        return provider;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/console/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/clearview/hotels").hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE")
                .antMatchers("/clearview/hotels/{\\d+}").hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE")
                .antMatchers("/clearview/hotels/{\\d+}/customers").hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE")
                .antMatchers("/clearview/hotels/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/clearview/employees/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/clearview/customers/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/clearview/home").hasAnyAuthority("ROLE_ADMIN","ROLE_EMPLOYEE","ROLE_CUSTOMER")
                .antMatchers("/clearview/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/clearview/login").usernameParameter("username").passwordParameter("password").loginProcessingUrl("/clearview/login/authenticate").defaultSuccessUrl("/clearview/home").failureUrl("/login?error=true").permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/clearview/logout")).logoutSuccessUrl("/clearview/").permitAll().and().exceptionHandling().accessDeniedPage("/403");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/imgs/**", "/files/**");
    }
}
