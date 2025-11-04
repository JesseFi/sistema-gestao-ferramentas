package br.edu.infnet.jessefigueroapi.loaders;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Ferramenta;
import br.edu.infnet.jessefigueroapi.model.service.impl.FerramentaServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

@Order(1)
@Component
public class FerramentaLoader implements ApplicationRunner {

    @Value("${path.ferramentas}")
    private String path;

    private final FerramentaServiceImpl ferramentaService;

    public FerramentaLoader(FerramentaServiceImpl ferramentaService) {
        this.ferramentaService = ferramentaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);

            String line = reader.readLine();
            String[] fields = null;

            while(line != null) {

                fields = line.split(";");

                Ferramenta ferramenta = new Ferramenta();
                ferramenta.setNome(fields[0]);
                ferramenta.setTipo(fields[1]);
                ferramenta.setFabricante(fields[2]);
                ferramenta.setPreco(Double.valueOf(fields[3]));
                ferramenta.setNumeroSerie(fields[4]);
                ferramenta.setDescricao(fields[5]);
                ferramenta.setDisponivel(Boolean.valueOf(fields[6]));

                ferramentaService.insert(new FerramentaRequestDTO(ferramenta));

                line = reader.readLine();
            }

            Collection<FerramentaResponseDTO> ferramentas = ferramentaService.findAll();

            ferramentas.forEach(System.out::println);

            System.out.println("[FerramentaLoader] Carga de dados concluída! Total: " + ferramentas.size() + " ferramentas cadastradas.");

            reader.close();
        } catch (IOException e) {
            System.err.println("[FerramentaLoader] Erro ao carregar dados: " + e.getMessage());
        }
    }
}