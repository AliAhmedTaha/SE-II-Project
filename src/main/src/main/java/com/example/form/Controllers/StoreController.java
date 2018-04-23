package com.example.form.Controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.Store;
import com.example.form.Entities.Store_Contributors;
import com.example.form.Entities.Store_Product;
import com.example.form.Entities.User;
import com.example.form.Repository.StoreRepository;
import com.example.form.Repository.Store_ContributorsRepository;
import com.example.form.Repository.Store_productRepository;

@Component
public class StoreController {

	@Autowired
	private StoreRepository StoreRepo;
	@Autowired
	private Store_productRepository store_productRebo;
	@Autowired
	private AccountController Account;
	@Autowired
	Store_ContributorsRepository SCRepo;
	
	public StoreController() {}
	
	public Store getStore(String StoreId) 
	{
		try 
		{
			return StoreRepo.findById(StoreId).get();
		}
		catch(Exception e) {return null;}
	}
	
	public ArrayList<Store> getAllStores()
	{
		ArrayList<Store> AllStores = new ArrayList<Store>();
		Iterable<Store> StoreIterable = StoreRepo.findAll();
		for(Store S:StoreIterable) 
		{
			if(S.getState()) {AllStores.add(S);}
		}
		return AllStores;
	}
	
	public boolean addNewStore(HttpServletRequest request, String StoreId, String address, String type, User user) 
	{
		if(StoreRepo.existsById(StoreId)) 
    	{
    		System.out.println("store name already exists");
    		return false;
    	}
		else 
    	{
    		Store newStore=new Store(StoreId, address, false, type);
    		newStore.setUser(user);
    		StoreRepo.save(newStore);
			Account.AddCollaborator(request, newStore.getStorename(), user.getName(),"Original");
    		return true;
    	}
	}
	
	public ArrayList<Store> getUnApprovedStores()
	{
		ArrayList<Store> AllStores = new ArrayList<Store>();
		Iterable<Store> StoreIterable = StoreRepo.findAll();
		for(Store S:StoreIterable) 
		{
			if(!S.getState()) {AllStores.add(S);}
		}
		return AllStores;
	}
	
	public void approveStores(String[] StoreIds) 
	{
		for (int i = 0; i < StoreIds.length; i++) 
		{
			StoreRepo.updateState(true,StoreIds[i]);
		}
	}
	
	public ArrayList<Store> getUserStores(String UserId)
	{
		Iterable<Store_Contributors> Store_ContributorsIterable = SCRepo.findAll();
		ArrayList<Store> AllStores = new ArrayList<Store>();
		Store Store;
		User user;
		for(Store_Contributors C:Store_ContributorsIterable) 
		{
			Store = C.getStore();
			user = C.getUser();
			if(Store.getState() && user.getName().equals(UserId))
				{AllStores.add(Store);}
		}
		return AllStores;
	}
	
	
	public String getSoldoutProducts(String Strorename)
	{
		ArrayList<Store_Product> SoldOut = new ArrayList<>();
		String result="";
		for (Store_Product e:store_productRebo.findAll())
		{
			if (e.getID().contains(Strorename) && e.getQuantity()==0)
			{
				SoldOut.add(e);
			}
		}
		
		if (SoldOut.isEmpty()){ result = "no sold out products in the store";}
		else 
		{
			for (Store_Product e: SoldOut){ result+=e.getProductName()+", ";}
		}

		return result;
	}
	
	public ArrayList<String> getProductsInStore(String StoreName)
	{
		ArrayList<String> StoreProductsNames = new ArrayList<String>();
		for (Store_Product StoreProduct:store_productRebo.findAll())
		{
			if (StoreProduct.getID().contains(StoreName))
			{
				StoreProductsNames.add(StoreProduct.getProductName());
			}
		}
		return StoreProductsNames;
	}
	
	
	public void incrementStoreVisits(String StoreName) 
	{
		Store myStore = StoreRepo.findById(StoreName).get();
		int numVisit = myStore.getNumvisitors();
		StoreRepo.updateVisit(numVisit+1, StoreName);
	}
	
	public void updateStoreBuyers(String StoreName) 
	{
		Store myStore = StoreRepo.findById(StoreName).get();
		int numBuyers = myStore.getNumbuyers();
		StoreRepo.updateVisit(numBuyers+1, StoreName);
	}
	

	public String getMaxVisitedStore(String UserName) {
		
		/// get all stores of this user (with UserName)
		ArrayList<Store> AllStores = getUserStores(UserName);
	
		/// get Maximum visited Store 
		String MaxVisitedStoreName = "";
		int MaxVisits = -1;
		for(int i = 0 ; i < AllStores.size() ; ++i) {
			Store CurrStore = AllStores.get(i);
			if (CurrStore.getNumvisitors() > MaxVisits) {
				MaxVisits = CurrStore.getNumvisitors();
				MaxVisitedStoreName = CurrStore.getStorename();
			}
		}
		
		return MaxVisitedStoreName;
	}
	
	
	
	public String getMinVisitedStore(String UserName) {
		
		/// get all stores of this user (with UserName)
		ArrayList<Store> AllStores = getUserStores(UserName);
		
		/// get Minimum visited Store Name
		String MinVisitedStoreName = "";
		int MinVisits = 1000000000;
		for(int i = 0 ; i < AllStores.size() ; ++i) {
			Store CurrStore = AllStores.get(i);
			if (CurrStore.getNumvisitors() < MinVisits) {
				MinVisits = CurrStore.getNumvisitors();
				MinVisitedStoreName = CurrStore.getStorename();
			}
		}
		return MinVisitedStoreName;
	}
	
	public int getNumberOfVisitedStores(String UserName) {
		
		/// get all stores of this user (with UserName).
		ArrayList<Store> AllStores = getUserStores(UserName);
		
		/// get number of visited stores of that user.
		int NumberOfVisitedStores = 0;
		for(int i = 0 ; i < AllStores.size() ; ++i) {
			Store CurrStore = AllStores.get(i);
			NumberOfVisitedStores += (CurrStore.getNumvisitors()>0)? 1 : 0;
		}
		return NumberOfVisitedStores;
	}
	public float getAverageOfVisitedStores(String UserName) {
		
		/// get all stores of this user (with UserName).
		ArrayList<Store> AllStores = getUserStores(UserName);
				
		/// get number of visited stores of that user.
		float AllVisits = 0 , NumberOfVisitedStores = 0;
		for(int i = 0 ; i < AllStores.size() ; ++i) {
			 Store CurrStore = AllStores.get(i);
			 if (CurrStore.getNumvisitors() > 0) {
				 
				 AllVisits += CurrStore.getNumvisitors();
				 NumberOfVisitedStores++;
			 }
		}
		float Average =  AllVisits/NumberOfVisitedStores;
		if (AllVisits == 0) {
			Average = 0;
		}
			return Average;
	}

}
