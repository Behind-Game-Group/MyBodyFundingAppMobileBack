package fr.mybodysocial.mybodyfunding.security.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * openApi configuration
 * same as swagger
 */
@Configuration
@SecurityScheme(
        name="Bearer Authentification",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme="bearer"
)
public class SpringOpenApiConfiguration {

    private static final Logger LOG = LogManager.getLogger();

    @Bean
    public OpenAPI customOpenAPI(Environment env) {
        SpringOpenApiConfiguration.LOG.debug("Loading Open API configuration");
        var openApi = new OpenAPI();
        var rootUrl = "http://localhost:" + env.getProperty("server.port", "8080")
                + env.getProperty("server.servlet.context-path", "");

        var info = new Info();
        info.setTitle("My Body Funding Rest API Information");
        info.setDescription(
                "Describes all services available for you in order to handle My Body Funding project. Will use JWT and Spring Security.");
        info.setTermsOfService("Free to use inside training sessions");
        info.setVersion("Avril 2023");

        var contact = new Contact();
        contact.setName("BENKHEDIR Maroua");
        contact.setEmail("benkhedir.maroua@gmail.com");
        info.setContact(contact);

        //TODO: ajouter une licence

        openApi.setInfo(info);

        List<Tag> tags = new ArrayList<>();

        var t = new Tag();

        t = new Tag();
        t.setDescription("Let you activate,delete,add,update,checkpassword,find users.");
        t.setName("User API");
        tags.add(t);

        openApi.setTags(tags);

        // "bearer-key" will be used in the @Opertaion annotation, it will not be
        // handled by Spring
      //  openApi.components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme()
        //        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat(SecurityConstants.TOKEN_TYPE)));
      //  openApi.

        return openApi;
    }
}
