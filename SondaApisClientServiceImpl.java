package cl.santander.transport.client.sonda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.santander.transport.config.sonda.SondaProfileServ;
import cl.santander.transport.exception.ServiceException;
import cl.santander.transport.request.C2DRequest;
import cl.santander.transport.request.EnrollmentRequest;
import cl.santander.transport.request.QueryC2DRequest;
import cl.santander.transport.response.App;
import cl.santander.transport.response.C2DRequestResponse;
import cl.santander.transport.response.EnrollmentResponse;
import cl.santander.transport.response.QueryC2DResponse;
import cl.santander.transport.response.Requests;
import cl.santander.transport.response.Transaction;
import cl.santander.transport.response.TransactionResume;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SondaApisClientServiceImpl implements SondaApisClientService {

	private WebClient clientFlux;
	@Autowired
	private SondaProfileServ sonda;

	@PostConstruct
	public void init() {
		clientFlux = WebClient.create(sonda.getBaseUrl());
	}// clousure post-constructor

	@Override
	public Mono<EnrollmentResponse> enrollment(EnrollmentRequest request) {

		// TODO este if es solo mientras el servicio es dummy luego solo deberia estar
		// la parte del else y eliminar la funcion dummyEnrollmentResponse
		if (true) {
			return Mono.just(dummyEnrollmentResponse(request.isSuccessResponse()));
		} else {
			return clientFlux.post().uri(sonda.getEnrolUri()).accept(MediaType.APPLICATION_JSON).bodyValue(request)
					.exchange().flatMap(status -> {
						log.info("response sonda enroll => {} ", status);
						if (!status.statusCode().is2xxSuccessful())
							throw new ServiceException(status.statusCode().toString(),
									status.bodyToMono(EnrollmentResponse.class).toString());
						log.info("status: " + status.bodyToMono(EnrollmentResponse.class));
						return status.bodyToMono(EnrollmentResponse.class);
					});
		}

	}

	@Override
	public Mono<QueryC2DResponse> queryC2D(QueryC2DRequest request) {
		// TODO este if es solo mientras el servicio es dummy luego solo deberia estar
		// la parte del else y eliminar la funcion dummyQueryC2DResponse
		if (true) {
			return Mono.just(dummyQueryC2DResponse(request.isSuccessResponse()));
		} else {
			return clientFlux.post().uri(sonda.getEnrolUri()).accept(MediaType.APPLICATION_JSON).bodyValue(request)
					.exchange().flatMap(status -> {
						log.info("response sonda enroll => {} ", status);
						if (!status.statusCode().is2xxSuccessful())
							throw new ServiceException(status.statusCode().toString(),
									status.bodyToMono(QueryC2DResponse.class).toString());
						log.info("status: " + status.bodyToMono(QueryC2DResponse.class));
						return status.bodyToMono(QueryC2DResponse.class);
					});
		}
	}
	
	@Override
	public Mono<C2DRequestResponse> requestC2D(C2DRequest request) {
		if (true) {
			return Mono.just(dummyRequestC2DResponse(request.isSuccessResponse()));
		} else {
			return clientFlux.post().uri(sonda.getEnrolUri()).accept(MediaType.APPLICATION_JSON).bodyValue(request)
					.exchange().flatMap(status -> {
						log.info("response sonda enroll => {} ", status);
						if (!status.statusCode().is2xxSuccessful())
							throw new ServiceException(status.statusCode().toString(),
									status.bodyToMono(C2DRequestResponse.class).toString());
						log.info("status: " + status.bodyToMono(C2DRequestResponse.class));
						return status.bodyToMono(C2DRequestResponse.class);
					});
		}
	}
	
	public EnrollmentResponse dummyEnrollmentResponse(boolean success) {
		EnrollmentResponse result = new EnrollmentResponse();
		if (success) {
			result = EnrollmentResponse.builder().nuAbt("fc5b00715157e3d3")
					.mensajeResultado("Proceso realizado exitosamente").idEnrolamiento(2097L)
					.nroTarjetaExterno(6001024L).codigoResultado(0).build();
		} else {
			result = EnrollmentResponse.builder().nuAbt(null)
					.mensajeResultado("Requerimiento rechazado por inconsistencia de horas").idEnrolamiento(null)
					.nroTarjetaExterno(null).codigoResultado(-100).build();
		}
		return result;
	}
	
	public C2DRequestResponse dummyRequestC2DResponse(boolean success) {
		C2DRequestResponse result = new C2DRequestResponse();
		Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("ddMMyyyyhhmmss");
        Long now = Long.valueOf(formatter.format(date));
        
		if (success) {
			result = C2DRequestResponse.builder().tramaSistemaCentral("D3E3575171005BFC5401E50F3161011EFEBD614D8D0185E4A3371B5967D695")
					.tiempoRefrescoC2D(6).c2dVersion(1).iosVersionActual("1.0.0").publickey("ME4wEAYHKoZIzj0CAQYFK4EEACEDOgAEIcv7lkva2cogcJW8soUhh6ATjLfqCOCgw86mdYGDvjndJSvqhxrhbY5wyNFPVSbj027aALx2/qM=")
					.fechaHoraUTCServidor(now).saldo(0).androidVersionMinima("1.0.0").tiempoVigenciaC2D(20).maximoTiempoSenial(60)
					.androidVersionActual("1.0.0").fechaCreacion("02/09/2021 13:34:45").precisionMinima(130).c2dSistema("T").iosVersionMinima("1.0.0").numeroSerialUnico(null)
					.c2dICR(1).mensajeResultado("Proceso exitoso").codigoResultado(0).build();
		} else {
			result = C2DRequestResponse.builder().tramaSistemaCentral(null)
					.tiempoRefrescoC2D(0).c2dVersion(0).iosVersionActual(null).publickey(null)
					.fechaHoraUTCServidor(now).saldo(0).androidVersionMinima(null).tiempoVigenciaC2D(0).maximoTiempoSenial(60)
					.androidVersionActual(null).fechaCreacion(null).precisionMinima(0).c2dSistema(null).iosVersionMinima(null).numeroSerialUnico(null)
					.c2dICR(0).mensajeResultado("Celular no tiene asociado una cuenta").codigoResultado(-5).build();
		}
		return result;
	}
	

	public QueryC2DResponse dummyQueryC2DResponse(boolean success) {
		QueryC2DResponse result = new QueryC2DResponse();
		if (success) {
			
			List<App> listApp = new ArrayList<>();
			App app = App.builder().codigoApp(1).nombreApp("TRANSAPP").codigoOperador(29).nombreOperador("MINISTERIO TRANSPORTE").build();
			listApp.add(app);
			
			List<TransactionResume> listTrxResume = new ArrayList<>();
			TransactionResume trxResume1 = TransactionResume.builder().tipoTrx("CARGAS").fechaPrimeraTrx("10/08/2021 13:31:06").fechaUltimaTrx("11/08/2021 13:07:31").cantidad(2).monto(4000).build();
			TransactionResume trxResume2 = TransactionResume.builder().tipoTrx("Usos").fechaPrimeraTrx("10/08/2021 15:02:51").fechaUltimaTrx("10/08/2021 15:57:36").cantidad(6).monto(2800).build();
			listTrxResume.add(trxResume1);
			listTrxResume.add(trxResume2);
			
			List<Transaction> listTrx = new ArrayList<>();
			Transaction trx1 = Transaction.builder().tipoTrx("CARGA").fechaTrx("11/08/2021 13:07:31").monto(3000).estado("CARGADA").servicio("---").fechaVarolizacion("---").build();
			Transaction trx2 = Transaction.builder().tipoTrx("CARGA").fechaTrx("10/08/2021 13:31:06").monto(1000).estado("CARGADA").servicio("---").fechaVarolizacion("---").build();
			Transaction trx3 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:57:36").monto(700).estado("Valorizado").servicio("BUS -SERVICIO 1029").fechaVarolizacion("11/08/2021 02:30:03").build();
			Transaction trx4 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:56:56").monto(700).estado("Valorizado").servicio("BUS -SERVICIO 1029").fechaVarolizacion("11/08/2021 02:30:03-").build();
			Transaction trx5 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:03:04").monto(0).estado("Valorizado").servicio("BUS -SERVICIO 17431").fechaVarolizacion("11/08/2021 02:30:03").build();
			Transaction trx6 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:02:59").monto(0).estado("Valorizado").servicio("BUS -SERVICIO 8197").fechaVarolizacion("11/08/2021 02:30:03").build();
			Transaction trx7 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:02:56").monto(700).estado("Valorizado").servicio("BUS -SERVICIO 1029").fechaVarolizacion("11/08/2021 02:30:03").build();
			Transaction trx8 = Transaction.builder().tipoTrx("Uso").fechaTrx("10/08/2021 15:02:51").monto(700).estado("Valorizado").servicio("BUS -SERVICIO 1029").fechaVarolizacion("11/08/2021 02:30:03").build();
		
			listTrx.add(trx1);
			listTrx.add(trx2);
			listTrx.add(trx3);
			listTrx.add(trx4);
			listTrx.add(trx5);
			listTrx.add(trx6);
			listTrx.add(trx7);
			listTrx.add(trx8);
			
			List<Requests> listRequests = new ArrayList<>();
			Requests rqst1 = Requests.builder().tipoSolicitud("VIAJE").fechaSolicitud("11/08/2021 13:07:55").montoSaldo(1200).build();
			Requests rqst2 = Requests.builder().tipoSolicitud("VIAJE").fechaSolicitud("10/08/2021 15:56:21").montoSaldo(1000).build();
			Requests rqst3 = Requests.builder().tipoSolicitud("VIAJE").fechaSolicitud("10/08/2021 15:02:47").montoSaldo(1000).build();
			Requests rqst4 = Requests.builder().tipoSolicitud("VIAJE").fechaSolicitud("10/08/2021 13:31:38").montoSaldo(1000).build();
			
			listRequests.add(rqst1);
			listRequests.add(rqst2);
			listRequests.add(rqst3);
			listRequests.add(rqst4);
		
			
			result = QueryC2DResponse.builder().retornaEstado(0).msgRespuesta("OK").estadoCuenta("VIGENTE")
					.fechaEnrolamiento("2021-08-10 11:40:21").saldoVigente(1200).valorMontoMinimoCarga(0)
					.valorMontoMaximoCarga(48800).nuabt("de17e238090ffdb4").nroTarjetaExterno(9990000015L)
					.nroTarjetaInterno(268435592L).rut("7777777-7").listadoAPP(listApp).listaResumenTrx(listTrxResume)
					.listaTrx(listTrx).listaSolicitudes(listRequests).build();

		}else {
			
			result = QueryC2DResponse.builder().retornaEstado(22).msgRespuesta("Periodo de consulta no puede sobrepasar los 31dias").estadoCuenta(null)
					.fechaEnrolamiento(null).saldoVigente(0).valorMontoMinimoCarga(0)
					.valorMontoMaximoCarga(0).build();
		}
		

		return result;
	}
	
	

	

}
