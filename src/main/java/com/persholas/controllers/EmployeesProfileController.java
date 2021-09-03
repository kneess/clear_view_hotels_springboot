package com.persholas.controllers;

import com.persholas.dao.IAuthGroupRepo;
import com.persholas.dao.IUserRepo;
import com.persholas.models.AuthGroup;
import com.persholas.models.EmployeeProfile;
import com.persholas.models.Hotel;
import com.persholas.models.User;
import com.persholas.services.EmployeeProfileService;
import com.persholas.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class EmployeesProfileController {

    EmployeeProfileService employeeService;
    HotelService hotelService;
    IAuthGroupRepo authGroupRepo;
    IUserRepo userRepo;
    @Autowired
    public EmployeesProfileController(EmployeeProfileService employeeService, HotelService hotelService,
                                      IAuthGroupRepo authGroupRepo, IUserRepo userRepo)
    {
        this.employeeService = employeeService;
        this.hotelService = hotelService;
        this.authGroupRepo = authGroupRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/employees")
    public String employees(Model model)
    {
        List<EmployeeProfile> employees = employeeService.getAllEmployeeProfiles();
        model.addAttribute("employees",employees);
        return "employees";
    }

    @GetMapping("/hotels/{hotelId}/employees/form")
    public String employeeForm(@PathVariable("hotelId") Long hotelId, Model model)
    {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<EmployeeProfile> employees = hotel.getEmployees();
        List<EmployeeProfile> managers = new ArrayList<>();
        for(EmployeeProfile e : employees)
        {
            if(e.getTitle().toLowerCase().compareTo("manager") == 0 || e.getTitle().toLowerCase().compareTo("landlord") == 0){
                managers.add(employeeService.getEmployeeById(e.getId()));
            }
        }

        //employee set active false for radio button checked
        EmployeeProfile newEmployeeProfile = new EmployeeProfile();
        User newUser = new User();
        newEmployeeProfile.setActive(false);
        model.addAttribute("hotel",hotel);
        model.addAttribute("user", newUser);
        model.addAttribute("employee",newEmployeeProfile);
        model.addAttribute("managers",managers);
        return "newEmployeeForm";
    }

    @PostMapping("/hotels/{hotelId}/employees")
    public String addEmployee(@PathVariable("hotelId") Long hotelId
            ,@ModelAttribute("user")@Valid User user, BindingResult bindingResultUser
            ,@ModelAttribute("employee") @Valid EmployeeProfile employeeProfile,BindingResult bindingResultEmployeeProfile,
             Model model, @RequestParam("managerId") Long managerId)
    {
        //set up email to company email
        String companyEmail = user.getFirstName().toLowerCase() + "."+user.getLastName().toLowerCase()+"@clearview.com";
        user.setEmail(companyEmail);
        //pass as attributes
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<EmployeeProfile> employees = hotel.getEmployees();

        if(bindingResultEmployeeProfile.hasErrors() || bindingResultUser.hasErrors())
        {
            log.warn(bindingResultEmployeeProfile.getAllErrors().toString());
            //get hotel managers to display in dropdown
            List<EmployeeProfile> managers = new ArrayList<>();
            for(EmployeeProfile e : employees)
            {
                if(e.getTitle().toLowerCase().compareTo("manager") == 0 || e.getTitle().toLowerCase().compareTo("landlord") == 0){
                    managers.add(e);
                }
            }
            model.addAttribute("hotel",hotel);
            model.addAttribute("managers",managers);
            return "newEmployeeForm";
        }
        //check if email taken
        if(authGroupRepo.findByaUsername(user.getEmail()).isEmpty()) {
            //encryting employeeProfile password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getUPassword());
            user.setUPassword(encodedPassword);
            User newUser = userRepo.save(user);
            authGroupRepo.save(new AuthGroup(newUser.getEmail(),"ROLE_EMPLOYEE"));
            //assigning employeeProfile a manager and a hotel
            EmployeeProfile manager = employeeService.getEmployeeById(managerId);
            log.warn(manager.getId().toString());
            employeeProfile.setEmployeeManager(manager);
            employeeProfile.setUser(newUser);
            EmployeeProfile employee1 = employeeService.addNewEmployeeProfile(employeeProfile);
            hotelService.addEmployeeToHotel(hotelId, employee1);
            return "redirect:/clearview/hotels/" + hotelId + "/employees";
        }
        //get hotel managers to display in dropdown
        List<EmployeeProfile> managers = new ArrayList<>();
        for(EmployeeProfile e : employees)
        {
            if(e.getTitle().compareTo("Manager") == 0){
                managers.add(e);
            }
        }
        String usernameExists = "email already exists in our database";
        model.addAttribute("usernameExists",usernameExists);
        model.addAttribute("hotel",hotel);
        model.addAttribute("managers",managers);
        return "newEmployeeForm";
    }

}
