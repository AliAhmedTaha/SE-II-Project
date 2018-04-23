package com.example.form.command;

import org.springframework.stereotype.Component;

import com.example.form.Entities.Store_Product;
@Component
public class InvokerProductAction {
public void ExecuteCommand(Command comm,Store_Product StoreProduct) {
	comm.DoAction(StoreProduct);
}
public void UnDoExecuteCommand(Command comm,Store_Product StoreProduct) {
	comm.UnDoAction(StoreProduct);
}
}
