package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:TaskBazaar.properties")
public class DbConfig {


    private final Environment env;

    public DbConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.mysql.driver")));
        dataSource.setUrl(env.getProperty("db.mysql.url"));
        dataSource.setUsername(env.getProperty("db.mysql.user"));
        dataSource.setPassword(env.getProperty("db.mysql.password"));
        return dataSource;
    }

   @Bean
   public JdbcTemplate jdbcTemplate() {
       return new JdbcTemplate(dataSource());
   }

}