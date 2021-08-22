package com.persholas.controllers;

import com.persholas.dao.IHotelRepo;
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
@RequestMapping("/clearview")
public class EmployeesController {

    EmployeeService employeeService;
    HotelService hotelService;
    @Autowired
    public EmployeesController(EmployeeService employeeService,HotelService hotelService)
    {
        this.employeeService = employeeService;
        this.hotelService = hotelService;
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
        // Todo: refactor this or move to service
        if(bindingResult.hasErrors())
        {
            log.warn(bindingResult.getAllErrors().toString());
            Hotel hotel = hotelService.getHotelById(hotelId);
            List<Employee> employees = hotel.getEmployees();
            List<Employee> managers = new ArrayList<>();
            for(Employee e : employees)
            {
                if(e.getTitle().compareTo("Manager") == 0){
                    managers.add(e);
                }
            }
            model.addAttribute("hotelId",hotelId);
            model.addAttribute("managers",managers);
            return "newEmployeeForm";
        }
        //assigning employee a manager and a hotel
        Employee manager = employeeService.getEmployeeById(managerId);
        log.warn(manager.getId().toString());
        employee.setEmployeeManager(manager);
        Employee employee1 = employeeService.addNewEmployee(employee);
        hotelService.addEmployeeToHotel(hotelId,employee1);
        return "redirect:/clearview/employees";
    }
}
