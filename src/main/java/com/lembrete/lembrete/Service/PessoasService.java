package com.lembrete.lembrete.Service;

import com.lembrete.lembrete.Entity.Pessoas;
import com.lembrete.lembrete.Repository.PessoasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PessoasService {
    @Autowired
    private PessoasRepository pessoasRepository;

    public Pessoas procurar(String nome){
        Pessoas pessoas = pessoasRepository.NomePessoa(nome);

        if (!pessoasRepository.NomePessoaExistente(nome) ){
            throw new RuntimeException("Nome inválido - Motivo: Nao Existe no Banco de Dados");
        }
        pessoas.getLembretes().size();
        return pessoas;
    }

    public List<Pessoas> lista(){
        return pessoasRepository.findAll();
    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrarPessoa( Pessoas pessoa){

        if(pessoa.getNome() == null) {
            throw new RuntimeException("Nome Nulo");
        }else if (!pessoa.getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        }else if (pessoasRepository.NomePessoaExistente(pessoa.getNome())) {
            throw new RuntimeException("Pessoa já existe");
        }else {
            pessoasRepository.save(pessoa);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void editar(@RequestParam("id")  Long id, @RequestBody Pessoas pessoa) {

        final Pessoas pessoasbanco = this.pessoasRepository.findById(id).orElseThrow(()-> {
            throw new EntityNotFoundException("Nao foi encontrado o ID no Banco");
        });
        if (!pessoa.getId().equals(id)){
            throw new RuntimeException("Não foi possivel identificar o registro informado pois o ID não confere");
        } else if (!pessoasRepository.idExistente(id)) {
            throw new RuntimeException(" Id da Pessoa Não existe");
        }else if (!pessoa.getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        }else if (pessoasRepository.NomePessoaExistente(pessoa.getNome())) {
            throw new RuntimeException("Pessoa já existe");
        }else {
            pessoasRepository.save(pessoa);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id")  Long id) {

        Pessoas pessoas = this.pessoasRepository.findById(id).orElse(null);

        if(id == null){
            throw new RuntimeException(" Id da Pessoa Invalido");
        } else if (!pessoasRepository.idExistente(id)) {
            throw new RuntimeException(" Pessoa nao existe no banco");
        }else {
            pessoasRepository.delete(pessoas);
        }



    }



}
