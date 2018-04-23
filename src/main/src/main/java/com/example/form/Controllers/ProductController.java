package com.example.form.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.Product;
import com.example.form.Entities.Store;
import com.example.form.Entities.Store_Product;
import com.example.form.Repository.ProductRepsitory;
import com.example.form.Repository.Store_productRepository;
import com.example.form.command.AddProductCommand;
import com.example.form.command.Command;
import com.example.form.command.InvokerProductAction;

@Component
public class ProductController {

	@Autowired
	private ProductRepsitory ProductRepo;
	@Autowired
	private Store_productRepository Store_ProductRepo;
	@Autowired
	AddProductCommand create;
	@Autowired
	InvokerProductAction Action;
	
	public ProductController() { }
	
	
	
	public void buyProduct(String StoreId, String ProductId, int quantity) 
	{
		Store_Product productInStore=getStoreProductById(StoreId, ProductId);
		int currQuantity=productInStore.getQuantity();
		Store_ProductRepo.updateProductQuantity(currQuantity-quantity, productInStore.getID());
	}
	
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Entry<Integer,String> GetlowestProduct(Map<String, Integer> productmap) {
		Iterator it = productmap.entrySet().iterator();
		Map.Entry pair = (Map.Entry) it.next();
		Integer value = (Integer) pair.getValue();
		for (Entry<String, Integer> entry : productmap.entrySet()) {

			int value2 = entry.getValue();
			if (value > value2)
				pair = entry;
			// ...
		}
		return pair;
	}


	@SuppressWarnings("rawtypes")
	public Entry GetHighestProduct(Map<String, Integer> productmap) {
		Iterator it = productmap.entrySet().iterator();
		Map.Entry pair = (Map.Entry) it.next();
		Integer value = (Integer) pair.getValue();
		for (Entry<String, Integer> entry : productmap.entrySet()) {

			int value2 = entry.getValue();
			if (value < value2)
				pair = entry;
			// ...
		}
		return pair;

	}



	public Map<String, Integer> getpoductmap() {
		Iterable<Product> allproducts = ProductRepo.findAll();
		Map<String, Integer> productmap=new HashMap<String, Integer>();
		for (Product e : allproducts) {
			productmap.put(e.getName(), 0);
		}
		Iterable<Store_Product> ProductsinAllStors = Store_ProductRepo.findAll();
		for (Store_Product e : ProductsinAllStors) {
			productmap.put(e.getProductName(), productmap.get(e.getProductName()) + e.getBuyed());
		}
		return productmap;
	}



	@SuppressWarnings("rawtypes")
	public int getTottalSoldBroduct(Map<String, Integer> productmap) {
		Iterator it = productmap.entrySet().iterator();
		Map.Entry pair = (Map.Entry) it.next();
		Integer value = (Integer) pair.getValue();
		for (Entry<String, Integer> entry : productmap.entrySet()) {

			 value += entry.getValue();
			
			// ...
		}
		return value;
	}

	public boolean AddProductInStore(Store_Product storeProduct) {
		Product producttemp =new Product();
		producttemp.setName(storeProduct.getID().split("@")[1]);
		Store temp= new Store();
		temp.setStorename(storeProduct.getID().split("@")[0]);
	return	this.AddProductInStore(producttemp, temp, storeProduct.getPrice()
				, storeProduct.getBrandId(),storeProduct.getQuantity());
	}



	public void deleteStoreProduct(Store_Product storeProduct) {
		
		Store_ProductRepo.deleteById(storeProduct.getID());
	}



	public void UpdateStoreProduct(Store_Product storeProduct) {
		Store_Product temp = Store_ProductRepo.
				findById(storeProduct.getID()).get();
		temp.setPrice(storeProduct.getPrice());
		temp.setBrandId(storeProduct.getBrandId());
		temp.setQuantity(storeProduct.getQuantity());
		Store_ProductRepo.deleteById(temp.getID());
		Store_ProductRepo.save(temp);
	}



	public void AddProductInStorecollaborator(Product product, Store store, double price,
			String brand, int quan) {
		System.out.println("addproductcollb");
	 
	Store_Product P_S= new Store_Product(product,store,price,brand,0,0,quan);
	Action.ExecuteCommand(create, P_S);
		
	}



	public Store_Product getStoreProduct(String productname) {
		
		return Store_ProductRepo.findById(productname).get();
	}



	public void undo(Store_Product storeProduct) {
		Action.UnDoExecuteCommand(create, storeProduct);
		
	}

}
