package com.example.command;

import com.example.form.Entities.LogProduct;
import com.example.form.Entities.Store_Product;

public class EditProductCommand extends Command {

	@Override
	public void DoAction(Store_Product StoreProduct) {
		// TODO Auto-generated method stub
		this.log.SaveChangeEditProduct( StoreProduct);
		this.productcontroler.UpdateStoreProduct(StoreProduct);
		
	}

	@Override
	public Store_Product UnDoAction(Store_Product StoreProduct) {
		// TODO Auto-generated method stub
		LogProduct hestory =this.log.getLastChangeAndDeleteIt("Edit",StoreProduct);
		this.productcontroler.UpdateStoreProduct(hestory.toStoreProduct());
		return null;
	}

}
