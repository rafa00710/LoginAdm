package com.api.microservice.contoller;

import com.api.microservice.models.Funcao;
import com.api.microservice.models.Tabela;
import com.api.microservice.services.UserService;
import com.api.microservice.services.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController

public class UserController {

    final UserService userService;

    public  UserController(UserService userService) {
        this.userService = userService;
    }

   @PostMapping(value = "/cadastrar")
    public ResponseEntity<Object> save(@RequestBody Tabela tabela ) {

        var resposta = userService.save(tabela);

        return ResponseEntity.ok().body(resposta);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserDto user ) {

        var resposta = userService.login(user);

        return ResponseEntity.ok().body(resposta);
    }

    @GetMapping
    public  ResponseEntity<Object> getAllUsers(@RequestParam(value = "funcao")Funcao funcao){


        if (!funcao.equals(Funcao.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body( "Usuário não possui esse nivel de acesso" );
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.fidAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id")UUID id,
                                             @RequestParam(value = "funcao")Funcao funcao) {

        if (!funcao.equals(Funcao.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body( "Usuário não possui esse nivel de acesso" );
        }
       return new ResponseEntity<>(userService.fingById(id), HttpStatus.OK);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id")UUID id,
                                             @RequestParam(value = "funcao")Funcao funcao)  {


        if (!funcao.equals(Funcao.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body( "Usuário não possui esse nivel de acesso" );
        }

        Tabela tabela = userService.fingById(id);
        userService.delete(tabela);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> upDateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid Tabela user,
                                             @RequestParam(value = "funcao")Funcao funcao) {


        if (!funcao.equals(Funcao.ADMIN)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body( "Usuário não possui esse nivel de acesso" );
        }

        Tabela tabela = userService.fingById(id);
        tabela.setName(user.getName());
        tabela.setFuncao(user.getFuncao());
        tabela.setSenha(user.getSenha());

        return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
    }

}
