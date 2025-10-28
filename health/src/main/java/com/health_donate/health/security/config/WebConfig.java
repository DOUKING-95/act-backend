package com.health_donate.health.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Chemin absolu vers ton dossier uploads
        String uploadPath = "file:///Users/MacBookAir/Documents/health-donate-backend/health/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/Users/MacBookAir/Documents/health-donate-backend/health/uploads/"
                );
    }
}


