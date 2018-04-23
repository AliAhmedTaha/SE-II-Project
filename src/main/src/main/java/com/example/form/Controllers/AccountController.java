package com.example.form.Controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.form.Entities.Store;
import com.example.form.Entities.Store_Contributors;
import com.example.form.Entities.User;
import com.example.form.Repository.StoreRepository;
import com.example.form.Repository.Store_ContributorsRepository;
import com.example.form.Repository.UserRepository;

@Component
public class AccountController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	StoreRepository StoreRepo;
	@Autowired
	Store_ContributorsRepository SCRepo;

	public AccountController() {
	}

	public String validateUser(String userName, String password, HttpServletRequest request) {
		try {
			User userAccount = userRepo.findById(userName).get();
			System.out
					.println(userAccount.getName() + "   " + userAccount.getPassword() + "  " + userAccount.getType());
			if (userAccount.getPassword().equals(password)) {
				request.getSession().setAttribute("user", userAccount);
				return userAccount.getType();
			} else {
				return "WrongPassword";
			}
		} catch (Exception e) {
			System.out.println("UserNotFound");
			return "UserNotFound";
		}
	}

	public boolean createUser(String userName, String password, String type) {
		try {
			userRepo.save(new User(userName, password, type));
			return true;
		} catch (Exception ex) {
			System.out.println(ex);
			return false;
		}
	}

	public User getSessionUser(HttpServletRequest request) {
		try {
			User user = new User((User) request.getSession().getAttribute("user"));
			return user;

		} catch (Exception e) {
			System.out.println("from getSessionUser : " + e);
			return null;
		}
	}

	public boolean getPermission(String StoreName, String UserName) {
		Iterable<Store_Contributors> Store_ContributorsIterable = SCRepo.findAll();
		Store Store;
		User user;
		for (Store_Contributors C : Store_ContributorsIterable) {
			Store = C.getStore();
			user = C.getUser();
			if (Store.getStorename().equals(StoreName) && user.getName().equals(UserName)) {
				if (C.getPermission().equals("Original")) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public ArrayList<User> getStoreOwners(String OriginalOwner, String StoreName) {
		ArrayList<User> AllStoreOwners = new ArrayList<User>();
		Iterable<User> UserIterable = userRepo.findAll();
		Store Store;
		User user;
		boolean Valid;
		Iterable<Store_Contributors> Store_ContributorsIterable = SCRepo.findAll();
		for (User U : UserIterable) {
			if (U.getType().equals("Store Owner") && !(U.getName().equals(OriginalOwner))) {
				Valid = true;
				for (Store_Contributors C : Store_ContributorsIterable) {
					Store = C.getStore();
					user = C.getUser();
					if (Store.getStorename().equals(StoreName) && user.getName().equals(U.getName())) {
						Valid = false;
					}
				}
				if (Valid) {
					AllStoreOwners.add(U);
				}
			}
		}
		return AllStoreOwners;
	}

	public boolean AddCollaborator(HttpServletRequest request, String StoreName, String CollaboratorName,
			String Permission) {
		User user = userRepo.findById(CollaboratorName).get();
		Store store = StoreRepo.findById(StoreName).get();
		Store_Contributors SC = new Store_Contributors(user, store, Permission);
		SCRepo.save(SC);
		return false;
	}
	
	public double calculateOrderPrice(int quantity,double price,String accType) 
	{
		double totalPrice=quantity*price;
		if(accType.equals("Store Owner")) 
		{
			totalPrice-=(totalPrice*15/100);
			if(quantity>=2)
				totalPrice-=(totalPrice*10/100);
			
		}
		else 
		{
			if(quantity>=2)
				totalPrice-=(totalPrice*10/100);
		}
		return totalPrice;
	}
	

}
