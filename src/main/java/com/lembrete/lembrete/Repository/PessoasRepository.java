package com.lembrete.lembrete.Repository;

import com.lembrete.lembrete.Entity.Pessoas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PessoasRepository extends JpaRepository<Pessoas, Long> {



    @Query(value = "select count(*)>0 from Pessoas pessoas where pessoas.nome = :nome")
    boolean NomePessoaExistente(@Param("nome") String nome);

    @Query(value = "select count(*)>0 from Pessoas pessoas  where pessoas.id = :id")
    boolean idExistente(@Param("id") Long id);

    @Query(value = "select pessoas from Pessoas pessoas where pessoas.nome = :nome")
    Pessoas NomePessoa(@Param("nome") String nome);

}
