package com.oxy.s3m.notification.controller.seller;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.s3m.notification.beans.seller.SellerDetailsBean;
import com.oxy.s3m.notification.convertor.PushNotificationConvertor;
import com.oxy.s3m.notification.convertor.SellerDetailsActiveConverter;
import com.oxy.s3m.notification.convertor.SellerDetailsConverter;
import com.oxy.s3m.notification.model.seller.Notification;
import com.oxy.s3m.notification.model.seller.SellerDetails;
import com.oxy.s3m.notification.service.seller.SellerService;
import com.oxy.s3m.notification.validators.PushNotificationValidator;
import com.oxy.s3m.notification.validators.SellerDetailsActiveValidator;
import com.oxy.s3m.notification.validators.SellerDetailsValidator;

@RestController
@RequestMapping("/seller")
public class SellerRestController {
	
	private static final Logger LOGGER  = Logger
			.getLogger(SellerRestController.class);
	
	@Autowired
	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	//-------------------Create a User--------------------------------------------------------
	@RequestMapping(value = "/registration", method = RequestMethod.POST)	
	public @ResponseBody SellerDetailsBean createSeller(@RequestBody String objJson) {
		LOGGER.debug("Logging Start---!");
		final JSONObject obj = new JSONObject(objJson.trim());
		JSONObject objcustomer=(JSONObject) obj.get("sellerDetails");
		
		SellerDetailsValidator validator = new SellerDetailsValidator();
		final JSONObject jObject = validator.validate(objcustomer);
		
		SellerDetailsBean sellerDetailsBean = new SellerDetailsBean();
		
		if(!(Boolean) jObject.get("validate")){
			
			for(Object key : jObject.keySet()){
				if("validate".equals(key)){
					continue;
				}
				String msg = sellerDetailsBean.getMessage() == null ? "" : sellerDetailsBean.getMessage(); 
				sellerDetailsBean.setMessage( msg + jObject.getString((String)key) + "\n");
			}			
			sellerDetailsBean.setStatus("Failed");
			return sellerDetailsBean;
		}
		
		
  		try{
			
			if(objcustomer.getString("agree")==null 
					|| objcustomer.getString("agree").toString().equals("") 
					|| !objcustomer.getString("agree").toString().equals("Yes")){
				
				sellerDetailsBean.setMessage("Terms and Condition should be accepted");				
				sellerDetailsBean.setStatus("Failed");
				return sellerDetailsBean;
			}
			
			final SellerDetailsConverter sellerDetailsConverter = new SellerDetailsConverter();
			SellerDetails seller = sellerDetailsConverter.convertSellerDetails(objcustomer);
			seller = sellerService.saveSeller(seller);
			sellerDetailsBean.setSellerId(seller.getSellerId());
			sellerDetailsBean.setSellerName(seller.getSellerName());
			sellerDetailsBean.setStatus("success");
			sellerDetailsBean.setMessage("Seller has created successfully");
			
		}catch(Exception ex){
			LOGGER.error("Error occured while adding seller details " + ex.getMessage());
			ex.printStackTrace();
			sellerDetailsBean.setMessage(ex.getMessage());			
			sellerDetailsBean.setStatus("Failed");
		}
		return sellerDetailsBean;
	}
	
	//-------------------Create a User--------------------------------------------------------
		@RequestMapping(value = "/pushNotification", method = RequestMethod.POST)	
		public @ResponseBody SellerDetailsBean pushNotification(@RequestBody String objJson) {
			LOGGER.debug("Logging Start---!");
			final JSONObject obj = new JSONObject(objJson.trim());
			JSONObject objcustomer=(JSONObject) obj.get("pushnotification");
			
			PushNotificationValidator validator = new PushNotificationValidator();
			final JSONObject jObject = validator.validate(objcustomer);
			
			SellerDetailsBean sellerDetailsBean = new SellerDetailsBean();
			
			if(!(Boolean) jObject.get("validate")){
				
				for(Object key : jObject.keySet()){
					if("validate".equals(key)){
						continue;
					}
					String msg = sellerDetailsBean.getMessage() == null ? "" : sellerDetailsBean.getMessage(); 
					sellerDetailsBean.setMessage( msg + jObject.getString((String)key) + "\n");
				}			
				sellerDetailsBean.setStatus("Failed");
				return sellerDetailsBean;
			}
			
			
	  		try{
				
	  			final PushNotificationConvertor pushNotificationConvertor = new PushNotificationConvertor();
	  			Notification notification = pushNotificationConvertor.convertSellerDetails(objcustomer);
				
	  			sellerDetailsBean=sellerService.sendNotification(notification);
				
				
			}catch(Exception ex){
				LOGGER.error("Error occured while adding seller details " + ex.getMessage());
				ex.printStackTrace();
				sellerDetailsBean.setMessage(ex.getMessage());			
				sellerDetailsBean.setStatus("Failed");
			}
			return sellerDetailsBean;
		}


//	private boolean isUserAuthenticated(String authString){
//        
//        String decodedAuth = "";
//        // Header is in the format "Basic 5tyc0uiDat4"
//        // We need to extract data before decoding it back to original string
//        String[] authParts = authString.split("\\s+");
//        String authInfo = authParts[1];
//        // Decode the data back to original string
//        byte[] bytes = null;
//        try {
//            bytes = new BASE64Decoder().decodeBuffer(authInfo);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        decodedAuth = new String(bytes);
//        System.out.println(decodedAuth);
//         
//        /**
//         * here you include your logic to validate user authentication.
//         * it can be using ldap, or token exchange mechanism or your 
//         * custom authentication mechanism.
//         */
//        // your validation code goes here....
//         
//        return true;
//    }
	/* //------------------- Update a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> updateUser(@PathVariable("id") long id, @RequestBody Customer user) {
        System.out.println("Updating User " + id);

        Customer currentUser = custService.findById(id);

        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        custService.updateUser(currentUser);
        return new ResponseEntity<Customer>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a User --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        Customer user = custService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        custService.deleteUserById(id);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteAllUsers() {
        System.out.println("Deleting All Users");

        custService.deleteAllUsers();
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }

	 */
		

		@RequestMapping(value = "/active", method = RequestMethod.POST)	
		public @ResponseBody SellerDetailsBean active(@RequestBody String objJson) {
			LOGGER.debug("Logging Start---!");
			final JSONObject obj = new JSONObject(objJson.trim());
			JSONObject objcustomer=(JSONObject) obj.get("sellerDetails");
			
			SellerDetailsActiveValidator validator = new SellerDetailsActiveValidator();
			final JSONObject jObject = validator.validate(objcustomer);
			
			SellerDetailsBean sellerDetailsBean = new SellerDetailsBean();
			
			if(!(Boolean) jObject.get("validate")){
				
				for(Object key : jObject.keySet()){
					if("validate".equals(key)){
						continue;
					}
					String msg = sellerDetailsBean.getMessage() == null ? "" : sellerDetailsBean.getMessage(); 
					sellerDetailsBean.setMessage( msg + jObject.getString((String)key) + "\n");
				}			
				sellerDetailsBean.setStatus("Failed");
				return sellerDetailsBean;
			}
			
			
	  		try{
				
				if(objcustomer.getString("agree")==null 
						|| objcustomer.getString("agree").toString().equals("") 
						|| !objcustomer.getString("agree").toString().equals("Yes")){
					
					sellerDetailsBean.setMessage("Terms and Condition should be accepted");				
					sellerDetailsBean.setStatus("Failed");
					return sellerDetailsBean;
				}
				
				final SellerDetailsActiveConverter sellerDetailsConverter = new SellerDetailsActiveConverter();
				SellerDetails seller = sellerDetailsConverter.convertSellerDetails(objcustomer);
				seller = sellerService.active(seller);
				sellerDetailsBean.setSellerId(seller.getSellerId());
				sellerDetailsBean.setSellerName(seller.getSellerName());
				sellerDetailsBean.setStatus("success");
				sellerDetailsBean.setMessage("Seller has been activated successfully");
				
			}catch(Exception ex){
				LOGGER.error("Error occured while adding seller details " + ex.getMessage());
				ex.printStackTrace();
				sellerDetailsBean.setMessage(ex.getMessage());			
				sellerDetailsBean.setStatus("Failed");
			}
			return sellerDetailsBean;
		}

}