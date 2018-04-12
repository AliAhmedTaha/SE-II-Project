package com.example.command;

import com.example.form.Entities.LogProduct;
import com.example.form.Entities.Store_Product;

public class AddProductCommand extends Command {

	@Override
	public void DoAction(Store_Product StoreProduct) {
		// TODO Auto-generated method stub
		this.log.SaveChangenewProduct(StoreProduct);
		this.productcontroler.AddProductInStore(StoreProduct);
		
	}

	@Override
	public Store_Product UnDoAction(Store_Product StoreProduct) {
		LogProduct hestory= this.log.getLastChangeAndDeleteIt("ADD",StoreProduct);
		productcontroler.deleteStoreProduct(StoreProduct);
		return null;
	}

}
