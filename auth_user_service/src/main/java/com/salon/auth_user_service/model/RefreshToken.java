package com.salon.auth_user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "token_hash" , nullable = false, unique = true , length = 64)
    private String tokenHash;

    @Column(name = "expire_at" , nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked" , nullable = false)
    private Boolean revoked;

    @Column(name = "created_at" , nullable = false)
    private LocalDateTime createdAt;

}
