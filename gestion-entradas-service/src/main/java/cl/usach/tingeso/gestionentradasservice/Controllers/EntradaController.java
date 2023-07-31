package cl.usach.tingeso.gestionentradasservice.Controllers;

import cl.usach.tingeso.gestionentradasservice.Entities.EntradaEntity;
import cl.usach.tingeso.gestionentradasservice.Services.EntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/entrada")
//Se habilita cors para react
@CrossOrigin(origins = "http://localhost:3000")
public class EntradaController {
    @Autowired
    private EntradaService entradaService;
    @GetMapping("/between")
    public List<EntradaEntity> getEntradasBetween(@RequestParam(value = "fechaInicio") String fechaInicio, @RequestParam(value = "fechaFin") String fechaFin) {
        return entradaService.getEntradasEntreFechas(fechaInicio, fechaFin);
    }

    @PostMapping("/create")
    public ResponseEntity<EntradaEntity> createEntrada(@RequestBody EntradaEntity entrada){
        try{
            EntradaEntity entradaEntity = entradaService.createEntrada(entrada);
            return ResponseEntity.created(new URI("/entrada/create"+entradaEntity.getId())).body(entradaEntity);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/create/autoDate")
    public ResponseEntity<EntradaEntity> createEntradaAutoDate(@RequestBody EntradaEntity entrada){
        try{
            EntradaEntity entradaEntity = entradaService.createEntradaAutoDate(entrada);
            return ResponseEntity.created(new URI("/entrada/create"+entradaEntity.getId())).body(entradaEntity);
        }catch (Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @PostMapping("/createList")
    public List<EntradaEntity> createEntradas(@RequestBody List<EntradaEntity> entradas){
        return entradaService.createEntradas(entradas);
    }

}
