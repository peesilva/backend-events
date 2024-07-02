package com.example.estoque.eventos;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventosResponseDTO(UUID uuid, String companyId, String url, String number, EventosStatus status, LocalDateTime timestamp) {
    public EventosResponseDTO(Eventos eventos){
        this(eventos.getId(), eventos.getCompanyId(), eventos.getUrl(), eventos.getNumber(), eventos.getStatus(), eventos.getTimestamp());
    }
}