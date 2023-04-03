package com.api.microservice.services.dtos;

import com.api.microservice.models.Funcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetuserDto {
    private String senha;
    private String name;
    private Funcao funcao;


}
