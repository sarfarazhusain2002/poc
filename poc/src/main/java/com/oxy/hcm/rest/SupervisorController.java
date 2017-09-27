package com.oxy.hcm.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oxy.hcm.models.HcmEmp;
import com.oxy.hcm.services.supervisor.SupervisorService;



@RestController
@RequestMapping("/rest")
public class SupervisorController {

	@Autowired
	@Resource(name = "supervisorService")
	private SupervisorService supervisorService;
		
	
	
	@RequestMapping(value = "/supervisor/{empId}", method = RequestMethod.GET)
	@ResponseBody
	public List<HcmEmp> getEmpList(@PathVariable("empId") long empId) {
		return supervisorService.getEmpList(empId);
	}

	
	@RequestMapping(value = "/supervisor/{id}/{status}/{supid}", method = RequestMethod.GET)
	@ResponseBody
	public List<HcmEmp> updateLeave(@PathVariable("id") int id,@PathVariable("status") String status,@PathVariable("supid") int supid) {
		
		 supervisorService.Approval(id,status);
		 System.out.println("SUpeID"+supid);
		 return supervisorService.getEmpList(supid);
	}

	/*@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}*/

	/*@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}*/

	/*@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}*/

}