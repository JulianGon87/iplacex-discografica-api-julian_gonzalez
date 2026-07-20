package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(value = "/disco",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepository.existsById(disco.idArtista)) {
            return new ResponseEntity<>("El artista indicado no existe", HttpStatus.BAD_REQUEST);
        }
        Disco nuevo = discoRepository.save(disco);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping(value = "/discos",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> lista = discoRepository.findAll();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepository.findById(id)
                .map(d -> new ResponseEntity<Object>(d, HttpStatus.OK))
                .orElse(new ResponseEntity<>("Disco no encontrado", HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/artista/{id}/discos",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        List<Disco> lista = discoRepository.findDiscosByIdArtista(id);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}