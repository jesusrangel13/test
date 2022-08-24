package cl.santander.transport.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class C2DRequestResponse implements Serializable {

	private static final long serialVersionUID = 8492315949485093038L;

	private String tramaSistemaCentral;
	private Integer tiempoRefrescoC2D;
	private Integer c2dVersion;
	private String iosVersionActual;
	private String publickey;
	private Long fechaHoraUTCServidor;
	private Integer saldo;
	private String androidVersionMinima;
	private Integer tiempoVigenciaC2D;
	private Integer maximoTiempoSenial;
	private String androidVersionActual;
	private String fechaCreacion;
	private Integer precisionMinima;
	private String c2dSistema;
	private String iosVersionMinima;
	private Long numeroSerialUnico;
	private Integer c2dICR;
	private String mensajeResultado;
	private Integer codigoResultado;

}
