package cl.usach.tingeso.resumenservice.Services;

import cl.usach.tingeso.resumenservice.Models.EntradaModel;
import cl.usach.tingeso.resumenservice.Models.POJO.TransaccionPOJO;
import cl.usach.tingeso.resumenservice.Models.SalidaModel;
import cl.usach.tingeso.resumenservice.Services.Factory.TransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ResumenService {
    @Autowired
    private RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8080";

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
            String url = BASE_URL + "/entrada/between?fechaInicio=" + dateFormat.format(dateFechaInicio)
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
            String url = BASE_URL + "/salidas/between?fechaInicio=" + dateFormat.format(dateFechaInicio)
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

    public List<TransaccionPOJO> generarResumen(String fechaInicio, String fechaFin) {
        try {
            List<EntradaModel> entradas = getEntradasBetweenDates(fechaInicio, fechaFin);
            List<SalidaModel> salidas = getSalidasBetweenDates(fechaInicio, fechaFin);
            return calcularTransacciones(entradas, salidas);
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
                    Long saldo;
                    if (transacciones.size() > 0) {
                        saldo = transacciones.get(transacciones.size() - 1).getSaldo();
                        saldo = saldo + entradas.get(idxEntrada).getMonto();
                    } else {
                        saldo = entradas.get(idxEntrada).getMonto();
                    }
                    transacciones.add(TransactionFactory.build(1, entradas.get(idxEntrada).getFecha(), 0,
                            entradas.get(idxEntrada).getNumeroRecibo(), "Ingreso a Caja", entradas.get(idxEntrada).getMonto(), saldo));
                    logger.info(transacciones.get(transacciones.size() - 1).toString());
                    idxEntrada++;
                } else {
                    Long saldo;
                    if (transacciones.size() > 0) {
                        saldo = transacciones.get(transacciones.size() - 1).getSaldo();
                        saldo = saldo - salidas.get(idxSalida).getMonto();
                    } else {
                        saldo = -(salidas.get(idxSalida).getMonto());
                    }
                    transacciones.add(TransactionFactory.build(2, salidas.get(idxSalida).getFecha(), salidas.get(idxSalida).getTipoDocumento(),
                            salidas.get(idxSalida).getNumeroDocumento(), salidas.get(idxSalida).getMotivoSalida(), salidas.get(idxSalida).getMonto(), saldo));
                    logger.info(transacciones.get(transacciones.size() - 1).toString());
                    idxSalida++;
                }
            } else if (idxEntrada < entradas.size()) {
                Long saldo;
                if (transacciones.size() > 0) {
                    saldo = transacciones.get(transacciones.size() - 1).getSaldo();
                    saldo = saldo + entradas.get(idxEntrada).getMonto();
                } else {
                    saldo = entradas.get(idxEntrada).getMonto();
                }
                transacciones.add(TransactionFactory.build(1, entradas.get(idxEntrada).getFecha(), 0,
                        entradas.get(idxEntrada).getNumeroRecibo(), "Ingreso a Caja", entradas.get(idxEntrada).getMonto(), saldo));
                logger.info(transacciones.get(transacciones.size() - 1).toString());
                idxEntrada++;
            } else if (idxSalida < salidas.size()) {
                Long saldo;
                if (transacciones.size() > 0) {
                    saldo = transacciones.get(transacciones.size() - 1).getSaldo();
                    saldo = saldo - salidas.get(idxSalida).getMonto();
                } else {
                    saldo = -(salidas.get(idxSalida).getMonto());
                }
                transacciones.add(TransactionFactory.build(2, salidas.get(idxSalida).getFecha(), salidas.get(idxSalida).getTipoDocumento(),
                        salidas.get(idxSalida).getNumeroDocumento(), salidas.get(idxSalida).getMotivoSalida(), salidas.get(idxSalida).getMonto(), saldo));
                logger.info(transacciones.get(transacciones.size() - 1).toString());
                idxSalida++;
            }
        }
        return transacciones;
    }
}
