package com.persholas.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customers")
public class CustomerProfile implements Serializable {
    static final long serialVersionUID = 6381462249347345007L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull @OneToOne
    User user;
    @NonNull @Pattern(regexp ="[0-9]{3}-[0-9]{3}-[0-9]{4}", message = "Please follow the format")
    String phoneNumber;
    @NonNull
    Boolean active;
    @ManyToOne
    Hotel hotel;
    @OneToOne
    Room room;
}