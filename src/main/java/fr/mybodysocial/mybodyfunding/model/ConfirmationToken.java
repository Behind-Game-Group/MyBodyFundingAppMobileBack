package fr.mybodysocial.mybodyfunding.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(nullable = false)
    private String token;
    @NonNull
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @NonNull
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @NonNull
    @ManyToOne()
    @Cascade(CascadeType.DELETE)
    @JoinColumn(nullable = false, name = "app_user_id" )
    private AppUser appUser;
}
