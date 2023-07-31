package cl.usach.tingeso.gestionsalidasservice.Services;

import cl.usach.tingeso.gestionsalidasservice.Entities.SalidaEntity;
import cl.usach.tingeso.gestionsalidasservice.Repositories.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalidaServices {
    @Autowired
    private SalidaRepository salidaRepository;

    public List<SalidaEntity> getSalidas(){
        return salidaRepository.findAll();
    }

    public List<SalidaEntity> getSalidasBetween(String fecha1, String fecha2){
        return salidaRepository.findByFechaBetweenOrderByFecha(fecha1, fecha2);
    }

    public SalidaEntity saveSalida(SalidaEntity salidaEntity){
        return salidaRepository.save(salidaEntity);
    }

    public List<SalidaEntity> saveSalidas(List<SalidaEntity> salidaEntities){
        return salidaRepository.saveAll(salidaEntities);
    }

    public SalidaEntity saveSalidaAutoDate(SalidaEntity salidaEntity) {
        //Se setea la fecha con la fecha actual del sistema en formato dd-MM-yyyy
        salidaEntity.setFecha(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return salidaRepository.save(salidaEntity);
    }
}
