package cl.usach.tingeso.gestionentradasservice.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entrada")
//Se habilita cors para react
@CrossOrigin(origins = "http://localhost:3000")
public class EntradaController {
    @RequestMapping("/test")
    public String test(@RequestParam(value="name", defaultValue="World") String name) {
        return "{\"id\":1,\"fecha\":\"2021-06-01\",\"numeroRecibo\":\"123456789\",\"monto\":10000}";
    }
}
