package com.javanauta.usuario.business;


import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;




    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {

        emailExiste(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        // CONVERTER USUARIO DTO PARA ENTITY
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

    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("1 - Email não encontrado " + email)));


        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("2 - Email não encontrado " + email);

        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);


    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {

        // extraí o email do token!!! retirando a string Bearer
        String email = jwtUtil.extractUsername(token.substring(7));

        // criptografia de senha, só se foi alterada
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        // agora que tem o email, vai buscar o usuario na base
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não cadastrado!"));

        // mesclou dados do DTO x dados da base de dados
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);


        // SALVOU OS DADOS DO USUARIO CONVERTIDO E DEPOIS PEGOU O RETORNO E CONVERTEU PARA USUARIO DTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow( () ->
                new ResourceNotFoundException("Id endereco não encontrado " + idEndereco ));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));

    }
    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow( () ->
                new ResourceNotFoundException("id telefone não encontrado " + idTelefone ));

        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

    }


}