package br.edu.infnet.jessefigueroapi.loaders;

import br.edu.infnet.jessefigueroapi.clients.ViaCepClient;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioRequestDTO;
import br.edu.infnet.jessefigueroapi.dtos.funcionario.FuncionarioResponseDTO;
import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import br.edu.infnet.jessefigueroapi.model.domain.Funcionario;
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

    public FuncionarioLoader(FuncionarioServiceImpl funcionarioService, ViaCepClient viaCepClient) {
        this.funcionarioService = funcionarioService;
        this.viaCepClient = viaCepClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);

            String linha = reader.readLine();
            String[] campos;

            if (linha == null || linha.isEmpty()) {
                System.out.println("[FuncionárioLoader] Arquivo CSV de funcionarios vazio ou sem cabeçalho.");
                return;
            }

            linha = reader.readLine();

            while (linha != null) {

                campos = linha.split(";");

                Funcionario funcionario = new Funcionario();

                funcionario.setNome(campos[0]);
                funcionario.setEmail(campos[1]);
                funcionario.setCpf(campos[2]);
                funcionario.setTelefone(campos[3]);
                funcionario.setMatricula(campos[4]);
                funcionario.setDepartamento(campos[5]);
                funcionario.setCargo(campos[6]);
                funcionario.setDataAdmissao(LocalDate.parse(campos[7]));
                funcionario.setAtivo(Boolean.valueOf(campos[8]));

                Endereco endereco = new Endereco();
                endereco.setCep("89110110");

                funcionario.setEndereco(endereco);
//                funcionario.setEndereco(viaCepClient.findByCep(campos[9]));

                funcionarioService.insert(new FuncionarioRequestDTO(funcionario));

                linha = reader.readLine();
            }

            Collection<FuncionarioResponseDTO> funcionarios = funcionarioService.findAll();
            funcionarios.forEach(System.out::println);
            reader.close();
        } catch (IOException e) {
            System.err.println("[FuncionarioLoader] Erro ao ler o arquivo funcionarios.csv: " + e.getMessage());
        }
    }
}
