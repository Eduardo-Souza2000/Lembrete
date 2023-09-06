package com.lembrete.lembrete;

import com.lembrete.lembrete.Controller.LembretesController;
import com.lembrete.lembrete.Entity.Lembretes;
import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Repository.LembretesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LembretesControllerIntegrationTest {
    @MockBean
    LembretesRepository lembretesRepository;
    @Autowired
    private LembretesController lembretesController;

    @BeforeEach
    void criaElementosData(){
        /*
         *Criando objetos de exemplo para uso nos testes.
         * Neste caso, estamos criando uma pessoa chamada "Yasmin" e dois lembretes associados a ela.
         * Os lembretes são para "Tomar Remédio" e "Tomar Água".
         * Esses objetos serão usados em testes posteriores.
         */
        Pessoas pessoa = new Pessoas(1,"Yasmin");
        Lembretes lembretesTest01 = new Lembretes(2L, "Tomar Remedio", pessoa);
        Lembretes lembretesTest02 = new Lembretes(2L, "Tomar AGUA", pessoa);
        List<Lembretes> lembretes = new ArrayList<>();
        lembretes.add(lembretesTest01);
        lembretes.add(lembretesTest02);


    }
}