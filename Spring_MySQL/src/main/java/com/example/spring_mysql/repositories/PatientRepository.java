package com.example.spring_mysql.repositories;

import com.example.spring_mysql.entities.Patient;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByMalade(boolean m);
    Page<Patient> findByMalade(boolean m, Pageable pageable);

    List<Patient> findByMaladeAndScoreLessThan(boolean m, int score);
    List<Patient> findByDateNaissanceBetweenAndMaladeIsTrueOrNomLike(Data d1, Data d2 , String mc);
    @Query("select p from Patient p where p.nom like :x and p.score <:y")
    List<Patient> chercherPatients(@Param("x") String nom, @Param("y")int scoreMin);


}
