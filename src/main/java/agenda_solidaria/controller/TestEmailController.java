package agenda_solidaria.controller;

import agenda_solidaria.dto.TestEmailRequest;
import agenda_solidaria.model.User;
import agenda_solidaria.security.PasswordGenerator;
import agenda_solidaria.service.UserService;
import agenda_solidaria.service.mail.CoreDelegateMailService;
import agenda_solidaria.service.mail.MailData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestEmailController {

    private static final Logger logger = LoggerFactory.getLogger(TestEmailController.class);

    private final CoreDelegateMailService mailService;
    private final ObjectMapper objectMapper;

    private Map<String, Map<String, String>> mailTemplates = new HashMap<>();

    public TestEmailController(CoreDelegateMailService mailService,
                               ObjectMapper objectMapper) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
        loadMailTemplates();
    }

    private void loadMailTemplates() {
        try {
            ClassPathResource resource = new ClassPathResource("mail-templates.json");
            Map<?, ?> templates = objectMapper.readValue(resource.getInputStream(), Map.class);
            for (Map<?, ?> mail : (Iterable<Map<?, ?>>) templates.get("mails")) {
                String messageId = (String) mail.get("messageId");
                Map<String, String> template = new HashMap<>();
                template.put("subject", (String) mail.get("subject"));
                template.put("message", (String) mail.get("message"));
                mailTemplates.put(messageId, template);
            }

            logger.info("Plantillas de email cargadas correctamente");
        } catch (IOException e) {
            logger.error("Error al cargar las plantillas de email", e);
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendTestEmail(@RequestBody TestEmailRequest request) {
        try {
            logger.info("Recibida solicitud para enviar email de prueba a: {}", request.getEmail());

            ObjectNode mailJson = objectMapper.createObjectNode();
            mailJson.put("mailFrom", "no-reply@agendasolidaria.com");
            mailJson.put("mailTo", request.getEmail());
            mailJson.put("subject", request.getSubject());
            mailJson.put("message", request.getMessage());

            mailService.send(mailJson.toString());

            return ResponseEntity.ok("Email de prueba enviado correctamente a " + request.getEmail());
        } catch (Exception e) {
            logger.error("Error al enviar email de prueba", e);
            return ResponseEntity.internalServerError().body("Error al enviar email de prueba: " + e.getMessage());
        }
    }
    @PostMapping("/test-forgot-password")
    public ResponseEntity<String> testForgotPasswordEmail(@RequestParam String email) {
        try {
            logger.info("Probando envío de email de recuperación a: {}", email);

            ObjectNode mailJson = objectMapper.createObjectNode();
            mailJson.put("mailFrom", "no-reply@agendasolidaria.com");
            mailJson.put("mailTo", email);
            mailJson.put("subject", "Prueba - Recuperación de contraseña");


            String htmlMessage = "<h1>Prueba de Recuperación de Contraseña</h1>" +
                    "<p>Este es un email de prueba para la funcionalidad de olvidó contraseña.</p>" +
                    "<p>Su contraseña temporal es: <strong>Temp1234</strong></p>" +
                    "<p>Por favor cambie esta contraseña al ingresar al sistema.</p>";

            mailJson.put("message", htmlMessage);

            mailService.send(mailJson.toString());

            return ResponseEntity.ok("Email de recuperación de prueba enviado a " + email);
        } catch (Exception e) {
            logger.error("Error al enviar email de prueba de recuperación", e);
            return ResponseEntity.internalServerError().body("Error al enviar email de prueba: " + e.getMessage());
        }
    }

    //este es un ejemplo donde se obtiene el primer campo de "mail-templates.json"
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPasswordEmail(
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "Usuario") String firstName,
            @RequestParam(required = false, defaultValue = "Apellido") String lastName,
            @RequestParam(required = false) String username) {
        try {
            logger.info("Probando envío de email de recuperación a: {}", email);

            // Obtener el template "OlvidePassword"
            Map<String, String> template = mailTemplates.get("OlvidePassword");
            if (template == null) {
                throw new IllegalArgumentException("Template 'OlvidePassword' no encontrado");
            }

            // Generar contraseña temporal
            String tempPassword = PasswordGenerator.generateRandomPassword();

            // Si no se proporciona username, usar el email
            String finalUsername = username != null ? username : email;

            String message = template.get("message")
                    .replace("$lastName", lastName)
                    .replace("$firstName", firstName)
                    .replace("$username", finalUsername)
                    .replace("$password", tempPassword);

            // Crear el JSON para el servicio de correo
            ObjectNode mailJson = objectMapper.createObjectNode();
            mailJson.put("mailFrom", "agendasolidaria@gmail.com.ar");
            mailJson.put("mailTo", email);
            mailJson.put("subject", template.get("subject"));
            mailJson.put("message", message);

            mailService.send(mailJson.toString());

            return ResponseEntity.ok("Email de recuperación enviado a " + email +
                    "\nContraseña temporal generada: " + tempPassword);
        } catch (Exception e) {
            logger.error("Error al enviar email de prueba de recuperación", e);
            return ResponseEntity.internalServerError().body("Error al enviar email: " + e.getMessage());
        }
    }
}