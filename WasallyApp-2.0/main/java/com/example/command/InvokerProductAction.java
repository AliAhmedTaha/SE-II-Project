package com.example.command;

import com.example.form.Entities.Store_Product;

public class InvokerProductAction {
public void ExecuteCommand(Command comm,Store_Product StoreProduct) {
	comm.DoAction(StoreProduct);
}
public void UnDoExecuteCommand(Command comm,Store_Product StoreProduct) {
	comm.UnDoAction(StoreProduct);
}
}
