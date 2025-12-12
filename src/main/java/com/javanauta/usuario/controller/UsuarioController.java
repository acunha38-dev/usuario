package com.javanauta.usuario.controller;

import com.javanauta.usuario.business.UsuarioService;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

    // Response Entity as respostas padrão HTTP


    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // login
    // Criando e definindo (assinando) o método login na Controller.
    // O Spring chamará este método quando chegar um POST em /login.
    @PostMapping("/login")
       public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );

        return "Bearer " + jwtUtil.generateToken(authentication.getName());


    }

    // padrão post para SALVAR USUARIO!!!!
    //É a assinatura do método salvaUsuario na controller
    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));

    }


    // Padrão para consulta
    //É a assinatura do método buscaUsuarioPorEmail na controller
    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));

    }
    //É a assinatura do método deleteUsuarioPorEmail na controller
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();

    }

    //UPDATE NA BASE
    @PutMapping
    //É a assinatura do método atualizaDadosUsuario na controller
     public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestHeader("Authorization") String token,
                                                           @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));

    }

    @PutMapping("/endereco")
    //É a assinatura do método atualizaDadosUsuario na controller
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestParam("id") Long id) {

        return ResponseEntity.ok(usuarioService.atualizaEndereco(id,enderecoDTO));
    }

    @PutMapping("/telefone")
    //É a assinatura do método atualizaDadosUsuario na controller
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                            @RequestParam("id") Long id) {

        return ResponseEntity.ok(usuarioService.atualizaTelefone(id,telefoneDTO));
    }
}
