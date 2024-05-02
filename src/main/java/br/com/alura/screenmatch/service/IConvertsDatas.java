package br.com.alura.screenmatch.service;

public interface IConvertsDatas {
    <T> T takeDatas(String json, Class<T> tClass);
}
