package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class UsuarioConverter {

    // Busca dados da UsuarioDTO e joga na entity Usuario////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();

    }
    // FORMA 1 DE FAZER
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecoDTOS) {
            enderecos.add(paraEndereco(enderecoDTO));

        }
        return enderecos;
    }


    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    // FORMA 2 DE FAZER É IGUAL O FOR ACIMA DO ENDERECO stream map
    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();


    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }


    // // /// Busca dados da Usuario  e joga na   UsuarioDTO ////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();

    }


    // FORMA 1 DE FAZER
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> endereco) {
        List<EnderecoDTO> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : endereco) {
            enderecos.add(paraEnderecoDTO(enderecoDTO));

        }
        return enderecos;
    }


    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    // FORMA 2 DE FAZER

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDTO).toList();


    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha()  != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();


    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() !=null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .build();

    }

    public Telefone updateTelefone ( TelefoneDTO dto, Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

    public Endereco paraEnderecoEntity (EnderecoDTO enderecoDTO, Long idUsuario){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .cep(enderecoDTO.getCep())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity (TelefoneDTO telefoneDTO, Long idUsuario){
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .usuario_id(idUsuario)
                .build();
    }

}
