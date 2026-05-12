package com.example.demo.repositories.usuarios;

import com.example.demo.models.usuarios.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Page<Usuario> findByNameContainingIgnoreCaseOrUsuarioIdContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name, String usuarioId, String phone, String email, Pageable pageable);
    long countByStatus(String status);
}
