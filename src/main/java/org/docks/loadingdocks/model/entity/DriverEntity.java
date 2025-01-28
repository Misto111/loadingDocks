package org.docks.loadingdocks.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.docks.loadingdocks.enums.Role;


@Entity
@Table(name = "drivers")
public class DriverEntity extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // Поле за роля


    // Конструктор без параметри
    public DriverEntity() {
    }

    // Конструктор с параметри
    public DriverEntity(String firstName, String lastName, String password, String phoneNumber, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    // Гетъри и сетъри
    public String getFirstName() {
        return firstName;
    }

    public DriverEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DriverEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DriverEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DriverEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public DriverEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public DriverEntity setRole(Role role) {
        this.role = role;
        return this;
    }

}
