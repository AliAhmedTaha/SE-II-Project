package com.example.form.command;

import com.example.form.Entities.LogProduct;
import com.example.form.Entities.Store_Product;

public class DeleteProductCommand extends Command
{

	@Override
	public void DoAction(Store_Product StoreProduct) {
		this.log.SaveChangeDeleteProduct( StoreProduct);
		this.productcontroler.deleteStoreProduct(StoreProduct);
		
	}

	@Override
	public Store_Product UnDoAction(Store_Product StoreProduct) {
		// TODO Auto-generated method stub
		LogProduct hestory =log.getLastChangeAndDeleteIt("Delete",StoreProduct);
		productcontroler.AddProductInStore(hestory.toStoreProduct());
		return null;
	}

}
