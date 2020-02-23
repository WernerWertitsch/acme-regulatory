package corp.acme.regulatory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//@ComponentScan("corp.acme.regulatory")
public class RegulatoryFacade {

    public static void main(String[] args) {
        SpringApplication.run(RegulatoryFacade.class, args);
    }


//    @Bean
//    public Supplier<Flux<Category>> categories() {
//        return () -> {
//            return Flux.fromIterable(this.categoryService.getAllCategories());
//        };
//    }

//    @Bean
//    public Function<String, Category> byName() {
//        return name -> categoryService.getByName(name);
//    }



}
