package agenda_solidaria.controller;

import agenda_solidaria.dto.*;
import agenda_solidaria.exception.ServiceException;
import agenda_solidaria.security.PasswordGenerator;
import agenda_solidaria.security.SecurityService;
import agenda_solidaria.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/change-password")
    public void changePasswordExterno(@RequestBody ChangePasswordRequest request) {
        this.userService.changePassword(request);
    }

    @PostMapping("/forget-password/{username}")
    public SimpleDto<Boolean> forgetPassword(@PathVariable("username") String username) {
        String tempPassword = PasswordGenerator.generateRandomPassword();
        boolean interno = this.userService.forgetPassword(username, tempPassword);
        return new SimpleDto<>(interno);
    }
//
//    @GetMapping("/forget-password/{uuid}")
//    public SimpleDto<Boolean> verifyForgetPassword(@PathVariable("uuid") String uuid) {
//        boolean interno = this.userService.verifyForgetPassword(uuid);
//        return new SimpleDto<>(interno);
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        securityService.logout(token);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/otp/register")
//    public ResponseEntity<Void> registrarTotpCode(@Valid @RequestBody OtpRequest request) {
//        securityService.registrarOTP(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/otp/deregister")
//    public ResponseEntity<Void> desregistrarTotpCode(@Valid @RequestBody OtpRequest request) {
//        securityService.desregistrarOTP(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/otp/validate")
//    public ResponseEntity<Void> validarOtpCode(@Valid @RequestBody ValidateOtpRequest request) {
//        securityService.validarOtp(request);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/otp/qr/{requestId}")
//    public ResponseEntity<String> showSecretKeyQR(@PathVariable String requestId) {
//        try {
//            String qrCode = securityService.showSecretKeyQR(requestId);
//            return ResponseEntity.ok(qrCode);
//        } catch (ServiceException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


} 