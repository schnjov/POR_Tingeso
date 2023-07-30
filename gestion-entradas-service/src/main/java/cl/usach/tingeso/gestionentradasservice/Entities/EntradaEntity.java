package cl.usach.tingeso.gestionentradasservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class EntradaEntity {
    @Id
    private Long id;
    private String fecha;
    private String numeroRecibo;
    private Long monto;
}
