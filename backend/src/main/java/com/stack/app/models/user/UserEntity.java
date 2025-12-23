package com.stack.app.models.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stack.app.generalDTO.RolesDTO;
import com.stack.app.models.notes.NotesEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 18, unique = true)
    private String nick;

    @Column(length = 25, unique = true)
    private String email;

    @Column(length = 257)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolesDTO roles = RolesDTO.user;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<NotesEntity> notes = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "fav_notes",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "note_id")
    private List<Long> favoriteNoteIds = new ArrayList<>();

    public UserEntity(String nick, String email, String password) {
        this.nick = nick;
        this.email = email;
        this.password = password;
    }


}



