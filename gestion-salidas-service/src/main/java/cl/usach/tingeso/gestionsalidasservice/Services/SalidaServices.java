package cl.usach.tingeso.gestionsalidasservice.Services;

import cl.usach.tingeso.gestionsalidasservice.Entities.SalidaEntity;
import cl.usach.tingeso.gestionsalidasservice.Repositories.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SalidaServices {
    @Autowired
    private SalidaRepository salidaRepository;

    public List<SalidaEntity> getSalidas(){
        return salidaRepository.findAll();
    }

    public List<SalidaEntity> getSalidasBetween(String fecha1, String fecha2) {
        List<SalidaEntity> salidas = salidaRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate1 = LocalDate.parse(fecha1, formatter);
        LocalDate localDate2 = LocalDate.parse(fecha2, formatter);

        List<SalidaEntity> result = new ArrayList<>();
        for (SalidaEntity salida : salidas) {
            LocalDate salidaDate = LocalDate.parse(salida.getFecha(), formatter);
            if (!salidaDate.isBefore(localDate1) && !salidaDate.isAfter(localDate2)) {
                result.add(salida);
            }
        }

        // Ordenar la lista por fecha (día, mes, año)
        result.sort(Comparator.comparing(SalidaEntity::getFecha, Comparator.naturalOrder()));

        return result;
    }

    public SalidaEntity saveSalida(SalidaEntity salidaEntity){
        return salidaRepository.save(salidaEntity);
    }

    public List<SalidaEntity> saveSalidas(List<SalidaEntity> salidaEntities){
        return salidaRepository.saveAll(salidaEntities);
    }

    public SalidaEntity saveSalidaAutoDate(SalidaEntity salidaEntity) {
        //Se setea la fecha con la fecha actual del sistema en formato dd-MM-yyyy
        salidaEntity.setFecha(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return salidaRepository.save(salidaEntity);
    }
}
