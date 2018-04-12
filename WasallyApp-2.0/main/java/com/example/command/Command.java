package com.example.command;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.ProductLogActivityContrroler;
import com.example.form.Entities.Store_Product;

public abstract class Command {
@Autowired  
protected ProductController productcontroler;
@Autowired
protected ProductLogActivityContrroler log;
public abstract void DoAction(Store_Product StoreProduct);
public abstract Store_Product UnDoAction(Store_Product StoreProduct);
}
