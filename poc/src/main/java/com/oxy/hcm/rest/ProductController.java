package com.oxy.hcm.rest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.hcm.dto.Employee;
import com.oxy.hcm.dto.Product;
import com.oxy.hcm.dto.ResponseTransaction;
import com.oxy.hcm.dto.TransactionDetails;
import com.oxy.hcm.services.employee.EmployeeService;
import com.oxy.hcm.services.product.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	@Resource(name = "productService")
	private ProductService productService;
	
	
	@RequestMapping(value = "/getProduct", method = RequestMethod.GET)
	@ResponseBody
	public Product login(@RequestParam("product_id") Long product_id) {
		Product product = null;
		try {
			Long productId = Long.valueOf(product_id);
			 product = productService.getProduct(productId);			

		} catch (Exception e) {
			System.out.println(e);
			
		}

		return product;
	}
	
	
	//@CrossOrigin(origins = "http://poc-donate4ummat.rhcloud.com")
	@RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
	@ResponseBody
	public ResponseTransaction addTransacction(@RequestBody String objJson,HttpServletRequest req) {
		ResponseTransaction rt = new ResponseTransaction();
		try {
			System.out.println("Header>>"+req.getHeaderNames());
			
			//Map<String, String> map = new HashMap<String, String>();

	        Enumeration headerNames = req.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String key = (String) headerNames.nextElement();
	            String value = req.getHeader(key);
	            System.out.println("key>>"+key);
	            System.out.println("value>>"+value);
	            //map.put(key, value);
	        }
			List<TransactionDetails> lstTransactionDetails= new ArrayList<TransactionDetails>();
			//String str= "{'userTransaction': {'email': 'user@gmail.com','phone': '9012345678','address': 'Golden hights, Dubai','country': 'UAE','orderId': 12345,'transactionId': 9074532778 }, 'Device': {'DeviceType': 'Android','DeviceVersion': '5.1','DeviceModel': 'XT1033','DeviceUUID': '1d70a028b1741e86' }, 'cart': [{'productId': 6,'productName': 'PAMPERS','productQuantity': 1,'productPrice': '600','productDiscount': '10','productDiscountedPrice': 60,'productFinalPrice': 540,'productCurrency': 'INR','$$hashKey': 'object:31'},	{'productId': 2,'productName': 'Creamed Honey','productQuantity': 1,'productPrice': '30','productDiscount': '2','productDiscountedPrice': 0.6,'productFinalPrice': 29.4,'productCurrency': 'INR','$$hashKey': 'object:35'},{'productId': 13,'productName': 'PARLE G','productQuantity': 1,'productPrice': '10','productDiscount': '1','productDiscountedPrice': 0.1,'productFinalPrice': 9.9,'productCurrency': 'INR'} ]}'";
			System.out.println(objJson);
			JSONObject obj = new JSONObject(objJson.trim());
			System.out.println(">>"+obj.get("userTransaction"));
			JSONObject objtransaction=(JSONObject) obj.get("userTransaction");
			//List<String> list = new ArrayList<String>();
			System.out.println(">>>>"+objtransaction.getString("payment_transactionId"));
			JSONArray array = obj.getJSONArray("cart");
			System.out.println(3);
			for(int i = 0 ; i < array.length() ; i++){
				TransactionDetails transactionDetails = new TransactionDetails();
				System.out.println(array.getJSONObject(i).getString("productName"));
			    //list.add(array.getJSONObject(i).getString("productName"));
				transactionDetails.setPayment_transaction_id(objtransaction.getString("payment_transactionId"));
			    transactionDetails.setCurr(array.getJSONObject(i).getString("productCurrency"));
			    transactionDetails.setProduct_id(array.getJSONObject(i).getLong("productId"));
			    transactionDetails.setDiscount(array.getJSONObject(i).getString("productDiscount"));
			    transactionDetails.setPrice(array.getJSONObject(i).getString("productPrice"));
			    transactionDetails.setQuantity(Long.toString(array.getJSONObject(i).getLong("productQuantity")));
			    transactionDetails.setFinal_price(Long.toString(array.getJSONObject(i).getLong("productFinalPrice")));
			    transactionDetails.setDiscount_price(Long.toString(array.getJSONObject(i).getLong("productDiscountedPrice")));
			    lstTransactionDetails.add(transactionDetails);
			}
			rt=productService.addTransaction(lstTransactionDetails);	
			//success="Success";

		} catch (Exception e) {
			System.out.println(e);
			
		}

		return rt;
	}



}
