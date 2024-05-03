package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episode {

    private Integer season;
    private String title;
    private Integer numberEpisode;
    private Double rating;
    private LocalDate releaseYear;

    //CONSTRUTOR PERSONALIZADO
    public Episode(Integer seasonNumber, DataEpisode dataEpisode) {
        this.season = seasonNumber;
        this.title = dataEpisode.title();
        this.numberEpisode = dataEpisode.number();

        try{
            this.rating = Double.valueOf(dataEpisode.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try{
            this.releaseYear = LocalDate.parse(dataEpisode.releaseDate());
        } catch (DateTimeException e) {
            this.releaseYear = null;
        }

    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumber() {
        return numberEpisode;
    }

    public void setNumber(Integer number) {
        this.numberEpisode = number;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "season = " + season +
                ", title = '" + title + '\'' +
                ", numberEpisode = " + numberEpisode +
                ", rating = " + rating +
                ", releaseYear = " + releaseYear;
    }
}
