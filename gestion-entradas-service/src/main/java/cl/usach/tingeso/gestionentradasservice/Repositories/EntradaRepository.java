package cl.usach.tingeso.gestionentradasservice.Repositories;

import cl.usach.tingeso.gestionentradasservice.Entities.EntradaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EntradaRepository extends JpaRepository<EntradaEntity, Long> {
    List<EntradaEntity> findByFechaBetween(String fecha1, String fecha2);
}
