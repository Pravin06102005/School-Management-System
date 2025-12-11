package School.Management.School.Management.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelmapperConfig {

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
}
