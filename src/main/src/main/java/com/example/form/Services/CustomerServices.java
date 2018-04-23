package com.example.form.Services;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.form.Controllers.AccountController;
import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.StoreController;
import com.example.form.Entities.Store_Product;
import com.example.form.Entities.User;

@Controller
public class CustomerServices {

	@Autowired
	private AccountController Account;
	@Autowired
	private ProductController productController;
	@Autowired
	private StoreController storeController;
	
	
	
	@GetMapping("/CustomerHomePage")
    public String ShowCustomerHomePage(HttpServletRequest req,Model model) 
    {
    	try 
    	{
    		User user=new User((User) req.getSession().getAttribute("user"));
    		
    		if( !user.getType().equals("admin")) 
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
    		if(!Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			String StoreName=request.getParameter("StoreId");
    			model.addAttribute("store", StoreName);
    			model.addAttribute("products", storeController.getProductsInStore(StoreName));
    			
    			storeController.incrementStoreVisits(StoreName);
    			        		
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
    		if(!Account.getSessionUser(request).getType().equals("admin")) 
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
	

	
	@GetMapping("/OrderDetails")
    public String ShowOrderDetails(HttpServletRequest request,Model model) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(!Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			String StoreName=request.getParameter("storeName");
        		String ProductName=request.getParameter("productName");
        		
    			model.addAttribute("store", StoreName);
    			model.addAttribute("product", ProductName);
    			
    			return "OrderDetails";
    		}
    		
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }

	
	@PostMapping("/OrderDetails")
    public String ValidateOrderDetails(HttpServletRequest request,Model model) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		if(!Account.getSessionUser(request).getType().equals("admin")) 
    		{
    			String StoreName=request.getParameter("storeName");
        		String ProductName=request.getParameter("productName");
        		Store_Product productInStore=productController.getStoreProductById(StoreName, ProductName);
        		
        		int orderedQuantity = Integer.parseInt(request.getParameter("quantity"));
        		
        		if(productInStore.getQuantity() >= orderedQuantity && orderedQuantity>0) 
        		{
        			return "redirect:/ConfirmOrder?StoreId="+StoreName+"&ProductId="+ProductName+"&quan="+orderedQuantity;
        		}
        		
    			model.addAttribute("notAvail", true);
    			model.addAttribute("store", StoreName);
    			model.addAttribute("product", ProductName);
    			
    			return "OrderDetails";
    		}
    		
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }
	
	@GetMapping("/ConfirmOrder")
    public String ConfirmOrderPage(HttpServletRequest request,Model model) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		String AccountType=Account.getSessionUser(request).getType();
    		if(!AccountType.equals("admin")) 
    		{
    			String StoreName=request.getParameter("StoreId");
        		String ProductName=request.getParameter("ProductId");
        		int quantity=Integer.parseInt(request.getParameter("quan"));
        		Store_Product productInStore=productController.getStoreProductById(StoreName, ProductName);
        		
        		double totalPrice=Account.calculateOrderPrice(quantity, productInStore.getPrice(), AccountType);
        		
    			model.addAttribute("store", StoreName);
    			model.addAttribute("product", ProductName);
    			model.addAttribute("quantity", quantity);
    			model.addAttribute("price", totalPrice);
    			
    			return "ConfirmOrder";
    		}
    		
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }

	
	@PostMapping("/ConfirmOrder")
    public String ConfirmOrder(HttpServletRequest request) 
    {
    	if(Account.getSessionUser(request) != null) 
    	{
    		String AccountType=Account.getSessionUser(request).getType();
    		if(!AccountType.equals("admin")) 
    		{
    			String StoreName=request.getParameter("storeName");
        		String ProductName=request.getParameter("productName");
        		int quantity=Integer.parseInt(request.getParameter("quantity"));
        		
        		productController.buyProduct(StoreName, ProductName, quantity);
        		storeController.updateStoreBuyers(StoreName);
    			
    			return "redirect:/CustomerHomePage";
    		}
    		
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}
    	
    	System.out.println("no session");
    	return "redirect:/index";
    	
    }

	

}
