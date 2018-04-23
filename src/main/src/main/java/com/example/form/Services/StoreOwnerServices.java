package com.example.form.Services;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.Controllers.AccountController;
import com.example.form.Controllers.BrandController;
import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.ProductLogActivityContrroler;
import com.example.form.Controllers.StoreController;
import com.example.form.Entities.LogProduct;
import com.example.form.Entities.Product;
import com.example.form.Entities.Store;
import com.example.form.Entities.User;
import com.example.form.Repository.StatisticsRepository;

@Controller
public class StoreOwnerServices {

	@Autowired
	private AccountController Account;
	@Autowired
	private ProductController productController;
	@Autowired
	private BrandController brandController;
	@Autowired
	private StoreController storeController;
	@Autowired
	StatisticsRepository StatRepo;
	@Autowired
	ProductLogActivityContrroler log;
	private String currentActiveStore;

	@GetMapping("/StoreOwnerHomePage")
	public String StoreOwnerHomePage(HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				return "StoreOwnerHomePage";
			}
			System.out.println("not a store owner !");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@GetMapping("/AddStore")
	public String ShowAddStorePage(HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				return "AddStore";
			}
			System.out.println("not a store owner !");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@PostMapping("/AddStore")
	public String AddStorePage(HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				User user = Account.getSessionUser(request);
				String StoreName = request.getParameter("name");
				String StoreAddress = request.getParameter("address");
				String StoreType = request.getParameter("type");
				if (storeController.addNewStore(request, StoreName, StoreAddress, StoreType, user)) {
					return "StoreOwnerHomePage";
				} else {
					System.out.println("store name already exists");
					return "AddStore";
				}
			}
			System.out.println("not a store owner !");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@GetMapping("/ViewStores")
	public String ViewStores(HttpServletRequest request, Model model) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				String UserName = Account.getSessionUser(request).getName();
				model.addAttribute("stores", storeController.getUserStores(UserName));
				return "ViewStores";
			}
			System.out.println("not a store owner !");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@GetMapping("/AddCollaborators")
	public String AddCollaborators(HttpServletRequest request, Model model) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				String UserName = Account.getSessionUser(request).getName();
				model.addAttribute("StoreOwners", Account.getStoreOwners(UserName, currentActiveStore));
				return "AddCollaborators";
			}
			return "redirect:/index";
		}
		return "redirect:/index";
	}

	@PostMapping("/AddCollaborators")
	public String AddCollaborator(HttpServletRequest request, Model model) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				String Collaborator = request.getParameter("StoreOwnerName");
				Account.AddCollaborator(request, currentActiveStore, Collaborator, "Collaborator");
				String UserName = Account.getSessionUser(request).getName();
				model.addAttribute("StoreOwners", Account.getStoreOwners(UserName, currentActiveStore));
				return "AddCollaborators";
			}
			return "redirect:/index";
		}
		return "redirect:/index";
	}

	@GetMapping("/StoreHomePage")
	public String StoreHomePage(@RequestParam String StoreName, HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				currentActiveStore = StoreName;
				if (Account.getPermission(StoreName, Account.getSessionUser(request).getName())) {
					return "StoreHomePage";
				}
				return "StoreHomePageCollaborator";
			}
			System.out.println("not a store owner !");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@GetMapping("/AddProductInStore")
	public String ShowAddProductPage(HttpServletRequest request, Model model) {

		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				model.addAttribute("Products", productController.getSystemProducts());
				model.addAttribute("Brands", brandController.getAllBrands());

				return "AddProductInStore";
			}
			System.out.println("not a Customer!");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@PostMapping("/AddProductInStore")
	public String AddProduct(HttpServletRequest request) {
		System.out.println("the oregnal");
		String ProductId = request.getParameter("product");
		String brand = request.getParameter("brand");
		Product product = productController.getSystemProductById(ProductId);
		Store store = storeController.getStore(currentActiveStore);
		double price = Double.parseDouble(request.getParameter("price"));
		int Quan = Integer.parseInt(request.getParameter("quan"));

		if (productController.checkPriceRange(product, price)) {
			productController.AddProductInStore(product, store, price, brand, Quan);
			return "redirect:/StoreHomePage?StoreName=" + currentActiveStore;
		}
		return "AddProductInStore";
	}
	@GetMapping("/AddProductInStoreCollaborator")
	public String ShowAddProductPagecollabrator(HttpServletRequest request, Model model) {

		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				model.addAttribute("Products", productController.getSystemProducts());
				model.addAttribute("Brands", brandController.getAllBrands());

				return "AddProductInStoreCollaborator";
			}
			System.out.println("not a Customer!");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}

	@PostMapping("/AddProductInStoreCollaborator")
	public String AddProductcollabrator(HttpServletRequest request) {

		String ProductId = request.getParameter("product");
		String brand = request.getParameter("brand");
		Product product = productController.getSystemProductById(ProductId);
		Store store = storeController.getStore(currentActiveStore);
		double price = Double.parseDouble(request.getParameter("price"));
		int Quan = Integer.parseInt(request.getParameter("quan"));
		System.out.println(product.getName());
		System.out.println(store.getStorename());
		if (productController.checkPriceRange(product, price)) {
			productController.AddProductInStorecollaborator(product, store, price, brand, Quan);
			return "redirect:/StoreHomePage?StoreName=" + currentActiveStore;
		}
		return "AddProductInStore";
	}

	@GetMapping("/ViewStoreProducts")
    public String ShowStoreProducts(HttpServletRequest request,Model model) 
    {
		if(Account.getSessionUser(request) != null) 
    	{
    		if(Account.getSessionUser(request).getType().equals("Store Owner")) 
    		{

    			        		
    			return "ExploreStore";
    		}	
    		System.out.println("not a Customer!");
    		return "redirect:/index";
    	}	
    	System.out.println("no session");
    	return "redirect:/index";
    }

	@GetMapping("/ViewStatistics")
	public String ViewStat(Model model, HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {

				Store mystore = storeController.getStore(currentActiveStore);
				if (mystore == null) {
					model.addAttribute("value4", "no store with this name");
					return "ViewStatistics";
				} else {
					String SoldoutProducts = storeController.getSoldoutProducts(currentActiveStore);
					model.addAttribute("value1", mystore.getNumbuyers());
					model.addAttribute("value2", mystore.getNumvisitors());
					model.addAttribute("value3", SoldoutProducts);
					model.addAttribute("value4", "");
					return "ViewStatistics";
				}
			}
			System.out.println("not a Customer!");
			return "redirect:/index";
		}
		System.out.println("no session");
		return "redirect:/index";
	}
////////////////////////////////////////////////////////////////////////////////////////	
	@GetMapping("/CollaboratorsActions")
	public String CollaboratorsActions(Model model, HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {	
				ArrayList<LogProduct>data = log.getlog(currentActiveStore);
				model.addAttribute("Collaborators",data);
				// Logic to view actions of collaborators in the table 
				
				return "CollaboratorsActions";
			}
			return "redirect:/index";
		}
		return "redirect:/index";
	}
	
	@PostMapping("/CollaboratorsActions")
	public String Undo(Model model, HttpServletRequest request) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				String productname = request.getParameterValues("check")[0];
				productController.undo(productController.getStoreProduct(productname));

				
				return "CollaboratorsActions";
			}
			return "redirect:/index";
		}
		return "redirect:/index";
	}
////////////////////////////////////////////////////////////////////////////////////////////
}
