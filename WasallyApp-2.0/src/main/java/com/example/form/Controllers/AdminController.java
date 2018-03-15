package com.example.form.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.form.Entities.Brand;
import com.example.form.Entities.Product;
import com.example.form.Entities.Store;
import com.example.form.Entities.User;
import com.example.form.Repository.BrandRepository;
import com.example.form.Repository.ProductRepsitory;
import com.example.form.Repository.StoreRepository;

@Controller
public class AdminController {
	
	@Autowired
	ProductRepsitory ProductRepo;
	@Autowired
	BrandRepository BrandRepo;
	@Autowired
	StoreRepository StoreRepo;
	
    @GetMapping("/AdminHomePage")
    public String AdminHomePage(HttpServletRequest req) 
    {
    	try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		
    		if(user.getType().equals("admin")) 
    			{return "AdminHomePage";}
    		else 
    			{return "index";}
    	}
    	catch(Exception e)
    	{
    		System.out.println("no input");
    		return "index";
    	}
    }
    
    
    
    @GetMapping("/AddProductAdmin")
    public String AddProductAdmin(Model model)
    {
        model.addAttribute("AddProductAdmin", new Product());
        return "AddProductAdmin";
    }
    
    @PostMapping("/AddProductAdmin")
    public String AddProductAdmin(@ModelAttribute Product product) 
    {	
    	if(ProductRepo.existsById(product.getName()))
    	{
    		System.out.println("name exists");
    		return "AddProductAdmin";
    	}
    	else
    	{
    		ProductRepo.save(product);
    	}

    	return "AdminHomePage";
    }
    


    @GetMapping("/AddNewBrand")
    public String AddNewBrand(Model model)
    {
        model.addAttribute("AddNewBrand", new Brand());
        return "AddNewBrand";
    } 
    
    @PostMapping("/AddNewBrand")
    public String AddNewBrand(@ModelAttribute Brand brand) 
    {	
    	if(BrandRepo.existsById(brand.getName()))
    	{
    		System.out.println("Brand exists");
    		return "AddNewBrand";
    	}
    	else
    	{
    		BrandRepo.save(brand);
    	}

    	return "AdminHomePage";
    }
    
    
    @GetMapping("/StoresApproval")
    public String ApproveStores(HttpServletRequest req,Model model) 
    {
    	try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		
    		if(user.getType().equals("admin")) 
    		{
    			List<Store> AllStores = new ArrayList<Store>();
    			Iterable<Store> StoreIterable = StoreRepo.findAll();
    			for(Store S:StoreIterable) 
    			{
    				if(!S.getState())
    					{AllStores.add(S);System.out.println(S.getStorename());}
    			}
    			model.addAttribute("stores", AllStores);
    			return "StoresApproval";
    		}
    		else 
    			{return "index";}
    	}
    	catch(Exception e)
    	{
    		System.out.println("no input");
    		return "index";
    	}
    }
    
    

}
