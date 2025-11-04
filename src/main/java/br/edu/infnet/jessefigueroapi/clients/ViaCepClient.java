package br.edu.infnet.jessefigueroapi.clients;

import br.edu.infnet.jessefigueroapi.model.domain.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "${api.viacep.url}")
public interface ViaCepClient {

    @GetMapping("/{cep}/json/")
    Endereco findByCep(@PathVariable String cep);

}
