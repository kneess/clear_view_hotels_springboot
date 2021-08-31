package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String name;
    @NonNull
    String address;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String zipCode;
    @NonNull
    String phoneNumber;
    @OneToMany
    List<EmployeeProfile> employees;
    @OneToMany
    List<CustomerProfile> customers;
    @OneToMany
    List<Room> rooms;
}