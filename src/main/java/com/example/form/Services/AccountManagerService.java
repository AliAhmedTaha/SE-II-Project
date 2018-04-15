package com.example.form.Services;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.Controllers.AccountController;

@Controller
public class AccountManagerService {

	@Autowired
	AccountController Account;
	

	@GetMapping("/index")
    public String SigninForm() {return "index";}
	
	
    @PostMapping("/index")
    public String Signin(@RequestParam String name,@RequestParam String password,Model model,HttpServletRequest request) 
    {
    	try 
    	{
    		String AccountValidation=Account.validateUser(name, password, request);
        	System.out.println("accc"+AccountValidation);
        	
        	switch(AccountValidation) 
            {
        		case "admin":
        			System.out.println("swithc");
        			return"redirect:/AdminHomePage";
        				
        		case "Store Owner":
        			return"redirect:/StoreOwnerHomePage";
            				
        		case "Customer":
        			return"redirect:/CustomerHomePage";
        		
        		case "UserNotFound":
        			model.addAttribute("error", true);
        			model.addAttribute("message", "UserName Does Not Exist");
        			System.out.println("case:1 not fo");
        			return"index";
        		
        		case "WrongPassword":
        			model.addAttribute("error", true);
        			model.addAttribute("message", "Wrong Password");
        			System.out.println("case:1 not fo");
        			return"index";
        			
            	default:
            		return"index";
            		
            }
    	}
    	catch(Exception e) 
    	{
    		System.out.println("void -_-"+e);
    		return"index";
    	}
        	
    }
    
    
    @GetMapping("/signup")
    public String SignupForm() {return "signup";}
	
    @PostMapping("/signup")
    public String Signup(@RequestParam String username,@RequestParam String pass1,@RequestParam String pass2,@RequestParam String type) 
    {
        if(!(pass1.equals(pass2))) 
        {
        	System.out.println("Password is not the same"+"\n");
    		return "signup";
        }
        else 
        {
        	if(Account.createUser(username, pass1, type)) {return "index";}
        	else 
        	{
        		System.out.println("UserName Already Exists"+"\n");
        		return "signup";
        	}
        }
        
    }
    
    
}
