package com.epam.brest.config;

import com.epam.brest.rest.VersionController;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

@Bean
  public OpenAPI springSetlistOrganizerOpenAPI() {
      return new OpenAPI()
              .info(new Info().title("Setlist Organizer API")
              .description("'Setlist Organizer' is a web application for organizing repertoires of musical bands.")
              .version(new VersionController().version())
              .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0)")))
              .externalDocs(new ExternalDocumentation()
              .description("Setlist Organizer github")
              .url("https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer"));
  }
}
