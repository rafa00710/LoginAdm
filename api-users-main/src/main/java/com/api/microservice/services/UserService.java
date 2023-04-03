package com.api.microservice.services;

import com.api.microservice.models.Funcao;
import com.api.microservice.models.Tabela;
import com.api.microservice.repositories.UserRepository;
import com.api.microservice.services.dtos.UserDto;
import com.api.microservice.services.execptionhandler.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

   // @Autowired
   // AuthFeing authFeing;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Tabela save(Tabela user) {
        var encodedPassword = this.passwordEncoder.encode(user.getSenha());
        user.setSenha(encodedPassword);
        return userRepository.save(user);
    }

    public Funcao login(UserDto userDto){
        var res = userRepository.findByNameAndSenha(userDto.getName(),userDto.getSenha());
        if (res == null ){
            throw new RuntimeException("Usuario não cadastrado");
        }
        return res.getFuncao();

    }

    public List<Tabela> fidAll() {
        return userRepository.findAll();
    }




    public Tabela fingById(UUID id) {
        Optional<Tabela> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public void delete(Tabela tabela) {
        fingById(tabela.getId());
        userRepository.delete(tabela);
    }




  /* public boolean validateToken(String token) {
       return authFeing.validateToken(token);
    }

   public String getTypeUser(String token) {
        return authFeing.getTypeUser(token);
    }*/
}
