package com.simplecrud.employeeMS.service;

import com.simplecrud.employeeMS.dto.EmployeeDTO;
import com.simplecrud.employeeMS.entity.Employee;
import com.simplecrud.employeeMS.repository.EmployeeRepo;
import com.simplecrud.employeeMS.utill.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private ModelMapper modelMapper;

    //to add the data automatically
//    @PostConstruct
//    public void initDB()
//    {
//        List<Employee> employees = IntStream.rangeClosed(1,200).mapToObj(i -> new Employee("Saman" + i ,"Malabe"+i,"123" + i)).collect(Collectors.toList());
//        employeeRepo.saveAll(employees);
//    }
    public String saveEmployee(EmployeeDTO employeeDTO){
        if (employeeRepo.existsById(employeeDTO.getEmpId())) {
            return VarList.RSP_DUPLICATED;
        }else{
            employeeRepo.save(modelMapper.map(employeeDTO , Employee.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String updateEmployee(EmployeeDTO employeeDTO){
      if (employeeRepo.existsById(employeeDTO.getEmpId())){
          employeeRepo.save(modelMapper.map(employeeDTO , Employee.class));
          return VarList.RSP_SUCCESS;
      }else {
          return VarList.RSP_NO_DATA_FOUND;

      }
    }

    public List<EmployeeDTO> getAllEmployee(){
        List<Employee> employeeList = employeeRepo.findAll();
        return modelMapper.map(employeeList ,new TypeToken<ArrayList<EmployeeDTO>>(){
        }.getType());
    }

    public EmployeeDTO searchEmployeeById (int empId){
        if (employeeRepo.existsById(empId)){
           Employee employee =  employeeRepo.findById(empId).orElse(null);
            return modelMapper.map(employee,EmployeeDTO.class);
        }else {
            return null;
        }
    }

    public String deleteEmployeeById(int empId){
        if (employeeRepo.existsById(empId)) {
            employeeRepo.deleteById(empId);
            return VarList.RSP_SUCCESS;
        }else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


//    public Page getEmployeesAsPage(Integer pageNumber, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber,pageSize);
//        return employeeRepo.findAll(pageable);
//    }

    //sorting by field
    public List<Employee> searchByField(String field){
        return employeeRepo.findAll(Sort.by(Sort.Direction.ASC,field));
    }

    //pagination
    public Page<Employee> findDataByPaginate(int offset , int pageSize){
        Page<Employee> employees = employeeRepo.findAll(PageRequest.of(offset , pageSize));
        return employees;
    }
}

