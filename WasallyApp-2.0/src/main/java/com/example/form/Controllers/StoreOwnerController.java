package com.example.form.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.Entities.Store;
import com.example.form.Entities.User;
import com.example.form.Repository.StoreRepository;

@Controller
public class StoreOwnerController {
	
	@Autowired
	StoreRepository storeRepo;

	@GetMapping("/StoreOwnerHomePage")
    public String StoreOwnerHomePage(HttpServletRequest req) 
    {
		try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		if(user.getType().equals("Store Owner"))
    			{return "StoreOwnerHomePage";}
    		else 
				{return "index";}
    	}
		catch(Exception e){return "index";}
    }
	
	@GetMapping("/AddStore")
    public String ShowAddStorePage(HttpServletRequest req) 
    {
		try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		if(user.getType().equals("Store Owner"))
    			{return "AddStore";}
    		else 
				{return "index";}
    	}
		catch(Exception e){return "index";}
    }
	
	@PostMapping("/AddStore")
    public String AddStorePage(@RequestParam String name,@RequestParam String address,@RequestParam String type,HttpServletRequest req) 
    {
    	try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		if(!(user.getType().equals("Store Owner")))
				{return "index";}
    		System.out.println("Hello from session"+" ---> "+user.getName()+"   "+user.getType());
    		if(storeRepo.existsById(name)) 
        	{
        		System.out.println("store name already exists");
        		return "AddStore";
        	}
        	
        	try 
        	{
        		Store newStore=new Store(name, address, false, type);
        		newStore.setUser(user);
        		storeRepo.save(newStore);
        	}
        	catch(Exception e)
        	{
        		System.out.println("Exception in AddStore Post");
        		return "AddStore";
        	}
    	}
    	catch(Exception e) 
    	{
    		System.out.println("No session for user");
    		return "index";
    	}
		
    	return "StoreOwnerHomePage";
		
    }

}
