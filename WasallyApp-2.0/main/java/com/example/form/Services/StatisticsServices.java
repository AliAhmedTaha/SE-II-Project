package com.example.form.Services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.form.Entities.User;
import com.example.form.Controllers.AccountController;
import com.example.form.Controllers.ProductController;
import com.example.form.Controllers.StatisticsController;
import com.example.form.Controllers.StoreController;
import com.example.form.Entities.Product;
import com.example.form.Entities.Statistics;
import com.example.form.Entities.Store_Product;
import com.example.form.Repository.ProductRepsitory;
import com.example.form.Repository.StatisticsRepository;
import com.example.form.Repository.Store_productRepository;

@Controller
public class StatisticsServices {
	@Autowired
	StatisticsController statcontrol;
	@Autowired
	StatisticsRepository StatRepo;
	@Autowired
	Store_productRepository S_PRepo;
	@Autowired
	ProductRepsitory ProductRepo;
	@Autowired
	private AccountController Account;
	@Autowired
	private StoreController storeController;
	@Autowired
	private ProductController productcontroller;
	
	@GetMapping("/AddStatistics")
	public String ViewStatistics(HttpServletRequest req, Model model) {
		try {
			User user = new User((User) req.getSession().getAttribute("user"));
			if (user.getType().equals("admin")) {
				List<Statistics> Statlist = statcontrol.getStatistics();
				if (Statlist == null)
					return "AddStatistics";
				model.addAttribute("Statistics", Statlist);
				return "AddStatistics";
			} else {
				return "redirect:index";
			}
		} catch (Exception e) {
			return "redirect:AdminHomePage";
		}
	}

	@PostMapping("/AddStatistics")
	public String AproveStatus(HttpServletRequest req, Model model) {
		try {
			User user = new User((User) req.getSession().getAttribute("user"));
			if (user.getType().equals("admin")) {

				String[] approvedStat = req.getParameterValues("check");
				statcontrol.AproveStatistics(approvedStat);
				System.out.println(approvedStat.length);
				List<Statistics> Statlist = statcontrol.getStatistics();
				if (Statlist == null)
					return "AddStatistics";
				model.addAttribute("Statistics", Statlist);
				return "AddStatistics";
			} else {
				return "redirect:index";
			}
		} catch (Exception e) {
			return "redirect:AdminHomePage";
		}
	}


	@GetMapping("/SystemStatistics")
	public String SystemStatisticsPage(HttpServletRequest request, Model model) {

		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner"))
				return "SystemStatistics";
			return "redirect:/index";
		}
		return "redirect:/index";
	}

	@GetMapping("/UserStatistics")
	public String UserStatisticsPage(HttpServletRequest request, Model model) {
		if (Account.getSessionUser(request) != null) {
			if (Account.getSessionUser(request).getType().equals("Store Owner")) {
				if (StatRepo.existsById("UserStatistics")) {
					if (StatRepo.findById("UserStatistics").get().getStatus()) {
							String me = Account.getSessionUser(request).getName();
							String Max = storeController.getMaxVisitedStore(me);
							String Min = storeController.getMinVisitedStore(me);
							int sum = storeController.getNumberOfVisitedStores(me);
							float avg = storeController.getAverageOfVisitedStores(me);
							model.addAttribute("value1", Max);
							model.addAttribute("value2", Min);
							model.addAttribute("value3", sum);
							model.addAttribute("value4", avg);
							return "UserStatistics";
					}
				} else
					return "SystemStatistics";
			}
		}
		return "redirect:/index";
	}

	@GetMapping("/ProductStatistics")
	public String ProductStatisticsPage(HttpServletRequest req, Model model) {
		if (Account.getSessionUser(req) != null) {
			if (Account.getSessionUser(req).getType().equals("Store Owner")) {

				if (StatRepo.existsById("ProductStatistics")) {
					if (StatRepo.findById("ProductStatistics").get().getStatus())
					{
						try {
								
								Map<String, Integer> productmap = productcontroller.getpoductmap();
								System.out.println("1"+productmap.size());
								@SuppressWarnings("rawtypes")
								Map.Entry HighestProduct = productcontroller.GetHighestProduct(productmap);
								System.out.println("2"+HighestProduct.getKey());
								@SuppressWarnings("rawtypes")
								Map.Entry lowestProduct = productcontroller.GetlowestProduct(productmap);
								int numofSoldBroducts= productcontroller.getTottalSoldBroduct(productmap);
								int avrage= numofSoldBroducts/productmap.size();
								System.out.println("3"+HighestProduct.getValue());
								model.addAttribute("higstProductName", HighestProduct.getKey());
								model.addAttribute("value3",numofSoldBroducts);
								model.addAttribute("value4",avrage);
								model.addAttribute("higstProductValue", HighestProduct.getValue());
								model.addAttribute("lowestProductName", lowestProduct.getKey());
								model.addAttribute("lowestProductValue", lowestProduct.getValue());
								return "ProductStatistics";
							}
						 catch (Exception e) {
							 System.out.println(e);
							return "SystemStatistics";
						}
					}
						return "ProductStatistics";
				} else
					return "SystemStatistics";
			}
		}
		return "redirect:/index";
	}

	
}
