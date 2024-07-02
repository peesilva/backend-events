package com.example.estoque.eventos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Eventos {

    @Id
    @GeneratedValue
    private UUID id;

    private String companyId;

    private String url;

    private String number;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private EventosStatus status;

    public Eventos(EventosRequestDTO data) {
        this.id = UUID.randomUUID();
        this.url = data.url();
        this.companyId = data.companyId();
        this.number = data.number();
        this.timestamp = LocalDateTime.now();
        this.status = EventosStatus.AVAILABLE; // Definindo como disponível por padrão
    }
}