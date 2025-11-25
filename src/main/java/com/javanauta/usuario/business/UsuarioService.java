package com.javanauta.usuario.business;


import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }
    public void emailExiste(String email) {
        try {
            // Chama o método que consulta o banco
            boolean existe = verificaEmailExistente(email);

            // Se já existir, lança exceção com a mensagem + e-mail duplicado
            if (existe) {
                throw new ConflictException("2 - Email já cadastrado" + email);
            }

        } catch (ConflictException e) {
            // Caso a exceção acima seja lançada, ela é capturada aqui
            // e relançada novamente, mas agora com getCause (normalmente null)
            throw new ConflictException("3 - Email já cadastrado" + e.getCause());
        }
    }

    // Método que consulta o banco para verificar se o e-mail já está cadastrado
    public boolean verificaEmailExistente(String email) {
        // Retorna true se o e-mail existir, false se não existir
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado " + email));


    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);


    }

 }
