package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)      //ignora as propriedades/objetos não representadas na classe
public record DataSeason(String title,
                         @JsonAlias("Episode") Integer number,
                         @JsonAlias("imdbRating") String rating,
                         @JsonAlias("Released") String releaseDate) {
}
