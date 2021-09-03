package com.persholas;


import com.persholas.dao.*;
import com.persholas.models.*;
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
    IUserRepo userRepo;
    IEmployeeProfileRepo employeeProfileRepo;
    ICustomerProfileRepo customerProfileRepo;
    IHotelRepo hotelRepo;
    IRoomRepo roomRepo;
    IAuthGroupRepo authGroupRepo;

    @Autowired
    public AppRunner(IUserRepo userRepo, IEmployeeProfileRepo employeeRepo, IHotelRepo hotelRepo, ICustomerProfileRepo customerRepo, IRoomRepo roomRepo
    , IAuthGroupRepo authGroupRepo)
    {
        this.userRepo = userRepo;
        this.employeeProfileRepo = employeeRepo;
        this.hotelRepo = hotelRepo;
        this.customerProfileRepo = customerRepo;
        this.roomRepo = roomRepo;
        this.authGroupRepo = authGroupRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepo.save(new User("anibal@clearview.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Anibal","Lecter"));
        userRepo.save(new User("jenny@clearview.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Jenny","Johnson"));
        userRepo.save(new User("michal@clearview.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Michal","Murray"));
        userRepo.save(new User("nemo@clearview.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Nemo","Nancy"));
        userRepo.save(new User("dory@clearview.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Dory","Dan"));
        authGroupRepo.save(new AuthGroup("anibal@clearview.com","ROLE_ADMIN"));
        authGroupRepo.save(new AuthGroup("jenny@clearview.com","ROLE_EMPLOYEE"));
        authGroupRepo.save(new AuthGroup("michal@clearview.com","ROLE_EMPLOYEE"));
        authGroupRepo.save(new AuthGroup("nemo@clearview.com","ROLE_EMPLOYEE"));
        authGroupRepo.save(new AuthGroup("dory@clearview.com","ROLE_EMPLOYEE"));
        employeeProfileRepo.save(new EmployeeProfile(userRepo.getById(1L), "999 w grand ave","PHX","AZ","85001","459-999-9999","Landlord",1000000.00, true));
        employeeProfileRepo.save(new EmployeeProfile(userRepo.getById(2L),"999 w grand ave","PHX","AZ","85001","459-999-9999","Manager",55000.00,true));
        employeeProfileRepo.save(new EmployeeProfile(userRepo.getById(3L),"999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeProfileRepo.save(new EmployeeProfile(userRepo.getById(4L),"999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeProfileRepo.save(new EmployeeProfile(userRepo.getById(5L),"999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));

        EmployeeProfile landlord = employeeProfileRepo.getById(1l);
        EmployeeProfile manager = employeeProfileRepo.getById(2l);
        EmployeeProfile maintenance_one = employeeProfileRepo.getById(3l);
        EmployeeProfile maintenance_two = employeeProfileRepo.getById(4l);
        EmployeeProfile maintenance_three = employeeProfileRepo.getById(5l);
        //set employee manager
        manager.setEmployeeManager(landlord);
        landlord.setEmployeeManager(landlord);
        maintenance_one.setEmployeeManager(manager);
        maintenance_two.setEmployeeManager(manager);
        maintenance_three.setEmployeeManager(manager);

        List<EmployeeProfile> hEmps = new ArrayList<>();
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
        userRepo.save(new User("jmy@mail.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Jeremy","John"));
        userRepo.save(new User("rachel@mail.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Rachel","Randy"));
        userRepo.save(new User("arnold@mail.com","$2y$11$JcGGFLmi46mM4SKUGKSv8.2srUTueRcyxRoHfHSVK/voHjw9kEKv.","Arnold","Ashley"));
        authGroupRepo.save(new AuthGroup("jmy@mail.com","ROLE_CUSTOMER"));
        authGroupRepo.save(new AuthGroup("rachel@mail.com","ROLE_CUSTOMER"));
        authGroupRepo.save(new AuthGroup("arnold@mail.com","ROLE_CUSTOMER"));
        customerProfileRepo.save(new CustomerProfile(userRepo.getById(6L), "808-000-0000",true));
        customerProfileRepo.save(new CustomerProfile(userRepo.getById(7L),"808-000-0000",true));
        customerProfileRepo.save(new CustomerProfile(userRepo.getById(8L),"808-000-0000",true));

        //get customer
        List<CustomerProfile> customers = new ArrayList<>();
        CustomerProfile customer1 = customerProfileRepo.getById(1l);
        CustomerProfile customer2 = customerProfileRepo.getById(2l);
        CustomerProfile customer3 = customerProfileRepo.getById(3l);
        customer1.setHotel(nHotel);
        customer2.setHotel(nHotel);
        customer3.setHotel(nHotel);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        //create room

        for(int i=1; i<=50; i++) {
            roomRepo.save(new Room(i+"N",5,2,1800.00,"808-000-0000",true));
        }
        for(int i=51; i<=100; i++) {
            roomRepo.save(new Room(i+"N", 2, 1, 1300.00, "808-000-0000",true));
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
            Room room = roomRepo.getById(i);
            room.setHotel(nHotel);
            rooms.add(room);
        }
        nHotel.setRooms(rooms);
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