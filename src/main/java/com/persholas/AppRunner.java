package com.persholas;


import com.persholas.dao.ICustomerRepo;
import com.persholas.dao.IEmployeeRepo;
import com.persholas.dao.IHotelRepo;
import com.persholas.dao.IRoomRepo;
import com.persholas.models.Customer;
import com.persholas.models.Employee;
import com.persholas.models.Hotel;
import com.persholas.models.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {
    IEmployeeRepo employeeRepo;
    ICustomerRepo customerRepo;
    IHotelRepo hotelRepo;
    IRoomRepo roomRepo;

    @Autowired
    public AppRunner(IEmployeeRepo employeeRepo, IHotelRepo hotelRepo, ICustomerRepo customerRepo, IRoomRepo roomRepo)
    {
        this.employeeRepo = employeeRepo;
        this.hotelRepo = hotelRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepo.save(new Employee("Toby","Landlord",1000000.00, true));
        employeeRepo.save(new Employee("Cujo","Manager",55000.00,true));
        employeeRepo.save(new Employee("Wishbone","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Nemo","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Dory","Maintenance",32000.00,true));
        Employee landlord = employeeRepo.getById(1l);
        Employee manager = employeeRepo.getById(2l);
        Employee maintenance_one = employeeRepo.getById(3l);
        Employee maintenance_two = employeeRepo.getById(4l);
        Employee maintenance_three = employeeRepo.getById(5l);
        //set employee manager
        manager.setEmp_manager(landlord);
        landlord.setEmp_manager(landlord);
        maintenance_one.setEmp_manager(manager);
        maintenance_two.setEmp_manager(manager);
        maintenance_three.setEmp_manager(manager);
        //create customer
        customerRepo.save(new Customer("Kitten","kitty@mail.com",true));
        //get customer
        Customer customer = customerRepo.getById(1l);
        //create room
        roomRepo.save(new Room("1",4,1600.00,true));
        //get room
        Room room = roomRepo.getById(1l);

        //todo - hotel
        // add list of rooms to hotel
        // add hotel expenses

        // todo - room
        // add customer
        // add hotel
        // add room expenses

        // todo - expense type
        // create expense types
        // add expense types to hotel and room expenses





        List<Employee> hEmps = new ArrayList<>();
        hEmps.add(landlord);
        hEmps.add(manager);
        hEmps.add(maintenance_one);
        hEmps.add(maintenance_two);
        hEmps.add(maintenance_three);

        hotelRepo.save(new Hotel("north","9999 ave","AZ","98879"));
        Hotel nHotel = hotelRepo.getById(1l);
        nHotel.setManager(manager);
        nHotel.setEmployees(hEmps);
        hotelRepo.save(nHotel);


    }
}