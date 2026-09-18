package br.com.alura.screenmanch.model;

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    CRIME("Crime");
    private String categoriaOmdb;
    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }
    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        try {
            throw new IllegalAccessException("Nenhuma categoria encontrada para a string fornecida: " + text);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
