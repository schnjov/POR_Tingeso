package cl.usach.tingeso.resumenservice.Models.POJO;

import lombok.Data;

import java.util.List;
@Data
public class ResumenResponse {
    private List<TransaccionPOJO> resumen;
    private Long ingresos;
    private Long salidas;

    public ResumenResponse(List<TransaccionPOJO> resumen, Long ingresos, Long salidas) {
        this.resumen = resumen;
        this.ingresos = ingresos;
        this.salidas = salidas;
    }
}
