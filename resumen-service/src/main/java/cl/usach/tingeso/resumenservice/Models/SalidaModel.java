package cl.usach.tingeso.resumenservice.Models;

import lombok.Data;

@Data
public class SalidaModel {
    private Long id;
    private String fecha;
    private int tipoDocumento;
    private String numeroDocumento;
    private String motivoSalida;
    private Long monto;
}
