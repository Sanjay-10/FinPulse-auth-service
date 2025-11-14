package com.finpulse.auth_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(name = "ix_refresh_user", columnList = "user_id"),
                @Index(name = "ix_refresh_token", columnList = "token", unique = true)
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")   // instead of "id"
    private Long tokenId;

    // ---- RELATIONSHIP ----
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_user"))
    private User user;

    @Column(name = "token", nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }

}
