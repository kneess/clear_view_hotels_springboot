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
        employeeRepo.save(new Employee("Anibal","anibal@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Landlord",1000000.00, true));
        employeeRepo.save(new Employee("Jenny","jenny@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Manager",55000.00,true));
        employeeRepo.save(new Employee("Michal","michal@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Nemo","nemo@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
        employeeRepo.save(new Employee("Dory","dory@mail.com","999 w grand ave","PHX","AZ","85001","459-999-9999","Maintenance",32000.00,true));
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
        nHotel.setManager(manager);
        nHotel.setEmployees(hEmps);
        //create customer
        customerRepo.save(new Customer("Rachel","rachel@mail.com","808-000-0000",true));
        //get customer
        Customer customer = customerRepo.getById(1l);
        //create room
        roomRepo.save(new Room("1",5,2,1800.00,true));
        roomRepo.save(new Room("2",2,1,1300.00,true));
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
        hotelExpenseRepo.save(new HotelExpense(new Date(),electrical,nHotel,800.00));
        //get hotel expense
        HotelExpense electricalExpense = hotelExpenseRepo.getById(1l);
        // add hotel expenses
        List<HotelExpense> hotelExpenses = new ArrayList<>();
        hotelExpenses.add(electricalExpense);
        nHotel.setHotelExpenses(hotelExpenses);
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