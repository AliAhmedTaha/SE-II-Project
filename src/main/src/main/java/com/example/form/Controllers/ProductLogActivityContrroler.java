package com.example.form.Controllers;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.form.Entities.LogProduct;
import com.example.form.Entities.Store_Product;
import com.example.form.Repository.LogProductRepository;
import com.example.form.Repository.Store_productRepository;
@Component
public class ProductLogActivityContrroler {
@Autowired
private LogProductRepository logRepo;
@Autowired
private Store_productRepository Store_productRepo;
	public void SaveChangenewProduct(Store_Product storeProduct) {
		// TODO Auto-generated method stub
		LogProduct hestory = new LogProduct("ADD",storeProduct.getID()
				, storeProduct.getPrice(),storeProduct.getBrandId(),
				storeProduct.getQuantity());
		logRepo.save(hestory);
	}
	
	public void SaveChangeDeleteProduct(Store_Product storeProduct) {
		// TODO Auto-generated method stub
		LogProduct hestory = new LogProduct("Delete",storeProduct.getID()
				, storeProduct.getPrice(),storeProduct.getBrandId(),
				storeProduct.getQuantity());
		logRepo.save(hestory);
	}


	public void SaveChangeEditProduct(Store_Product storeProduct) {
		// TODO Auto-generated method s.tub
		Store_Product product = Store_productRepo.findById(storeProduct.getID())
				.get();
		
		LogProduct hestory = new LogProduct("Edit",product); 
		logRepo.save(hestory);
		
	}
	public LogProduct getLastChangeAndDeleteIt(String Type,Store_Product storeProduct) {
		Iterable<LogProduct>AllLog=logRepo.findAll();
		LogProduct temp = null;
		for (LogProduct l:AllLog)
		{
			if(l.getType().equals(Type)) 
			{
			if (temp==null&&l.getProductID().equals(storeProduct.getID()))
			{
				temp= l;
			}
			else if(temp.getId()<l.getId())
			{
				temp=l;
			}
			}
		}
		logRepo.deleteById(temp.getId());
		return temp;
		
	}

	public ArrayList<LogProduct> getlog(String currentStore) {
		// TODO Auto-generated method stub
		Iterable<LogProduct> itter =logRepo.findAll();
		ArrayList<LogProduct>data =new ArrayList<>();
		for (LogProduct e: itter)
		{
			if (e.getProductID().split("@")[0].equals(currentStore))
			data.add(e);
		}
		return data;
	}
	


}
