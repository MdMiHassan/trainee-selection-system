package com.example.tss.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Evaluator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Circular circular;
    @OneToOne
    private ScreeningRound assignedRound;
    @ElementCollection
    private List<Application> applications;
    private String gender;
    private String division;
    private String designation;
    private String empId;
    private String phone;
    private String firstName;
    private String lastName;
}
