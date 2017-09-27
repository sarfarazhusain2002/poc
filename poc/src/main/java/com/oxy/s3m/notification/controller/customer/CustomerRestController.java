package com.oxy.s3m.notification.controller.customer;



import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.s3m.notification.beans.customer.CustomerDetailsBean;
import com.oxy.s3m.notification.beans.customer.MessageListBean;
import com.oxy.s3m.notification.beans.customer.Notification;
import com.oxy.s3m.notification.beans.customer.ReqGetNotification;
import com.oxy.s3m.notification.convertor.CustomerConvertor;
import com.oxy.s3m.notification.convertor.ReqGetNotificationsConvertor;
import com.oxy.s3m.notification.exception.CustomerException;
import com.oxy.s3m.notification.model.customer.CustomerDetails;
import com.oxy.s3m.notification.service.customer.CustomerService;
import com.oxy.s3m.notification.validators.CustomerValidator;


@RestController
@RequestMapping("/customer")
public class CustomerRestController {
	private static final Logger logger = Logger
			.getLogger(CustomerRestController.class);

	@Autowired
	CustomerService custService;  //Service which will do all data retrieval/manipulation work


	public void setCustService(CustomerService custService) {
		this.custService = custService;
	}

	//-------------------Create a User--------------------------------------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST)	
	public @ResponseBody CustomerDetailsBean createCustomer(@RequestBody String objJson) {
		logger.debug("Logging Start createCustomer ---!");
		final JSONObject obj = new JSONObject(objJson.trim());
		
		logger.debug(">>"+obj.get("customerDetails"));
		JSONObject objcustomer=(JSONObject) obj.get("customerDetails");
		
		CustomerValidator validator = new CustomerValidator();
		logger.debug("Going to validate customer");
		final JSONObject validatedCustobj=validator.Validate(objcustomer);
		
		 
		CustomerDetailsBean customerDetailsBean = new CustomerDetailsBean();
		CustomerDetails customer = null;
		
		try{
			
			logger.debug("Validation Result"+validatedCustobj.toString());
			if((Boolean) validatedCustobj.get("validate")){
				logger.debug("Going to convert json to customer...!");
				 customer=CustomerConvertor.convert(objcustomer);
			}else{
				logger.debug("Validation got faailed...!");
				customerDetailsBean.setStatus("Failed");				
				customerDetailsBean.setMessage(validatedCustobj.toString());
				return customerDetailsBean;
			}
			logger.debug("Going to save customer...!");
			    customer=custService.saveCustomer(customer);			
				logger.debug("Customer has created successfully....!");
				customerDetailsBean.setCustId(customer.getCustId());
				customerDetailsBean.setStatus(customer.getStatus());
				customerDetailsBean.setCustName(customer.getCustName());
				customerDetailsBean.setMessage("Customer created successfully...!");
			
			System.out.println("End");

		}
		catch(CustomerException ex){
			ex.printStackTrace();
			logger.debug(ex.getMessage());
			logger.debug(ex.getStackTrace());
			customerDetailsBean.setMessage(ex.getMessage());

			customerDetailsBean.setStatus("Failed");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.debug(ex.getMessage());
			logger.debug(ex.getStackTrace());
			customerDetailsBean.setMessage(ex.getMessage());

			customerDetailsBean.setStatus("Failed");
		}finally{
			customer=null;

		}
		//System.out.println("***************");
		//System.out.println("$$$"+customerDetailsBean.getCustName());
		return customerDetailsBean;

		//        HttpHeaders headers = new HttpHeaders();
		//        //headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri());
		//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}


	//------------------- Update a User --------------------------------------------------------

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT)
	public @ResponseBody CustomerDetailsBean updateCustomer(@RequestBody String objJson) {


		logger.debug("Logging Start---!");
		JSONObject obj = new JSONObject(objJson.trim());
		System.out.println(">>"+obj.get("customerDetails"));
		JSONObject objcustomer=(JSONObject) obj.get("customerDetails");


		CustomerDetails customer = new CustomerDetails();

		CustomerDetailsBean customerDetailsBean = new CustomerDetailsBean();

		try{
			customer.setDeviceId(objcustomer.getString("deviceId"));
			customer.setFcmToken(objcustomer.getString("fcmToken"));
			customer.setCustId(Integer.parseInt(objcustomer.getString("custid")));
			custService.updateUser(customer);
			customerDetailsBean.setStatus("Success");
			customerDetailsBean.setMessage("Updated Successfully");
			customerDetailsBean.setCustId(Integer.parseInt(objcustomer.getString("custid")));

		}catch(Exception ex){
			custService.updateUser(customer);
			customerDetailsBean.setStatus("Failes");
			customerDetailsBean.setMessage(ex.getMessage());
			customerDetailsBean.setCustId(Integer.parseInt(objcustomer.getString("custid")));
		}

		return customerDetailsBean;

	}


	@RequestMapping(value = "/getNotifications", method = RequestMethod.PUT)
	public @ResponseBody List<Notification> getMessages(@RequestBody String objJson) throws JSONException, ParseException {


		logger.debug("Logging Start---!");
		JSONObject obj = new JSONObject(objJson.trim());
		System.out.println(">>"+obj.get("getNotification"));
		JSONObject objcustomer=(JSONObject) obj.get("getNotification");


		CustomerDetails customer = new CustomerDetails();

		MessageListBean messageListBean = new MessageListBean();
		
		ReqGetNotification reqGetNotification=ReqGetNotificationsConvertor.convert(objcustomer);
		List<Notification> lstNotification=null;

		try{
			
			 lstNotification=custService.getNotifications(reqGetNotification);
			System.out.println(lstNotification.size());
			

		}catch(Exception ex){
			//custService.updateUser(customer);
			///customerDetailsBean.setStatus("Failes");
			//customerDetailsBean.setMessage(ex.getMessage());
			//customerDetailsBean.setCustId(Integer.parseInt(objcustomer.getString("custid")));
			ex.printStackTrace();
		}

		return lstNotification;

	}

}