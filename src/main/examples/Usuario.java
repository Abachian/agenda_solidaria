package ar.com.vwa.extranet.domain;

import java.time.LocalDateTime;
import java.util.Set;

import ar.com.vwa.extranet.services.security.VWUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id")
	private Long id;

	@Column(name = "username", unique = true)
	@NotEmpty
	private String username;

	@Column(name = "apellido")
	@NotEmpty
	private String apellido;
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	@Column(name = "email", unique = true)
	@NotEmpty
	private String email;

	@Column(name = "estado")
	@Enumerated(EnumType.ORDINAL)
	private Estado estado = Estado.HABILITADO;

	@Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	@Column(name = "fecha_ultimo_acceso", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaUltimoAcceso;

	@Column(name = "otp_codigo", nullable = false)
	private String otpCodigo;
	@Column(name = "otp_uuid")
	private String otpUuid;
	@Column(name = "otp_uri_vencimiento", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime otpUriVencimiento;
	@Column(name = "otp_validado")
	private Boolean otpValidado = Boolean.FALSE;
	@Column(name = "otp_intentos")
	private Integer otpIntentos;
	@Column(name = "otp_reintentar", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime otpReintentar;

	@Column(name = "login_intentos")
	private Integer loginIntentos;
	@Column(name = "login_vencimiento", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime loginVencimiento;

	@Column(name = "usuario_interno")
	private Boolean usuarioInterno;

	@Column(name = "password")
	private String password;
	@Column(name = "password_fecha_creacion", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime passwordCreacion;
	@Column(name = "password_modificar")
	private Boolean passwordModificar;
	@Column(name = "password_forget_uuid")
	private String passwordForgetUuid;
	@Column(name = "password_forget_vencimiento", nullable = true, columnDefinition = "TIMESTAMP")
	private LocalDateTime passwordForgetVencimiento;
	
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")

    )
    private Set<Rol> roles;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(
			name = "usuarios_proveedores",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "proveedor_id")

	)
	private Set<Proveedor> proveedores;

	public Usuario() {
	}

	public Usuario(VWUser user) {
		this.username = user.getUsername();
		this.apellido = user.getLastName();
		this.nombre = user.getFirstName();
		this.email = user.getEmail();
	}

	public boolean isBloqueado() {
		LocalDateTime now = LocalDateTime.now();
		return this.estado != null && this.estado == Estado.DESHABILITADO
				&& (loginVencimiento != null && loginVencimiento.isAfter(now));
	}

	public boolean isValidOtp() {
		return this.otpValidado != null && this.otpValidado == true;
	}

	public boolean isValidOtpVencimiento() {
		LocalDateTime now = LocalDateTime.now();
		return isValidOtp() && this.getOtpUriVencimiento() != null && this.getOtpUriVencimiento().isAfter(now);
	}

	public void incrementLoginIntentos() {
		if (this.loginIntentos == null)
			this.loginIntentos = 1;
		else
			this.loginIntentos = this.loginIntentos.intValue() + 1;
	}

	public void clearLogin() {
		this.setLoginIntentos(0);
		this.setLoginVencimiento(null);
		this.setEstado(Estado.HABILITADO);
	}

	public void copy(Usuario usuario) {

	}

	public void clear() {
		this.setLoginIntentos(0);
		this.setLoginVencimiento(null);
		this.setEstado(null);
		this.setFechaUltimoAcceso(null);
		this.setOtpCodigo(null);
		this.setOtpIntentos(-1);
		this.setOtpReintentar(null);
		this.setOtpUriVencimiento(null);
		this.setOtpUuid(null);
		this.setOtpValidado(null);
		this.setPassword(null);
		this.setPasswordCreacion(null);
		this.setPasswordModificar(null);
		this.setPasswordForgetUuid(null);
		this.setPasswordForgetVencimiento(null);
		this.setRoles(null);
		this.setId(null);
	}

	public Usuario(Usuario original) {
		this.id = original.id;
		this.username = original.username;
		this.apellido = original.apellido;
		this.nombre = original.nombre;
		this.email = original.email;
		this.estado = original.estado;
		this.fechaCreacion = original.fechaCreacion;
		this.fechaUltimoAcceso = original.fechaUltimoAcceso;
		this.otpCodigo = original.otpCodigo;
		this.otpUuid = original.otpUuid;
		this.otpUriVencimiento = original.otpUriVencimiento;
		this.otpValidado = original.otpValidado;
		this.otpIntentos = original.otpIntentos;
		this.otpReintentar = original.otpReintentar;
		this.loginIntentos = original.loginIntentos;
		this.loginVencimiento = original.loginVencimiento;
		this.usuarioInterno = original.usuarioInterno;
		this.password = original.password;
		this.passwordCreacion = original.passwordCreacion;
		this.passwordModificar = original.passwordModificar;
		this.passwordForgetUuid = original.passwordForgetUuid;
		this.passwordForgetVencimiento = original.passwordForgetVencimiento;
		this.roles = original.roles != null ? new java.util.HashSet<>(original.roles) : null;
		this.proveedores = original.proveedores != null ? new java.util.HashSet<>(original.proveedores) : null;
	}
}
