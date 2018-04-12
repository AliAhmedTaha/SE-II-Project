package com.example.form.Services;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.form.Controllers.AccountController;
import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.StoreController;
import com.example.form.Entities.Store;
import com.example.form.Entities.Store_Product;
import com.example.form.Entities.User;
import com.example.form.Repository.StoreRepository;

@Controller
public class CustomerServices {

	@Autowired
	private AccountController Account;
	@Autowired
	private ProductController productController;
	@Autowired
	private StoreController storeController;
	@Autowired
	private StoreRepository StoreRepo;
	
	
	@GetMapping("/CustomerHomePage")
    public String ShowCustomerHomePage(HttpServletRequest req,Model model) 
    {
    	try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		
    		if(user.getType().equals("Customer")) 
    		{
    			
    			model.addAttribute("stores", storeController.getAllStores());
    			return "CustomerHomePage";
    		}
    		else 
    			{return "redirect:/index";}
    	}
    	catch(Exception e)
    	{
    		System.out.println("no Session Created");
    		return "redirect:/index";
    	}
    }
	
	@GetMapping("/ExploreStore")
    public String ShowStoreProducts(HttpServletRequest request,Model model) 
    {
		if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("Customer")) 
    		{
    			String StoreName=request.getParameter("StoreId");
    			model.addAttribute("store", StoreName);
    			model.addAttribute("products", storeController.getProductsInStore(StoreName));
    			/// //////////////////////////////////////////////////
    			Store myStore = StoreRepo.findById(StoreName).get();
        		StoreRepo.delete(myStore);
        		myStore.setNumvisitors(myStore.getNumvisitors()+1);
        		StoreRepo.save(myStore);        		
    			return "ExploreStore";
    		}	
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}	
    	System.out.println("no session");
    	return "redirect:/index";
    }

	
	@GetMapping("/ProductDetails")
    public String ShowProductDetails(HttpServletRequest request,Model model) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("Customer")) 
    		{
    			String StoreName=request.getParameter("StoreId");
        		String ProductName=request.getParameter("ProductId");
        		Store_Product productInStore=productController.getStoreProductById(StoreName, ProductName);
        		
    			model.addAttribute("store", StoreName);
    			model.addAttribute("product", ProductName);
    			model.addAttribute("brand", productInStore.getBrandId());
    			model.addAttribute("quantity", productInStore.getQuantity());
    			model.addAttribute("price", productInStore.getPrice());
    			
    			return "ProductDetails";
    		}
    		
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }
	


}
