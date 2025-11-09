package br.edu.infnet.jessefigueroapi.loaders;

import br.edu.infnet.jessefigueroapi.clients.ViaCepClient;
import br.edu.infnet.jessefigueroapi.dtos.endereco.EnderecoResponseDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.mapper.EnderecoMapper;
import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import br.edu.infnet.jessefigueroapi.model.enums.StatusFuncionario;
import br.edu.infnet.jessefigueroapi.model.repository.EnderecoRepository;
import br.edu.infnet.jessefigueroapi.model.service.impl.FuncionarioServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

@Order(2)
@Component
public class FuncionarioLoader implements ApplicationRunner {
    @Value("${path.funcionarios}")
    private String path;

    private final FuncionarioServiceImpl funcionarioService;
    private final ViaCepClient viaCepClient;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepository;

    public FuncionarioLoader(FuncionarioServiceImpl funcionarioService,
                             ViaCepClient viaCepClient,
                             EnderecoMapper enderecoMapper,
                             EnderecoRepository enderecoRepository) {
        this.funcionarioService = funcionarioService;
        this.viaCepClient = viaCepClient;
        this.enderecoMapper = enderecoMapper;
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (FileReader file = new FileReader(path);
             BufferedReader reader = new BufferedReader(file)) {

            String linha = reader.readLine();
            String[] campos;

            while (linha != null) {
                campos = linha.split(";");

                EnderecoResponseDTO enderecoDTO = viaCepClient.findByCep(campos[9]);
                Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
                Endereco enderecoSalvo = enderecoRepository.saveAndFlush(endereco);

                System.out.println("Endereço salvo com ID: " + enderecoSalvo.getId());

                FuncionarioRequestDTO funcionarioRequestDTO = new FuncionarioRequestDTO();

                funcionarioRequestDTO.setNome(campos[0]);
                funcionarioRequestDTO.setEmail(campos[1]);
                funcionarioRequestDTO.setCpf(campos[2]);
                funcionarioRequestDTO.setTelefone(campos[3]);
                funcionarioRequestDTO.setMatricula(campos[4]);
                funcionarioRequestDTO.setDepartamento(campos[5]);
                funcionarioRequestDTO.setCargo(campos[6]);
                funcionarioRequestDTO.setDataAdmissao(LocalDate.parse(campos[7]));
                funcionarioRequestDTO.setStatus(StatusFuncionario.valueOf(campos[8]));
                funcionarioRequestDTO.setEnderecoId(endereco.getId());

                funcionarioService.insert(funcionarioRequestDTO);

                linha = reader.readLine();
            }

            Collection<FuncionarioResponseDTO> funcionarios = funcionarioService.findAll();
            funcionarios.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("[FuncionarioLoader] Erro ao ler o arquivo funcionarios.csv: " + e.getMessage());
        }
    }
}
