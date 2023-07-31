package cl.usach.tingeso.resumenservice.Models.POJO;

import lombok.Data;

@Data
public class TransaccionPOJO {
    private int tipoTransaccion;
    private String fecha;
    private int tipoDocumento;
    private String numeroDocumento;
    private String motivo;
    private Long monto;
    private Long saldo;
}
