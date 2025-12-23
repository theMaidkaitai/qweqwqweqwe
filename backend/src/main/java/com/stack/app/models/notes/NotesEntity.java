package com.stack.app.models.notes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stack.app.generalDTO.Currency;
import com.stack.app.models.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "notes")
@Entity
@Data
public class NotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @Column()
    private Integer budget;

    @Column()
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = true)
    private Integer limit_on_day;



    @Column(nullable = true)
    private Integer spends_in_day;

    @Column(nullable = true)
    private Integer average_spends;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NoteTranscationHistoryEntity> transactions = new ArrayList<>();

    @Column(nullable = true)
    private Integer totalSpends;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;


    @PrePersist // Аннотации со словом persist - те которые вызываются после создания/перед созданием в бд и т.п
    public void setDefaultValues() {
        this.totalSpends = 0;
        this.average_spends = 0;
        this.spends_in_day = 0;
    }


    public NotesEntity(String title,
                       UserEntity user,
                       Integer budget,
                       Currency currency,
                       Integer limit_on_month,
                       Integer totalSpends) {
        this.title = title;
        this.user = user;
        this.budget = budget;
        this.currency = currency;
        this.limit_on_day = limit_on_month;
        this.totalSpends = totalSpends;
        this.average_spends = 0;
        this.spends_in_day = 0;
    }

    public NotesEntity() {

    }
}
