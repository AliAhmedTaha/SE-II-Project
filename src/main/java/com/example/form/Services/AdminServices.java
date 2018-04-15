package com.example.form.Services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.form.Controllers.AccountController;
import com.example.form.Controllers.BrandController;
import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.StoreController;
import com.example.form.Entities.Brand;
import com.example.form.Entities.Product;

@Controller
public class AdminServices {
		
	@Autowired
	private AccountController Account;
	@Autowired
	private ProductController productController;
	@Autowired
	private BrandController brandController;
	@Autowired
	private StoreController storeController;
	
	
	@GetMapping("/AdminHomePage")
    public String AdminHomePage(HttpServletRequest request) 
    {	
		if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			return "AdminHomePage";
    		}
    		else 
    		{
    			System.out.println("not an admin!");
    			return "redirect:/index";
    		}
    	}	
    	System.out.println("no session");
    	return "redirect:/index";   	
    }
    
    
    @GetMapping("/AddProductAdmin")
    public String AddProductAdmin(Model model, HttpServletRequest request)
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			model.addAttribute("AddProductAdmin", new Product());
    	        return "AddProductAdmin";
    		}
    		System.out.println("not an admin!");
    		return "redirect:/index";
    	}	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }
    
    @PostMapping("/AddProductAdmin")
    public String AddProductAdmin(@ModelAttribute Product product) 
    {	
    	if(productController.AddProductInSystem(product))
    	{
    		return "AdminHomePage";
    	}
    	else
    	{
    		System.out.println("name exists");
    		return "AddProductAdmin";
    	}

    	
    }
    
    
    @GetMapping("/AddNewBrand")
    public String AddNewBrand(Model model,HttpServletRequest request)
    {
    	
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			model.addAttribute("AddNewBrand", new Brand());
    			return "AddNewBrand";
    		}
    		else 
    		{
    			System.out.println("not an admin!");
    			return "redirect:/index";
    		}
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    } 
    
    @PostMapping("/AddNewBrand")
    public String AddNewBrand(@ModelAttribute Brand brand) 
    {	
    	if(brandController.AddBrand(brand)) {return "AdminHomePage";}
    	else
    	{
    		System.out.println("Brand exists");
    		return "AddNewBrand";
    	}
   
    }

    
    @GetMapping("/StoresApproval")
    public String ShowApproveStores(HttpServletRequest request,Model model) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			model.addAttribute("stores", storeController.getUnApprovedStores());
    			return "StoresApproval";
    		}
    		System.out.println("not an admin!");
    		return "redirect:/index";
    	}	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }
    
    @PostMapping("/StoresApproval")
    public String ApproveStores(HttpServletRequest req,Model model) 
    {
    	try
    	{
    		String[] approve_Stores= req.getParameterValues("check");
    		System.out.println(approve_Stores.length);
    		
    		storeController.approveStores(approve_Stores);
    		
    		System.out.println("--- done ---");
    		return"redirect:/StoresApproval";
    	}
    	
    	catch(Exception e) 
    	{
    		System.out.println(e);
    		return"redirect:/StoresApproval";
    	}
    	
    }

    
    
    

}
