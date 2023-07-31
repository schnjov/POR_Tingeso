package cl.usach.tingeso.gestionentradasservice.Services;

import cl.usach.tingeso.gestionentradasservice.Entities.EntradaEntity;
import cl.usach.tingeso.gestionentradasservice.Repositories.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;

    public EntradaEntity createEntrada(EntradaEntity entrada){
        return entradaRepository.save(entrada);
    }

    public List<EntradaEntity> createEntradas(List<EntradaEntity> entradas){
        return entradaRepository.saveAll(entradas);
    }

    public List<EntradaEntity> getEntradas(){
        return entradaRepository.findAll();
    }

    public List<EntradaEntity> getEntradasEntreFechas(String fecha1, String fecha2){
        return entradaRepository.findByFechaBetweenOrderByFecha(fecha1, fecha2);
    }

    public EntradaEntity createEntradaAutoDate(EntradaEntity entrada) {
        //Se setea la fecha con la fecha actual del sistema en formato dd-MM-yyyy
        entrada.setFecha(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        return entradaRepository.save(entrada);
    }
}
