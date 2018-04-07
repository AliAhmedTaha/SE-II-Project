package com.example.form.Controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.User;
import com.example.form.Repository.UserRepository;

@Component
public class AccountController {

	@Autowired
	UserRepository userRepo;
	
	public AccountController() { }
	
	public String validateUser(String userName, String password, HttpServletRequest request)
	{
		try
        {        	
			User userAccount=userRepo.findById(userName).get();
        	System.out.println(userAccount.getName()+"   "+userAccount.getPassword()+"  "+userAccount.getType());
        	if(userAccount.getPassword().equals(password)) 
        	{	
    			request.getSession().setAttribute("user", userAccount);
    			return userAccount.getType();
        	}
        	else {return "WrongPassword";}
        }       
        catch (Exception e) 
        {
        	System.out.println("UserNotFound");
        	return "UserNotFound";
		}
	}
	
	public boolean createUser(String userName, String password, String type) 
	{
		try 
        {
        	userRepo.save(new User(userName,password,type));
        	return true;
        }
        catch(Exception ex)
        {
        	System.out.println(ex);
        	return false;
        }
	}
	
	public User getSessionUser(HttpServletRequest request) 
	{
		try 
    	{
    		User user=new User((User) request.getSession().getAttribute("user"));
    	    return user;
  
    	}
    	catch(Exception e)
    	{
    		System.out.println("from getSessionUser : "+e);
    		return null;
    	}
	}

}
