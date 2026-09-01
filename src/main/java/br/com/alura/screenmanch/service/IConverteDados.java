package br.com.alura.screenmanch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
