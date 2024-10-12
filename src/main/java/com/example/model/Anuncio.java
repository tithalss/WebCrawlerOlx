package com.example.model;

public class Anuncio {
    private String titulo;
    private double valor;
    private String endereco;
    private String url;

    public Anuncio(String titulo, double valor, String endereco, String url) {
        this.titulo = titulo;
        this.valor = valor;
        this.endereco = endereco;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getValor() {
        return valor;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "titulo='" + titulo + '\'' +
                ", valor=" + valor +
                ", endereco='" + endereco + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
