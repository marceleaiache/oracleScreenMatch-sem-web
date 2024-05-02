package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DataSerie;
import br.com.alura.screenmatch.service.APIConsumption;
import br.com.alura.screenmatch.service.ConvertsDatas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		APIConsumption consumption = new APIConsumption();
		var json = consumption.takeDatas("http://www.omdbapi.com/?t=gilmore%20girls&apikey=6a7fcf42");
		System.out.println(json);
		ConvertsDatas converter = new ConvertsDatas();

		DataSerie data = converter.takeDatas(json, DataSerie.class);
		System.out.println(data);
	}

}
