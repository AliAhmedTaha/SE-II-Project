package com.example.form.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.form.Entities.Store;

public interface StoreRepository extends CrudRepository<Store, String>{

	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="UPDATE Store s SET s.State = :State WHERE s.Storename = :Storename")
    public void updateState(@Param("State") boolean State, @Param("Storename") String Storename );
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="UPDATE Store s SET s.numvisitors = :numvisitors WHERE s.Storename = :Storename")
    public void updateVisit(@Param("numvisitors") int numvisitors, @Param("Storename") String Storename );	
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="UPDATE Store s SET s.numbuyers = :numbuyers WHERE s.Storename = :Storename")
    public void updateNumBuyers(@Param("numbuyers") int numbuyers, @Param("Storename") String Storename );

	
}
