package cl.usach.tingeso.resumenservice.Services;

import cl.usach.tingeso.resumenservice.Models.EntradaModel;
import cl.usach.tingeso.resumenservice.Models.POJO.ResumenResponse;
import cl.usach.tingeso.resumenservice.Models.POJO.TransaccionPOJO;
import cl.usach.tingeso.resumenservice.Models.SalidaModel;
import cl.usach.tingeso.resumenservice.Services.Factory.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Configuration
public class ResumenService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${base.url}")
    private String BASE_URL;

    private final Logger logger = LoggerFactory.getLogger(ResumenService.class);

    public List<EntradaModel> getEntradasBetweenDates(String fechaInicio, String fechaFin) {
        try {
            // Convertir las fechas de String a Date utilizando SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateFechaInicio = dateFormat.parse(fechaInicio);
            Date dateFechaFin = dateFormat.parse(fechaFin);
            logger.info("Fecha inicio: " + dateFormat.format(dateFechaInicio));
            logger.info("Fecha fin: " + dateFormat.format(dateFechaFin));
            // Construir la URL con los parámetros de consulta
            String url = "http://34.95.244.48:8082/entrada/between?fechaInicio=" + dateFormat.format(dateFechaInicio)
                    + "&fechaFin=" + dateFormat.format(dateFechaFin);
            logger.info("URL: " + url);

            // Realizar la solicitud HTTP GET y obtener la lista de EntradaModel
            URI uri = new URI(url);
            EntradaModel[] entradaArray = restTemplate.getForObject(uri, EntradaModel[].class);

            // Convertir el array de entrada a una lista y devolverla
            assert entradaArray != null;
            return Arrays.asList(entradaArray);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores, si es necesario
            return null; // o lanzar una excepción
        }
    }

    public List<SalidaModel> getSalidasBetweenDates(String fechaInicio, String fechaFin) {
        try {
            // Convertir las fechas de String a Date utilizando SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateFechaInicio = dateFormat.parse(fechaInicio);
            Date dateFechaFin = dateFormat.parse(fechaFin);
            logger.info("Fecha inicio: " + dateFormat.format(dateFechaInicio));
            logger.info("Fecha fin: " + dateFormat.format(dateFechaFin));
            // Construir la URL con los parámetros de consulta
            String url ="http://34.95.179.207:8083/salidas/between?fechaInicio=" + dateFormat.format(dateFechaInicio)
                    + "&fechaFin=" + dateFormat.format(dateFechaFin);

            logger.info("URL: " + url);

            // Realizar la solicitud HTTP GET y obtener la lista de EntradaModel
            URI uri = new URI(url);
            SalidaModel[] salidaArray = restTemplate.getForObject(uri, SalidaModel[].class);

            // Convertir el array de entrada a una lista y devolverla
            assert salidaArray != null;
            return Arrays.asList(salidaArray);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores, si es necesario
            return null; // o lanzar una excepción
        }
    }

    public ResumenResponse generarResumen(String fechaInicio, String fechaFin) {
        logger.info("Generando resumen entre fechas " + fechaInicio + " y " + fechaFin);
        try {
            List<EntradaModel> entradas = getEntradasBetweenDates(fechaInicio, fechaFin);
            List<SalidaModel> salidas = getSalidasBetweenDates(fechaInicio, fechaFin);
            List<TransaccionPOJO> resumen = calcularTransacciones(entradas, salidas);
            logger.info("RESUMEN GENERADO");
            Long ingresos = 0L;
            Long egresos = 0L;
            for (TransaccionPOJO transaccion : resumen) {
                if (transaccion.getTipoTransaccion() == 1) {
                    ingresos += transaccion.getMonto();
                } else {
                    egresos += transaccion.getMonto();
                }
            }

            return new ResumenResponse(resumen, ingresos, egresos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<TransaccionPOJO> calcularTransacciones(List<EntradaModel> entradas, List<SalidaModel> salidas) {
        List<TransaccionPOJO> transacciones = new ArrayList<>();
        logger.info("GENERANDO RESUMEN");
        int idxEntrada = 0;
        int idxSalida = 0;

        while (idxEntrada < entradas.size() || idxSalida < salidas.size()) {
            if (idxEntrada < entradas.size() && idxSalida < salidas.size()) {
                if (DateComparator.isFirstDateBeforeSecond(entradas.get(idxEntrada).getFecha(), salidas.get(idxSalida).getFecha())) {
                    generarTransaccionEntrada(transacciones, entradas.get(idxEntrada));
                    idxEntrada++;
                } else {
                    generarTransaccionSalida(transacciones, salidas.get(idxSalida));
                    idxSalida++;
                }
            } else if (idxEntrada < entradas.size()) {
                generarTransaccionEntrada(transacciones, entradas.get(idxEntrada));
                idxEntrada++;
            } else if (idxSalida < salidas.size()) {
                generarTransaccionSalida(transacciones, salidas.get(idxSalida));
                idxSalida++;
            }
        }

        return transacciones;
    }

    private void generarTransaccionEntrada(List<TransaccionPOJO> transacciones, EntradaModel entrada) {
        Long saldo = 0L;
        if (!transacciones.isEmpty()) {
            saldo = transacciones.get(transacciones.size() - 1).getSaldo();
        }
        saldo += entrada.getMonto();
        transacciones.add(TransactionFactory.build(1, entrada.getFecha(), 0,
                entrada.getNumeroRecibo(), "Ingreso a Caja", entrada.getMonto(), saldo));
        logger.info(transacciones.get(transacciones.size() - 1).toString());
    }

    private void generarTransaccionSalida(List<TransaccionPOJO> transacciones, SalidaModel salida) {
        Long saldo = 0L;
        if (!transacciones.isEmpty()) {
            saldo = transacciones.get(transacciones.size() - 1).getSaldo();
        }
        saldo -= salida.getMonto();
        transacciones.add(TransactionFactory.build(2, salida.getFecha(), salida.getTipoDocumento(),
                salida.getNumeroDocumento(), salida.getMotivoSalida(), salida.getMonto(), saldo));
        logger.info(transacciones.get(transacciones.size() - 1).toString());
    }

}
