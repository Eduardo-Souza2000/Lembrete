package com.lembrete.lembrete.Controller;

import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Service.PessoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/pessoas")
public class PessoasController {
    @Autowired
    private PessoasService pessoasService;

    @GetMapping("/{nome}")
    public ResponseEntity<Pessoas> buscaPessoaPorNome (@PathVariable String nome){

        try{
            Pessoas pessoa = pessoasService.procurar(nome);
            return  ResponseEntity.ok(pessoa);

        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Pessoas>> listaPessoas(){
        List<Pessoas> pessoas = pessoasService.lista();
        return  ResponseEntity.ok(pessoas);
    }


    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody final Pessoas pessoa){
        try{
            this.pessoasService.cadastrarPessoa(pessoa);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> editar(
            @PathVariable("id") final Long id,
            @RequestBody final  Pessoas pessoa
    ) {
        try{
            this.pessoasService.editar(id,pessoa);
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
            this.pessoasService.delete(id);
            return ResponseEntity.ok("Registro Alterado com sucesso");

        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }

    }

}
