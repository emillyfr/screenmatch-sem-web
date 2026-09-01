package br.com.alura.screenmanch.service;

import br.com.alura.screenmanch.model.DadosSerie;
import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {

        return mapper.readValue(json, classe);
    }
}


