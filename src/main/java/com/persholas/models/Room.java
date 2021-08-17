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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    String roomNumber;
    @NonNull
    Integer numOfRooms;
    @NonNull
    Integer numOfBathrooms;
    @NonNull
    Double ratePerMonth;
    @OneToOne
    Customer customer;
    @NonNull
    Boolean vacancy;
    @ManyToOne
    Hotel hotel;
    @OneToMany(mappedBy = "room")
    List<RoomExpense> roomExpenses;
}