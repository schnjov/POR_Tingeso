package cl.usach.tingeso.resumenservice.Services.Factory;

import cl.usach.tingeso.resumenservice.Models.POJO.TransaccionPOJO;

public class TransactionFactory {
    //Constructor con los siguientes parametros
    /*tipoTransaccion, fecha, tipoDocumento, numeroDocumento, motivo, monto, saldo*/
    public static TransaccionPOJO build(int tipoTransaccion, String fecha, int tipoDocumento, String numeroDocumento, String motivo, Long monto, Long saldo) {
        TransaccionPOJO transaccionPOJO = new TransaccionPOJO();
        transaccionPOJO.setTipoTransaccion(tipoTransaccion);
        transaccionPOJO.setFecha(fecha);
        transaccionPOJO.setTipoDocumento(tipoDocumento);
        transaccionPOJO.setNumeroDocumento(numeroDocumento);
        transaccionPOJO.setMotivo(motivo);
        transaccionPOJO.setMonto(monto);
        transaccionPOJO.setSaldo(saldo);
        return transaccionPOJO;
    }
}
