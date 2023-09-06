package com.lembrete.lembrete.Repository;

import com.lembrete.lembrete.Entity.Lembretes;
import com.lembrete.lembrete.Entity.Pessoas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LembretesRepository extends JpaRepository<Lembretes, Long> {
    /*@Query(value = "select count(*)>0 from Lembretes lembretes where lembretes.nome = :nome")
    boolean NomePessoaExistente(@Param("nome") String nome);*/

    List<Lembretes> findByPessoaNome(String nome);

    boolean existsByPessoaNome(String nome);


    @Query(value = "select count(*)>0 from Lembretes lembretes   where lembretes.id = :id")
    boolean idExistente(@Param("id") Long id);

    @Query(value = "select lembretes from Lembretes lembretes where lembretes.pessoa.nome = :nome")
    List<Lembretes> lembretesExistente(@Param("nome") String nome);

    @Query(value = "select count(*)>0 from Lembretes lembretes where lembretes.pessoa.nome = :nome")
    boolean pessoaExistente(@Param("nome") String nome);

/*
    @Query(value = "select pessoas from Pessoas pessoas where pessoas.nome = :nome")
    Pessoas NomePessoa(@Param("nome") String nome);
    */
}
