package com.example.form.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.ProductLogActivityContrroler;
import com.example.form.Entities.Store_Product;
@Component
public abstract class Command {

	@Autowired  
	protected ProductController productcontroler;
	@Autowired
	protected ProductLogActivityContrroler log;
/*public Command() {
	productcontroler= new ProductController();
	log=new ProductLogActivityContrroler();
}*/
public abstract void DoAction(Store_Product StoreProduct);
public abstract Store_Product UnDoAction(Store_Product StoreProduct);
}
