package fr.alainncls.demospringrestdocs.repository;

import fr.alainncls.demospringrestdocs.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
}
