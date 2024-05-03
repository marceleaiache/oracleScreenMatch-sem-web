package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DataEpisode;
import br.com.alura.screenmatch.model.DataSeason;
import br.com.alura.screenmatch.model.DataSerie;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.service.APIConsumption;
import br.com.alura.screenmatch.service.ConvertsDatas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    private Scanner sc = new Scanner(System.in);

    private APIConsumption consumption = new APIConsumption();
    private ConvertsDatas converter = new ConvertsDatas();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6a7fcf42";

    public void showMenu() {
        System.out.println("Digite o nome da série: ");
        var serieTitle = sc.nextLine();
        var realAddress = ADDRESS + serieTitle.replace(" ", "+") + API_KEY;
        var json = consumption.takeDatas(realAddress);

        DataSerie datas = converter.takeDatas(json, DataSerie.class);
        System.out.println(datas);

        List<DataSeason> seasons = new ArrayList<>();
        for (int i=1; i<=datas.totalSeasons(); i++) {
            json = consumption.takeDatas(ADDRESS + serieTitle.replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = converter.takeDatas(json, DataSeason.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);

// TODO ESSE CODIGO FOI FEITO ATRAVES DA FUNÇÃO LAMBDA ABAIXO

//        for (int i=0; i<datas.totalSeasons(); i++) {
//            List<DataEpisode> episodesSeason = seasons.get(i).episode();
//
//            for (int j=0; j<episodesSeason.size(); j++) {
//                System.out.println(episodesSeason.get(j).title());
//            }
//        }
//
        seasons.forEach(t -> t.episodes().forEach(e -> System.out.println(e.title())));

        List<DataEpisode> dataEpisodes = seasons.stream()
                .flatMap(t -> t.episodes().stream())
                .collect(Collectors.toList());

//        System.out.println();
//        System.out.println("Top 10 Episódios: ");
//        dataEpisodes.stream()
//                .filter(e -> !e.rating()        .equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primeiro filtro (N/A) " + e))
//                .sorted(Comparator.comparing(DataEpisode::rating).reversed())
//                .peek(e -> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek(e -> System.out.println("Limite " + e))
//                .map(e -> e.title().toUpperCase())
//                .peek(e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episode> epsisodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.seasonNumber(), d))
                ).collect(Collectors.toList());

        epsisodes.forEach(System.out::println);

        System.out.println("Digite um trecho do título do episódio: ");
        var fromTitle = sc.nextLine();

        Optional<Episode> episodeSearched = epsisodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(fromTitle.toUpperCase()))
                .findFirst();

        if (episodeSearched.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodeSearched.get().getSeason());
        }
        else {
            System.out.println("Episódio não encontrado!");
        }


//
//        System.out.println("A partir de que ano você deseja ver os episódios?");
//        var year = sc.nextInt();
//        sc.nextLine();
//
//        LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//        //formatação da data
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        epsisodes.stream()
//                .filter(e -> e.getReleaseYear()  != null && e.getReleaseYear().isAfter(searchDate))
//                .forEach(e -> System.out.println(
//                        " Temporada " + e.getSeason() +
//                        " Data de Lançamento: " + e.getReleaseYear().format(formatter)
//                ));

        Map<Integer, Double>  ratingBySeason = epsisodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason, Collectors.averagingDouble(Episode::getRating)));

        System.out.println(ratingBySeason);

        DoubleSummaryStatistics est = epsisodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade de episódio avaliado: " + est.getCount());


    }
}
