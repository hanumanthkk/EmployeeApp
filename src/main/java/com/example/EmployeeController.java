package com.example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;



@Api(basePath = "swagger-demo/employee", value = "Employee", 
	description = "Operations with Employee", produces = "application/json")
@RestController
@RequestMapping(value = "swagger-demo/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
	@Autowired
	private EmployeeRepository repo;
	static final Logger logger = LogManager.getLogger(EmployeeController.class.getName());

	/**
	   * Create the employee in the database.
	   */
	// Create Department
	@Transactional
	@RequestMapping(method = RequestMethod.POST, value="/employee/create")
	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Create Employee", notes = "creating Employee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "List"),
            @ApiResponse(code = 500, message = "Error occurred while creating Employee")
    })	
	public String createEmployee(String name, String manager_name, String dept, double sal) throws Exception {
		Employee emp = new Employee(name, manager_name, dept, sal);
		repo.save(emp);
		throw new Exception("Exception");
	}
	
	/**
	   * Fetch the employees from the database.
	   */
	// Get Department Details
	@Transactional
	@RequestMapping(method = RequestMethod.GET, value="/employee/read")
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Get Employees", notes = "Fetch List of employees")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "List"),
            @ApiResponse(code = 500, message = "Error occurred while fetching employees")
    })
	public String getEmployee(long id) throws Exception {
		Employee emp;
		emp = repo.findOne(id);
		if (emp == null) {
			String errorMsg = "no department found for id " + id;
			throw new Exception(errorMsg);
		} else {
			return emp.getId() + " : " + emp.getName();
		}
	}
	
	/**
	   * Delete the employee from the database.
	   */
	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, value="/employee/remove")
	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Delete Employee", notes = "Delete Employee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "List"),
            @ApiResponse(code = 500, message = "Error occurred while deleting Employees")
    })	
	public void delete(Employee employee) {
	    if (repo.contains(employee))
	      repo.remove(employee);
	    else
	      repo.remove(repo.merge(employee));
	    return;
	  }
	
	 /**
	   * Update the passed employee in the database.
	   */
	@Transactional
	@RequestMapping(method = RequestMethod.PUT, value="/employee/update")
	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiOperation(value = "Update Employee", notes = "Update Employee")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Fields are with validation errors"),
            @ApiResponse(code = 201, message = "List"),
            @ApiResponse(code = 500, message = "Error occurred while Updating Employee")
    })	
	public void update(Employee employee) {
	    repo.merge(employee);
	    return;
	  }

}
