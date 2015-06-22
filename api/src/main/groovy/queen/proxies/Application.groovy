package queen.proxies

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@ComponentScan
@EnableScheduling
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
class Application extends SpringBootServletInitializer {
    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
