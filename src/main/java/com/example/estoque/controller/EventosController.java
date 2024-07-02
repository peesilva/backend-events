package com.example.estoque.controller;

import com.example.estoque.eventos.*;
import com.example.estoque.eventos.Eventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("eventos")
public class EventosController {

    @Autowired
    private EventosRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<EventosResponseDTO> saveCar(@RequestBody EventosRequestDTO data) {
        Eventos eventosData = new Eventos(data);
        repository.save(eventosData);
        EventosResponseDTO responseDTO = new EventosResponseDTO(eventosData);
        return ResponseEntity.ok(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/databaseH2-start==")
    public void defaultCar() {
        List<Eventos> carsToAdd = List.of(
                new Eventos(new EventosRequestDTO("12.345.678/0001-90", "https://example.com/url1", "NB-3344")),
                new Eventos(new EventosRequestDTO("98.765.432/0001-55", "https://example.com/url2", "NB-4455")),
                new Eventos(new EventosRequestDTO("11.222.333/0001-44", "https://example.com/url3", "NB-5443")),
                new Eventos(new EventosRequestDTO("21.312.415/0001-66", "https://example.com/url4", "NB-1234"))
        );
        repository.saveAll(carsToAdd);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<EventosResponseDTO> getAllCars() {
        List<EventosResponseDTO> carList = repository.findAll().stream().map(EventosResponseDTO::new).toList();
        return carList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<EventosResponseDTO> getCarById(@PathVariable UUID id) {
        Eventos eventos = repository.findById(id).orElse(null);
        if (eventos == null) {
            return ResponseEntity.notFound().build();
        }
        EventosResponseDTO responseDTO = new EventosResponseDTO(eventos);
        return ResponseEntity.ok(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/{id}/reservar")
    public ResponseEntity<EventosResponseDTO> reserveCar(@PathVariable UUID id) {
        Eventos eventos = repository.findById(id).orElse(null);

        if (eventos == null) {
            return ResponseEntity.notFound().build();
        }

        if (eventos.getStatus() != EventosStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body(new EventosResponseDTO(eventos));
        }

        eventos.setStatus(EventosStatus.RESERVED);
        repository.save(eventos);

        EventosResponseDTO responseDTO = new EventosResponseDTO(eventos);
        return ResponseEntity.ok(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/{id}")
    public ResponseEntity<EventosResponseDTO> updateCar(@PathVariable UUID id, @RequestBody EventosRequestDTO data) {
        Eventos eventos = repository.findById(id).orElse(null);

        if (eventos != null) {
            if (data.companyId() != null) {
                eventos.setCompanyId(data.companyId());
            }
            if (data.url() != null) {
                eventos.setUrl(data.url());
            }
            if (data.number() != null) {
                eventos.setNumber(data.number());
            }
            if (data.status() != null) {
                eventos.setStatus(data.status());
            }
            repository.save(eventos);
        EventosResponseDTO responseDTO = new EventosResponseDTO(eventos);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping("/{id}/vender")
    public ResponseEntity<EventosResponseDTO> sellCar(@PathVariable UUID id) {
        Eventos eventos = repository.findById(id).orElse(null);

        if (eventos == null) {
            return ResponseEntity.notFound().build();
        }

        if (eventos.getStatus() != EventosStatus.AVAILABLE && eventos.getStatus() != EventosStatus.RESERVED) {
            return ResponseEntity.badRequest().body(new EventosResponseDTO(eventos));
        }

        eventos.setStatus(EventosStatus.SOLD);
        repository.save(eventos);

        EventosResponseDTO responseDTO = new EventosResponseDTO(eventos);
        return ResponseEntity.ok(responseDTO);
    }
}