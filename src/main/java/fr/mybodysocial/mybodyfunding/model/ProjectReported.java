package fr.mybodysocial.mybodyfunding.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public class ProjectReported {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private AppUser user;
    @ManyToOne
    private Project project;
    @NonNull
    private Date creationDate;
    @NonNull
    private boolean isProcessed;
    @NonNull
    private String message;



}