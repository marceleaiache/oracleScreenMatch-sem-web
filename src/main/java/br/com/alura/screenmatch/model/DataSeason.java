package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeason(@JsonAlias("Season") Integer seasonNumber,
                         @JsonAlias("Episodes") List<DataEpisode> episodes) {
}
