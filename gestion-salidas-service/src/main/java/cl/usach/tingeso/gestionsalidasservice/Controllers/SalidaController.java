package cl.usach.tingeso.gestionsalidasservice.Controllers;

import cl.usach.tingeso.gestionsalidasservice.Entities.SalidaEntity;
import cl.usach.tingeso.gestionsalidasservice.Services.SalidaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/salidas")
@CrossOrigin(origins = "http://localhost:3000")
public class SalidaController {
    @Autowired
    private SalidaServices salidaServices;

    @GetMapping("/between")
    public List<SalidaEntity> getSalidasBetween(@RequestParam(value = "fechaInicio") String fechaInicio, @RequestParam(value = "fechaFin") String fechaFin) {
        return salidaServices.getSalidasBetween(fechaInicio, fechaFin);
    }

    @PostMapping("/create")
    public ResponseEntity<SalidaEntity> saveSalida(@RequestBody SalidaEntity salidaEntity){
        try{
            SalidaEntity _salidaEntity = salidaServices.saveSalida(salidaEntity);
            return ResponseEntity.created(new URI("/salidas/create" + _salidaEntity.getId())).body(_salidaEntity);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/create/autoDate")
    public ResponseEntity<SalidaEntity> saveSalidaAutoDate(@RequestBody SalidaEntity salidaEntity){
        try{
            SalidaEntity _salidaEntity = salidaServices.saveSalidaAutoDate(salidaEntity);
            return ResponseEntity.created(new URI("/salidas/create/autoDate" + _salidaEntity.getId())).body(_salidaEntity);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/createList")
    public ResponseEntity<List<SalidaEntity>> saveSalidas(@RequestBody List<SalidaEntity> salidaEntities){
        try{
            List<SalidaEntity> _salidaEntities = salidaServices.saveSalidas(salidaEntities);
            return ResponseEntity.created(new URI("/salidas/createList" + _salidaEntities.size())).body(_salidaEntities);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }
}
