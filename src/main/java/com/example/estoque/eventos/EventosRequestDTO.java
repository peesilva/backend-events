package com.example.estoque.eventos;

public record EventosRequestDTO(String companyId, String url, String number, EventosStatus status) {
    public EventosRequestDTO(String companyId, String url, String number) {
        this(companyId, url, number, null); // Chama o construtor mais específico com status null
    }

    // Construtor alternativo para garantir que o status não seja definido automaticamente
    public EventosRequestDTO(String companyId, String url, String number, EventosStatus status) {
        this.companyId = companyId;
        this.url = url;
        this.number = number;
        this.status = status;
    }

    // Getters omitidos para brevidade
}