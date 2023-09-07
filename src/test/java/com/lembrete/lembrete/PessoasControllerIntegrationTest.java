package com.lembrete.lembrete;


import com.lembrete.lembrete.Controller.PessoasController;
import com.lembrete.lembrete.Entity.Lembretes;
import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Repository.LembretesRepository;
import com.lembrete.lembrete.Repository.PessoasRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@SpringBootTest
public class PessoasControllerIntegrationTest {
    @MockBean
    LembretesRepository lembretesRepository;
    @MockBean
    PessoasRepository pessoasRepository;
    @Autowired
    private PessoasController pessoasController;

    @BeforeEach
    void criaElementosData(){
        /*
         *Criando objetos de exemplo para uso nos testes.
         * Neste caso, estamos criando uma pessoa chamada "Yasmin"
         * Esses objetos serão usados em testes posteriores.
         */
        Pessoas pessoa01 = new Pessoas(1L, "Yasmin");
        Pessoas pessoa02 = new Pessoas(2L, "Eduardo");;
        List<Pessoas> pessoas = new ArrayList<>();
        pessoas.add(pessoa01);
        pessoas.add(pessoa02);

        Optional<Pessoas> pessoasOptional = Optional.of(new Pessoas(3L,"Dudu"));

        Long id = 1L;
        String nome = "Yasmin";



        Mockito.when(pessoasRepository.NomePessoaExistente(nome)).thenReturn(true);
        Mockito.when(pessoasRepository.NomePessoa(nome)).thenReturn(pessoa01);
        Mockito.when(pessoasRepository.findAll()).thenReturn(pessoas);
        Mockito.when(pessoasRepository.findAll()).thenReturn(pessoas);
        Mockito.when(pessoasRepository.idExistente(id)).thenReturn(true);
        Mockito.when(pessoasRepository.findById(id)).thenReturn(pessoasOptional);


    }

    @Test
    void procurarPessoaTest(){
        var pessoa = pessoasController.buscaPessoaPorNome("Yasmin");
        Assert.assertEquals("Yasmin", pessoa.getBody().getNome());
    }

    @Test
    void procuraListaPessoa(){
        var pessoa = pessoasController.listaPessoas();
        Assert.assertEquals(2,pessoa.getBody().size());
    }

    @Test
    void EditaPessoaTest(){
        var pessoa = pessoasController.editar(1l,new Pessoas(1L, "Sara"));
        Assert.assertEquals("Registro Atualizado com sucesso",pessoa.getBody());
    }

    @Test
    void CadastrarPessoaTest(){
        var pessoa = pessoasController.cadastrar(new Pessoas(4L, "Sara"));
        Assert.assertEquals("Registro Cadastrado com sucesso",pessoa.getBody());
    }

    @Test
    void deletePessoaTest(){
        var deletePessoa = pessoasController.delete(1L);
        Assert.assertEquals("Registro Alterado com sucesso", deletePessoa.getBody());
    }
}