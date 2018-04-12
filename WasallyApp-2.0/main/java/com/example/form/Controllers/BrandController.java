package com.example.form.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.form.Entities.Brand;
import com.example.form.Repository.BrandRepository;

@Component
public class BrandController {

	@Autowired
	private BrandRepository BrandRepo;
	
	public BrandController() {}
	
	public boolean AddBrand(Brand brand) 
	{
		if(BrandRepo.existsById(brand.getName()))
    	{
    		System.out.println("Brand exists");
    		return false;
    	}
    	else
    	{
    		BrandRepo.save(brand);
    		return true;
    	}
	}
	
	public ArrayList<Brand> getAllBrands()
	{
		ArrayList<Brand> AllBrands = new ArrayList<Brand>();
		Iterable<Brand> BrandIterable = BrandRepo.findAll();
		for(Brand B:BrandIterable) {AllBrands.add(B);}
		
		return AllBrands;
	}

}
