package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.IHotelRepo;
import com.persholas.models.AuthGroup;
import com.persholas.models.Customer;
import com.persholas.models.Employee;
import com.persholas.models.Hotel;
import com.persholas.services.EmployeeService;
import com.persholas.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("clearview")
public class EmployeesController {

    EmployeeService employeeService;
    HotelService hotelService;
    IAuthGroupRepo userRepo;
    @Autowired
    public EmployeesController(EmployeeService employeeService,HotelService hotelService,IAuthGroupRepo userRepo)
    {
        this.employeeService = employeeService;
        this.hotelService = hotelService;
        this.userRepo = userRepo;
    }

    @GetMapping("/employees")
    public String employees(Model model)
    {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees",employees);
        return "employees";
    }

    @GetMapping("/hotels/{hotelId}/employees/form")
    public String employeeForm(@PathVariable("hotelId") Long hotelId, Model model)
    {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Employee> employees = hotel.getEmployees();
        List<Employee> managers = new ArrayList<>();
        for(Employee e : employees)
        {
            if(e.getTitle().compareTo("Manager") == 0){
                managers.add(employeeService.getEmployeeById(e.getId()));
            }
        }

        //employee set active false for radio button checked
        Employee newEmployee = new Employee();
        newEmployee.setActive(false);
        model.addAttribute("hotel",hotel);
        model.addAttribute("employee",newEmployee);
        model.addAttribute("managers",managers);
        return "newEmployeeForm";
    }

    @PostMapping("/hotels/{hotelId}/employees")
    public String addEmployee(@PathVariable("hotelId") Long hotelId
            ,@ModelAttribute("employee") @Valid Employee employee,
            BindingResult bindingResult, Model model, @RequestParam("managerId") Long managerId)
    {
        if(bindingResult.hasErrors())
        {
            log.warn(bindingResult.getAllErrors().toString());
            Hotel hotel = hotelService.getHotelById(hotelId);
            List<Employee> employees = hotel.getEmployees();
            //get hotel managers to display in dropdown
            List<Employee> managers = new ArrayList<>();
            for(Employee e : employees)
            {
                if(e.getTitle().compareTo("Manager") == 0){
                    managers.add(e);
                }
            }
            model.addAttribute("hotel",hotel);
            model.addAttribute("managers",managers);
            return "newEmployeeForm";
        }
        //check if username taken
        //conflict because of Employee and Customer entity use with security auth
        if(userRepo.findByaUsername(employee.getEUsername()).isEmpty()) {
            //assigning employee a manager and a hotel
            Employee manager = employeeService.getEmployeeById(managerId);
            log.warn(manager.getId().toString());
            employee.setEmployeeManager(manager);
            Employee employee1 = employeeService.addNewEmployee(employee);
            //setting employee role
            userRepo.save(new AuthGroup(employee.getEUsername(),"ROLE_EMPLOYEE"));
            hotelService.addEmployeeToHotel(hotelId, employee1);
            return "redirect:/clearview/hotels/" + hotelId + "/employees";
        }

        Hotel hotel = hotelService.getHotelById(hotelId);
        List<Employee> employees = hotel.getEmployees();
        //get hotel managers to display in dropdown
        List<Employee> managers = new ArrayList<>();
        for(Employee e : employees)
        {
            if(e.getTitle().compareTo("Manager") == 0){
                managers.add(e);
            }
        }
        String usernameExists = "Username already taken please enter in another";
        model.addAttribute("usernameExists",usernameExists);
        model.addAttribute("hotel",hotel);
        model.addAttribute("managers",managers);
        return "newEmployeeForm";
    }

}
