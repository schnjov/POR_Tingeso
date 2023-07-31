package cl.usach.tingeso.resumenservice.Controller;

import cl.usach.tingeso.resumenservice.Models.EntradaModel;
import cl.usach.tingeso.resumenservice.Models.POJO.ResumenResponse;
import cl.usach.tingeso.resumenservice.Models.POJO.TransaccionPOJO;
import cl.usach.tingeso.resumenservice.Models.SalidaModel;
import cl.usach.tingeso.resumenservice.Services.ResumenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/resumen")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumenController {

    @Autowired
    private ResumenService resumenService;

    @GetMapping("/generar")
    public ResponseEntity<ResumenResponse> generarResumen(
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) {
        try {
            ResumenResponse response = resumenService.generarResumen(fechaInicio, fechaFin);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).build();
        }
    }

}

