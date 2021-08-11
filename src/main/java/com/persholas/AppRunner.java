package com.persholas;


import com.persholas.dao.*;
import com.persholas.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    IHotelExpenseRepo hotelExpenseRepo;
    IExpenseTypeRepo expenseTypeRepo;
    IRoomExpenseRepo roomExpenseRepo;

    @Autowired
    public AppRunner(IEmployeeRepo employeeRepo, IHotelRepo hotelRepo, ICustomerRepo customerRepo, IRoomRepo roomRepo
    ,IHotelExpenseRepo hotelExpenseRepo, IExpenseTypeRepo expenseTypeRepo, IRoomExpenseRepo roomExpenseRepo)
    {
        this.employeeRepo = employeeRepo;
        this.hotelRepo = hotelRepo;
        this.customerRepo = customerRepo;
        this.roomRepo = roomRepo;
        this.hotelExpenseRepo = hotelExpenseRepo;
        this.expenseTypeRepo = expenseTypeRepo;
        this.roomExpenseRepo = roomExpenseRepo;
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

        List<Employee> hEmps = new ArrayList<>();
        hEmps.add(landlord);
        hEmps.add(manager);
        hEmps.add(maintenance_one);
        hEmps.add(maintenance_two);
        hEmps.add(maintenance_three);
        //hotel creation
        hotelRepo.save(new Hotel("north","9999 ave","AZ","98879"));
        Hotel nHotel = hotelRepo.getById(1l);
        nHotel.setManager(manager);
        nHotel.setEmployees(hEmps);
        //create customer
        customerRepo.save(new Customer("Kitten","kitty@mail.com",true));
        //get customer
        Customer customer = customerRepo.getById(1l);
        //create room
        roomRepo.save(new Room("1",4,1600.00,true));
        roomRepo.save(new Room("2",2,1200.00,true));
        //get room
        //set customer and hotel
        Room room1 = roomRepo.getById(1l);
        Room room2 = roomRepo.getById(2l);
        //todo - hotel
        // add list of rooms to hotel
        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        nHotel.setRooms(rooms);
        //create hotel expense and expense types
        expenseTypeRepo.save(new ExpenseType("electrical"));
        ExpenseType electrical = expenseTypeRepo.getById(1l);
        hotelExpenseRepo.save(new HotelExpense(new Date(),electrical,nHotel,2000.00,800.00));
        //get hotel expense
        HotelExpense electricalExpense = hotelExpenseRepo.getById(1l);
        // add hotel expenses
        List<HotelExpense> hotelExpenses = new ArrayList<>();
        hotelExpenses.add(electricalExpense);
        nHotel.setHotel_expenses(hotelExpenses);
        // todo - room
        // add customer
        //add hotel
        room1.setHotel(nHotel);
        room1.setCustomer(customer);
        room2.setHotel(nHotel);
        room2.setCustomer(customer);
        // create room expense
        roomExpenseRepo.save(new RoomExpense(new Date(),electrical,room1,400.00));
        roomExpenseRepo.save(new RoomExpense(new Date(),electrical,room2,200.00));



        hotelRepo.save(nHotel);


    }
}