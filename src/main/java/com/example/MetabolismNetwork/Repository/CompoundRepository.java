package com.example.MetabolismNetwork.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.MetabolismNetwork.Model.Compound;


// Interface CrudRepository<T,ID> 
// https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html

// JPA : Java persistence API which provide specification for persisting, reading, managing data from your java object to relations in database.
// Hibernate: There are various provider which implement jpa. Hibernate is one of them. So we have other provider as well. But if using jpa 
// with spring it allows you to switch to different providers in future.
// Spring Data JPA : This is another layer on top of jpa which spring provide to make your life easy.

// Spring Data JPA used Hibernate implementation by default.. If you see the transitive dependencies 
// of spring-boot-starter-data-jpa, you can see hibernate-core there

// All your business logic should be in the Service Layer.
// Any access to the Database (any storage) should go to the Repository Layer.
// Repository Layer just concentrate on DB Operation.

// [Xuan] repository only define the needed queries; and the services will use these queries for business logic
// Same as the RoR, Model with activerecord, which activerecord will do all the queries method, but you can also implement your own query inside model.
// How it works: extend the CrudRepository that link to Compound class (model class) with ID (Long).
// I feel like I should use Repository directly
@Repository
public interface CompoundRepository extends CrudRepository<Compound, Long> {
	
	
	// convention:
	// findBy{the local variable in model}
	List<Compound> findByName(String name);
    List<Compound> findByinchikey(String inchikey);
    List<Compound> findByid(Long ID);
    List<Compound> findAll();
    
    
    
    
    
    
    // create customized query (like findByName)
    // @Query("from Auction a join a.category c where c.name=:categoryName")
    // public Iterable<Compound> findByCategory(String categoryName);
    
    
    // create customized query that join tables 
    // in model
    // @OneToOne(cascade=ALL, mappedBy="ReleaseDateType")
    // private ReleaseDateType releaseDateType;
    // here
    // @Query("Select * from A a  left join B b on a.id=b.id")
    // public List<ReleaseDateType> FindAllWithDescriptionQuery();
    
}
