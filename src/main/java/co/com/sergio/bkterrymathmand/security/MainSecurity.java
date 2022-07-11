package co.com.sergio.bkterrymathmand.security;

import co.com.sergio.bkterrymathmand.security.jwt.JwtEntryPoint;
import co.com.sergio.bkterrymathmand.security.jwt.JwtTokenFilter;
import co.com.sergio.bkterrymathmand.security.service.UsuarioRolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioRolServiceImpl usuarioRolService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioRolService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/login").permitAll()
                .antMatchers(HttpMethod.POST,"/auth/refresh").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/agregarAdministrador").permitAll()
                .antMatchers("/v3/api-docs/**","/v2/api-docs/**", "/swagger-ui/**",
                        "/swagger-resources/**","/configuration/**").permitAll()
                .antMatchers(HttpMethod.GET,"/cartilla/listarCartillas").permitAll()
                .antMatchers(HttpMethod.GET, "/estudiante/estudiantenombre").permitAll()
                .antMatchers(HttpMethod.GET, "/cartilla/obtenerPreguntas").permitAll()
                .antMatchers(HttpMethod.PUT, "/respuesta/guardarRespuestaEstudiante").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
