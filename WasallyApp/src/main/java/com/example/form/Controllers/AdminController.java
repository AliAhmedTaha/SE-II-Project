package com.example.form.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.form.Entities.Product;
import com.example.form.Repository.ProductRepsitory;

@Controller
public class AdminController {
	
	@Autowired
	ProductRepsitory ProductRepo;
	
    @GetMapping("/AdminHomePage")
    public String AdminHomePage() 
    {
        return "AdminHomePage";
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
    	if(ProductRepo.exists(product.getName()))
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

}
