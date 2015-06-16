package kyungw00k.hideyourass

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ComponentScan(basePackages = 'kyungw00k.hideyourass')
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
class Application extends SpringBootServletInitializer {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
