package com.example.estoque.eventos;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EventosRepository extends JpaRepository<Eventos, UUID> {
}