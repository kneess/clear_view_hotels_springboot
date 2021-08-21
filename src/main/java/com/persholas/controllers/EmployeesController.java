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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/clearview")
public class EmployeesController {

    EmployeeService employeeService;
    @Autowired
    public EmployeesController(EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }

    @GetMapping("/allemployees")
    public String employees(Model model)
    {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees",employees);
        return "employees";
    }

    @GetMapping("/allemployees/form")
    public String employeeForm(Model model)
    {
        model.addAttribute("employee",new Employee());
        return "newEmployeeForm";
    }

    @PostMapping("/allemployees")
    @ResponseBody
    public Employee addEmployee(@ModelAttribute("employee") Employee employee)
    {
        return employee;
    }
}
