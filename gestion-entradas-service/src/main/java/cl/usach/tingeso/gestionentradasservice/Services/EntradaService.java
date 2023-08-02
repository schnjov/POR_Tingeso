package cl.usach.tingeso.gestionentradasservice.Services;

import cl.usach.tingeso.gestionentradasservice.Entities.EntradaEntity;
import cl.usach.tingeso.gestionentradasservice.Repositories.EntradaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    private Logger logger = LoggerFactory.getLogger(EntradaService.class);

    public EntradaEntity createEntrada(EntradaEntity entrada){
        return entradaRepository.save(entrada);
    }

    public List<EntradaEntity> createEntradas(List<EntradaEntity> entradas){
        return entradaRepository.saveAll(entradas);
    }

    public List<EntradaEntity> getEntradas(){
        return entradaRepository.findAll();
    }

    public List<EntradaEntity> getEntradasEntreFechas(String fecha1, String fecha2) {
        logger.info("Obteniendo entradas entre fechas " + fecha1 + " y " + fecha2);
        List<EntradaEntity> entradas = entradaRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate1 = LocalDate.parse(fecha1, formatter);
        LocalDate localDate2 = LocalDate.parse(fecha2, formatter);

        List<EntradaEntity> result = new ArrayList<>();
        for (EntradaEntity entrada : entradas) {
            LocalDate entradaDate = LocalDate.parse(entrada.getFecha(), formatter);
            if (!entradaDate.isBefore(localDate1) && !entradaDate.isAfter(localDate2)) {
                result.add(entrada);
            }
        }

        // Ordenar la lista por fecha (día, mes, año)
        result.sort(Comparator.comparing(EntradaEntity::getFecha, Comparator.naturalOrder()));

        return result;
    }

    public EntradaEntity createEntradaAutoDate(EntradaEntity entrada) {
        // Se setea la fecha con la fecha actual del sistema en formato "dd-MM-yyyy"
        entrada.setFecha(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return entradaRepository.save(entrada);
    }
}
