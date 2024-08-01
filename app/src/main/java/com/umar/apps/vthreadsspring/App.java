package com.umar.apps.vthreadsspring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Bean
    CommandLineRunner defaultRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                System.out.println(new App().getGreeting());
            }
        };
    }
    
    @Bean
    RestClient restClient(RestClient.Builder builder, @Value("${url}") String url) {
        return builder.baseUrl(url).build();
    }
    
    @RestController
    static
    class AppController {
        Logger logger = LoggerFactory.getLogger(getClass());
        @Autowired RestClient restClient;

        //This will inturn call the delay endpoint of httpbin
        // https://httpbin.org/delay/{seconds}
        @GetMapping("/{seconds}")
        ResponseEntity<?> response(@PathVariable("seconds") int seconds) {
            var resp = restClient.get().uri("/delay/" + seconds)
                    .retrieve()
                    .toEntity(String.class);
            logger.info("{} on {}" , resp.getStatusCode(), Thread.currentThread());
            return ResponseEntity.ok(Map.of("done", true));
        }
    
    }
}
