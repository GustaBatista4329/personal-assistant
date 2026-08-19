package com.gustavo.personalassistant.model.user;

import com.gustavo.personalassistant.dto.userDto.UserRegistrationDto;
import com.gustavo.personalassistant.dto.userDto.UserUpdateDto;
import com.gustavo.personalassistant.model.transactions.expense.Expense;
import com.gustavo.personalassistant.model.transactions.income.Income;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    private LocalDate birthdate;

    @Column(length = 11)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    public User(UserRegistrationDto dto){
        this.name = dto.name();
        this.birthdate = dto.birthdate();
        this.phoneNumber = dto.phonenumber();
        this.email = dto.email();
        this.password = dto.password();
    }

    public void userUpdate(UserUpdateDto dto){
        if(dto.name() != null){
            this.name = dto.name();
        }
        if(dto.birthdate() != null){
            this.birthdate = dto.birthdate();
        }
        if(dto.birthdate() != null){
            this.phoneNumber = dto.phoneNumber();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public void setRole(UserRoles role) {
        this.role = role;
    }


    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }


    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
