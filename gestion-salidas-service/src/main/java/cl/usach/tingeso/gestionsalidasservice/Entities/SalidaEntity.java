package cl.usach.tingeso.gestionsalidasservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class SalidaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fecha;
    private int tipoDocumento;
    private String numeroDocumento;
    private String motivoSalida;
    private Long monto;
}
