package be.pxl.AON11.basicsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Ebert Joris on 24/03/2018.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "be.pxl.AON11.basicsecurity.repository"
)
public class JpaConfig {


}
