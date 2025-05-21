package com.datix.coresystem_poc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Required by JPA
@AllArgsConstructor
@Builder
@ToString
public class RentedWallbox {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Wallbox wallbox;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endTime;

    @Transient
    private Boolean locked;

    @Transient
    private Boolean charging;
}
