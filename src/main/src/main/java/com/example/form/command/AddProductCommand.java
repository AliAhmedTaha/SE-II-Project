package com.example.form.command;

import org.springframework.stereotype.Component;

import com.example.form.Entities.Store_Product;
@Component
public class AddProductCommand extends Command {
	
	@Override
	public void DoAction(Store_Product StoreProduct) {
		// TODO Auto-generated method stub
		if (this.productcontroler.AddProductInStore(StoreProduct))
		this.log.SaveChangenewProduct(StoreProduct);
		
		
	}

	@Override
	public Store_Product UnDoAction(Store_Product StoreProduct) {
	    this.log.getLastChangeAndDeleteIt("ADD",StoreProduct);
		productcontroler.deleteStoreProduct(StoreProduct);
		return null;
	}

}
