package com.example.form.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.Product;
import com.example.form.Entities.Store;
import com.example.form.Entities.Store_Product;
import com.example.form.Repository.ProductRepsitory;
import com.example.form.Repository.Store_productRepository;

@Component
public class ProductController {

	@Autowired
	private ProductRepsitory ProductRepo;
	@Autowired
	private Store_productRepository Store_ProductRepo;
	
	
	public ProductController() { }
	
	
	
	public boolean checkPriceRange(Product product,double price) 
	{
		if( product.getLowPrice() <= price && product.getHighPrice() >= price) {return true;}
		else {return false;}
	}
	
	public Product getSystemProductById(String productName) 
	{
		try 
		{
			Product product=ProductRepo.findById(productName).get();
			return product;
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	
	
	public boolean AddProductInSystem(Product product) 
    {	
    	if(ProductRepo.existsById(product.getName()))
    	{
    		System.out.println("ProductInSystem ProductName exists");
    		return false;
    	}
    	else
    	{
    		ProductRepo.save(product);
    		return true;
    	}
    }
	
	
	public ArrayList<Product> getSystemProducts() 
	{
		ArrayList<Product> AllProducts = new ArrayList<Product>();
		
		Iterable<Product> ProductIterable = ProductRepo.findAll();
		
		for(Product P:ProductIterable) 
		{
				AllProducts.add(P);
				System.out.println(P.getName());
		}
		
		return AllProducts;
	}
	
	
	
	public boolean AddProductInStore(Product product, Store store, double price, String brand, int Quan ) 
    {	
		try 
		{
			Store_Product S_P = new Store_Product(product, store, price, brand,0, 0, Quan);
			Store_ProductRepo.save(S_P);
			return true;
		}
		catch(Exception e) {return false;}
		
    }
	
	public Store_Product getStoreProductById(String StoreId,String ProductId) 
	{
		try 
		{
			Store_Product product=Store_ProductRepo.findById(StoreId+"@"+ProductId).get();
			return product;
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}

}
