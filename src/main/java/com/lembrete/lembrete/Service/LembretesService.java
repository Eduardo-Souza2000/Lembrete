package com.lembrete.lembrete.Service;

import com.lembrete.lembrete.Entity.Lembretes;
import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Repository.LembretesRepository;
import com.lembrete.lembrete.Repository.PessoasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class LembretesService {
    @Autowired
    private LembretesRepository lembretesRepository;
    @Autowired
    private PessoasRepository pessoasRepository;



    public List<Lembretes> buscarLembretesPorNomePessoa(String nome) {


        if (!lembretesRepository.pessoaExistente(nome)) {
            throw new RuntimeException("Nome inválido - Motivo: Não Existe no Banco de Dados");
        }

        return lembretesRepository.lembretesExistente(nome);

    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastroLembrete( Lembretes lembretes){
        if(lembretes.getRecado() == null) {
            throw new RuntimeException("Recado Nulo");
        }else if (!pessoasRepository.NomePessoaExistente(lembretes.getPessoa().getNome())){
            throw new RuntimeException("Nome inválido, pois nao existe no Banco");
        }else {
            lembretesRepository.save(lembretes);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(@RequestParam("id")  Long id, @RequestBody Lembretes lembretes) {

        final Lembretes  lembretebanco = this.lembretesRepository.findById(id).orElseThrow(()-> {
            throw new EntityNotFoundException("Nao foi encontrado o ID no Banco");
        });
        if (!lembretes.getId().equals(id)){
            throw new RuntimeException("Não foi possivel identificar o registro informado pois o ID não confere");
        } else if (!lembretesRepository.idExistente(id)) {
            throw new RuntimeException(" Id da Pessoa Não existe");
        }else if (!lembretes.getPessoa().getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        }else {
            lembretesRepository.save(lembretes);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id")  Long id) {

        Lembretes lembretes = this.lembretesRepository.findById(id).orElse(null);

        if(id == null){
            throw new RuntimeException(" Id do Lembrete Invalido");
        } else if (!pessoasRepository.idExistente(id)) {
            throw new RuntimeException(" Lembrete nao existe no banco");
        }else {
            lembretesRepository.delete(lembretes);
        }



    }

}
