package br.edu.infnet.jessefigueroapi.loaders;

import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.ferramenta.FerramentaResponseDTO;
import br.edu.infnet.jessefigueroapi.model.service.impl.FerramentaServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
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

        try (FileReader file = new FileReader(path);
             BufferedReader reader = new BufferedReader(file)) {

            String line = reader.readLine();
            String[] campos;

            while(line != null) {

                campos = line.split(";");

                FerramentaRequestDTO ferramentaRequestDTO = new FerramentaRequestDTO();

                ferramentaRequestDTO.setNome(campos[0]);
                ferramentaRequestDTO.setTipo(campos[1]);
                ferramentaRequestDTO.setFabricante(campos[2]);
                ferramentaRequestDTO.setPreco(Double.valueOf(campos[3]));
                ferramentaRequestDTO.setNumeroSerie(campos[4]);
                ferramentaRequestDTO.setDataAquisicao(LocalDateTime.parse(campos[5]));
                ferramentaRequestDTO.setDisponivel(Boolean.valueOf(campos[6]));
                ferramentaRequestDTO.setDescricao(campos[7]);

                ferramentaService.insert(ferramentaRequestDTO);

                line = reader.readLine();
            }

            Collection<FerramentaResponseDTO> ferramentas = ferramentaService.findAll();
            ferramentas.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("[FerramentaLoader] Erro ao carregar dados: " + e.getMessage());
        }
    }
}