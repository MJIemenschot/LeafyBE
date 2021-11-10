package com.example.xedd.config;
import com.example.xedd.filter.JwtRequestFilter;
import com.example.xedd.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //JWT token authentication
        http

                .csrf().disable()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/plants/**").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/api/v1/plants/**").hasRole("USER")
                .antMatchers(HttpMethod.PATCH,"/api/v1/plants/**").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/api/v1/plants/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/users/**").permitAll()
                .antMatchers(HttpMethod.PATCH,"/api/v1/users/**").permitAll()
                //.hasRole("USER")
                .antMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll()
                //.antMatchers(HttpMethod.GET,"/api/v1/users/**").hasRole("ADMIN")

                .antMatchers("/admin/**").hasRole("ADMIN")
                //.antMatchers("/api/v1/users/**").hasRole("ADMIN")
                .antMatchers("/authenticated").authenticated()
                .antMatchers("/authenticate").permitAll()
                .anyRequest().permitAll()
                .and()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
//    @Configuration
//    public class WebConfig {
//        @Autowired
//        private CrosFilter corsFilter;
//        @Bean
//        public FilterRegistrationBean corsFilter() {
//            FilterRegistrationBean registration = new FilterRegistrationBean();
//            registration.setFilter(corsFilter);
//            registration.addUrlPatterns("/*");
//            registration.setName("corsFilter");
//            registration.setOrder(1);
//            return registration;
//        }
//    }


    //Code van Xiaoling
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*")
//                        .allowedOrigins("http://localhost:3000")
//                        .exposedHeaders("Authorization");
//            }
//
//
//        };
//    }
//
}

//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Autowired
//    public CustomUserDetailsService customUserDetailsService;
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
////    @Autowired
////    DataSource datasource;
//
//
////auth.jdbcAuthentication().dataSource(dataSource)
////                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
////                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?");
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService);
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        //JWT token authentication
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/authenticate").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/v1/items/**").hasRole("USER")
//                .antMatchers("/api/v1/users/**/authorities").hasRole("ADMIN")
////                .antMatchers("/authenticated").authenticated()
////                .antMatchers("/authenticate").permitAll()
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Configuration
//    public class WebConfig {
//        @Autowired
//        private CrosFilter corsFilter;
//
//        @Bean
//        public FilterRegistrationBean corsFilter() {
//            FilterRegistrationBean registration = new FilterRegistrationBean();
//            registration.setFilter(corsFilter);
//            registration.addUrlPatterns("/*");
//            registration.setName("corsFilter");
//            registration.setOrder(1);
//            return registration;
//        }
//    }
//}