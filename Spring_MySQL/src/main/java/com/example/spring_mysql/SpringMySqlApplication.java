package com.example.spring_mysql;

import com.example.spring_mysql.entities.Patient;
import com.example.spring_mysql.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringMySqlApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringMySqlApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Ajout manuel
        patientRepository.save(
                new Patient(null, "Ichraq",new Date(),false,56));
        patientRepository.save(
                new Patient(null, "Sabrine",new Date(),false,75));
        patientRepository.save(
                new Patient(null, "Mohammed",new Date(),false,65));

        //Ajout boucle
        for (int i=0;i<100;i++){
            patientRepository.save(
                    new Patient(null, "Hassan",new Date(),Math.random()>0.5?true:false,(int)(Math.random()*100)));

        }

        //Affichage avec Pagination

        Page<Patient> patients = patientRepository.findAll(PageRequest.of(1,5));
        System.out.println("Total pages :"+ patients.getTotalPages());
        System.out.println("Total elements:"+patients.getTotalElements());
        System.out.println("Num Page:"+patients.getNumber());
        List<Patient> content = patients.getContent();
        System.out.println("--------------La liste des patients sans contrainte  : ---------------");
        content.forEach(patient->{
            System.out.println("-----------------------------");
            System.out.println(patient.getId());
            System.out.println(patient.getNom());
            System.out.println(patient.getScore());
            System.out.println(patient.getDateNaissance());
            System.out.println(patient.isMalade());
        });

        //----------------------------

        System.out.println("--------------La liste des patients malades : ---------------");
        //Page <Patient> byMalade = patientRepository.findByMalade(true,PageRequest.of(0,4));
        //List<Patient> byMalade = patientRepository.findByMalade(true);
        List <Patient> byMalade = patientRepository.chercherPatients("%m%",80);
        byMalade.forEach(p->{
            System.out.println("-----------------------------");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
        });


        /*
        //Affichage par liste
        List<Patient> patients = patientRepository.findAll();
        patients.forEach(p->{
            System.out.println("----------------La liste des patients -------------");
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getScore());
            System.out.println(p.getDateNaissance());
            System.out.println(p.isMalade());
                });*/

        //Recherche patient par ID
        System.out.println("***************Recherche des patients par ID **********************");
        Patient patient=patientRepository.findById(1L).orElse(null);
        if(patient!=null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        //Mise à jour patient
        patient.setScore(870);
        patientRepository.save(patient);

        //Suppression patient
        patientRepository.deleteById(1L);
    }
}
