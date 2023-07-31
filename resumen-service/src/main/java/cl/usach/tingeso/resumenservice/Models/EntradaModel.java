package cl.usach.tingeso.resumenservice.Models;

import lombok.Data;

@Data
public class EntradaModel {
    private Long id;
    private String fecha;
    private String numeroRecibo;
    private Long monto;
}
