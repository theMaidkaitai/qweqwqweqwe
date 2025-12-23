package com.stack.app.models.notes;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Data
public class NoteTranscationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private NotesEntity note;

    @Column
    private int transtication;

    @Column
    private Boolean operation; // true - inc | dec - false

    @CreationTimestamp
    private LocalDateTime created_at;



    public NoteTranscationHistoryEntity(NotesEntity note, int transtication) {
        this.note = note;
        this.transtication = transtication;
        this.operation = true;
    }

}
