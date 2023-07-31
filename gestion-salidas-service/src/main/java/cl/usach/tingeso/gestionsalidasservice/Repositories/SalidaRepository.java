package cl.usach.tingeso.gestionsalidasservice.Repositories;

import cl.usach.tingeso.gestionsalidasservice.Entities.SalidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalidaRepository extends JpaRepository<SalidaEntity, Long> {

    List<SalidaEntity> findByFechaBetweenOrderByFecha(String fecha1, String fecha2);
}
