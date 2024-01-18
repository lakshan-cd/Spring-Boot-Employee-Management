package com.simplecrud.employeeMS.controller;

import com.simplecrud.employeeMS.dto.EmployeeDTO;
import com.simplecrud.employeeMS.dto.ResponseDTO;
import com.simplecrud.employeeMS.entity.Employee;
import com.simplecrud.employeeMS.service.EmployeeService;
import com.simplecrud.employeeMS.utill.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/saveEmployees")
    public ResponseEntity saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        try{
            String res = employeeService.saveEmployee(employeeDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO , HttpStatus.ACCEPTED);

            }else if(res.equals("06")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Employee Already Registered");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error Occured");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/updateEmployee")
    public ResponseEntity updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        try{
            String res = employeeService.updateEmployee(employeeDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO , HttpStatus.ACCEPTED);

            }else if(res.equals("01")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Employee is not found");
                responseDTO.setContent(employeeDTO);
                return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error Occured");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);

            }
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllEmployees")
    public ResponseEntity getAllEmployees(){
        try {
            List<EmployeeDTO> employeeList = employeeService.getAllEmployee();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(employeeList);
            return new ResponseEntity(responseDTO , HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchById/{empId}")
    public ResponseEntity serachById(@PathVariable int empId){
        try{
           EmployeeDTO employeeDTO =  employeeService.searchEmployeeById(empId);
           if (employeeDTO != null){
               responseDTO.setCode(VarList.RSP_SUCCESS);
               responseDTO.setContent(employeeDTO);
               responseDTO.setMessage("Success");
               return new ResponseEntity(responseDTO , HttpStatus.ACCEPTED);
           }else {
               responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
               responseDTO.setMessage("No employee available in this id");
               responseDTO.setContent(null);
               return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);
           }
        }catch (Exception e){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setContent(e);
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteById/{empId}")
    public ResponseEntity deleteById(@PathVariable int empId){
        try {
            String res = employeeService.deleteEmployeeById(empId);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No employee found on this id");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO , HttpStatus.BAD_REQUEST);

            }
        } catch (Exception e){
                responseDTO.setCode(VarList.RSP_ERROR);
                responseDTO.setMessage(e.getMessage());
                responseDTO.setContent(e);
                return new ResponseEntity(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    //pagination
//    @RequestMapping(value = "/pagination/{pageNumber}/{pageSize}" , method = RequestMethod.GET)
//    public Page<Employee> getEmployeeData(@PathVariable Integer pageNumber , @PathVariable Integer pageSize){
//        return employeeService.getEmployeesAsPage(pageNumber,pageSize);
//    }


        @GetMapping("/sortingByField/{fieldName}")
        public List<Employee> searchByFieldName(@PathVariable String fieldName){
        List<Employee> sortedEmployees = employeeService.searchByField(fieldName);
        return new ArrayList<>(sortedEmployees);
        }

        @GetMapping("/getDataByPaginating/{offset}/{pageSize}")
        public Page<Employee> getAllEmployeeByPaginating(@PathVariable int offset , @PathVariable int pageSize){
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("success");
//            responseDTO.setContent(null);
            return employeeService.findDataByPaginate(offset,pageSize);

        }
}
