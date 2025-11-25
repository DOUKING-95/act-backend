package com.health_donate.health.service.external;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailTemplateService {

    public String loadTemplate(String name) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/partnership_email.html");
        String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);


        html = html.replace("[Nom du destinataire]", name);

        return html;
    }

    public String loadTemplate(String name, String code) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/code_activation.html");
        String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        html = html.replace("{{NAME}}", name);
        html = html.replace("{{CODE}}", code);

        return html;
    }

}

