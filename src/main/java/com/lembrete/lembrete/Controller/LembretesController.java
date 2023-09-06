package com.lembrete.lembrete.Controller;

import com.lembrete.lembrete.Entity.Lembretes;
import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Repository.PessoasRepository;
import com.lembrete.lembrete.Service.LembretesService;
import com.lembrete.lembrete.Service.PessoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/lembretes")
public class LembretesController {

    @Autowired
    private LembretesService lembretesService;

    @Autowired
    private PessoasService pessoasService;


    @GetMapping("/{nome}")
    public ResponseEntity<List<Lembretes>> procurar(@PathVariable String nome) {
        try {
            List<Lembretes> lembretes = lembretesService.buscarLembretesPorNomePessoa(nome);
            return ResponseEntity.ok(lembretes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final Lembretes lembretes){
        try{
            Pessoas pessoa = pessoasService.procurar(lembretes.getPessoa().getNome());
            if (pessoa == null) {
                pessoa = new Pessoas();
                pessoa.setNome(lembretes.getPessoa().getNome());
                pessoasService.cadastrarPessoa(pessoa);
            }
            lembretes.setPessoa(pessoa);
            this.lembretesService.cadastroLembrete(lembretes);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final  Lembretes lembretes
    ) {
        try{
            this.lembretesService.editar(id,lembretes);
            return ResponseEntity.ok("Registro Atualizado com sucesso");

        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> delete( @PathVariable("id") final Long id){
        try {
            this.lembretesService.delete(id);
            return ResponseEntity.ok("Registro Alterado com sucesso");

        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }

    }


}
