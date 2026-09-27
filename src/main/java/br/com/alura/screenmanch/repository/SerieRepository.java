package br.com.alura.screenmanch.repository;

import br.com.alura.screenmanch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie,Long> {
}
