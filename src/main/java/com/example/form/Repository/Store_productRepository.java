package com.example.form.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.form.Entities.Store_Product;

public interface Store_productRepository extends CrudRepository<Store_Product,String >{
	
	
	@Modifying(clearAutomatically = true)
	@Transactional
    @Query(value="UPDATE Store_Product s SET s.Quantity = :Quantity WHERE s.ID = :ID")
    public void updateProductQuantity(@Param("Quantity") int Quantity, @Param("ID") String ID );

}
