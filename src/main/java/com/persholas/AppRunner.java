package com.persholas;


import com.persholas.dao.*;
import com.persholas.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {
    IEmployeeRepo employeeRepo;
    ICustomerRepo customerRepo;
    IHotelRepo hotelRepo;
    IRoomRepo roomRepo;
    IHotelAccountRepo hotelAccountRepo;

    @Autowired
    public AppRunner(IEmployeeRepo employeeRepo, IHotelRepo hotelRepo, ICustomerRepo customerRepo, IRoomRepo roomRepo
    , IHotelAccountRepo hotelAccountRepo)
    {
        this.employeeRepo = employeeRepo;
        this.hotelRepo = hotelRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
        this.hotelAccountRepo = hotelAccountRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepo.save(new Employee("Anibal","Lecter","anibal@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Landlord",1000000.00, true));
        employeeRepo.save(new Employee("Jenny","Johnson","jenny@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Manager",55000.00,true));
        employeeRepo.save(new Employee("Michal","Murray","michal@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Nemo","Nancy","nemo@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Dory","Dan","dory@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        Employee landlord = employeeRepo.getById(1l);
        Employee manager = employeeRepo.getById(2l);
        Employee maintenance_one = employeeRepo.getById(3l);
        Employee maintenance_two = employeeRepo.getById(4l);
        Employee maintenance_three = employeeRepo.getById(5l);
        //set employee manager
        manager.setEmployeeManager(landlord);
        landlord.setEmployeeManager(landlord);
        maintenance_one.setEmployeeManager(manager);
        maintenance_two.setEmployeeManager(manager);
        maintenance_three.setEmployeeManager(manager);

        List<Employee> hEmps = new ArrayList<>();
        hEmps.add(landlord);
        hEmps.add(manager);
        hEmps.add(maintenance_one);
        hEmps.add(maintenance_two);
        hEmps.add(maintenance_three);
        //hotel creation
        hotelRepo.save(new Hotel("Clear View North","9999 e Thomas ave","PHX","AZ","85030","908-888-8888"));
        Hotel nHotel = hotelRepo.getById(1l);
        nHotel.setEmployees(hEmps);
        //create customer
        customerRepo.save(new Customer("Jeremy","John","jmy@mail.com","808-000-0000",true));
        customerRepo.save(new Customer("Rachel","Randy","rachel@mail.com","808-000-0000",true));
        customerRepo.save(new Customer("Arnold","Ashley","arnold@mail.com","808-000-0000",true));
        //get customer
        List<Customer> customers = new ArrayList<>();
        Customer customer1 = customerRepo.getById(1l);
        Customer customer2 = customerRepo.getById(2l);
        Customer customer3 = customerRepo.getById(3l);
        customer1.setHotel(nHotel);
        customer2.setHotel(nHotel);
        customer3.setHotel(nHotel);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        //create room

        for(int i=1; i<=50; i++) {
            roomRepo.save(new Room(i+"N",5,2,1800.00,true));
        }
        for(int i=51; i<=100; i++) {
            roomRepo.save(new Room(i+"N", 2, 1, 1300.00, true));
        }
        //get room
        //set customer and hotel
        Room room1 = roomRepo.getById(1l);
        Room room2 = roomRepo.getById(2l);
        customer1.setRoom(room1);

        //todo - hotel
        // add list of rooms to hotel
        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        for(Long i=3l; i<=100l; i++)
        {
            rooms.add(roomRepo.getById(i));
        }
        nHotel.setRooms(rooms);
        //create hotel account
        hotelAccountRepo.save(new HotelAccount(new Date(),400000.00,200000.00));
        //get hotel account
        HotelAccount account = hotelAccountRepo.getById(1l);
        // add hotel account
        nHotel.setHotelAccount(account);
        // todo - room
        // add customer
        //add hotel
        room1.setHotel(nHotel);
        room1.setCustomer(customer1);
        room1.setVacancy(false);
        room2.setHotel(nHotel);
        room2.setCustomer(customer2);
        room2.setVacancy(false);
        nHotel.setCustomers(customers);
        hotelRepo.save(nHotel);
    }
}