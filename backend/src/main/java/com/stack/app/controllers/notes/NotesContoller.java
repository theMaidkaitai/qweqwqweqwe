package com.stack.app.controllers.notes;


import com.stack.app.models.notes.DTO.NotesDTO;
import com.stack.app.models.notes.DTO.TransactionDTO;
import com.stack.app.models.notes.NotesEntity;
import com.stack.app.service.jwt.JwtService;
import com.stack.app.service.notes.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NotesContoller {
    
    @Autowired
    NoteService noteService;

    @Autowired
    JwtService jwtService;



    @PostMapping("/note/create")
    public NotesDTO createNote (@RequestHeader("Authorization") String header,
                                @RequestBody @Validated NotesDTO notesDTO) {
        try {
            String token = header.substring(7);
            Long userId = jwtService.getUserById(token);

            NotesDTO noteInstanse = noteService.createNote(notesDTO, userId);
            return new NotesDTO(notesDTO.title(), userId, notesDTO.currency(), notesDTO.budget(), notesDTO.limit_on_day(), notesDTO.totalSpends());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("note/transaction/inc/{id}")
    public ResponseEntity<?> incrementTransaction (
            @RequestBody @Validated TransactionDTO transactionDTO,
            @PathVariable Long id
    ) {
        try {
            TransactionDTO transaction = noteService.incrementTransaction(id, transactionDTO.num());
            return ResponseEntity.ok(transaction);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("note/transaction/dec/{id}")
    public ResponseEntity<?> decrementTransaction (
            @RequestBody @Validated TransactionDTO transactionDTO,
            @PathVariable Long id
    ) {
        try {
            TransactionDTO transaction = noteService.decrementTransaction(id, transactionDTO.num());
            return ResponseEntity.ok(transaction);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("note/transactions/spends/average/{id}")
    public Integer getAverageSpends (
            @PathVariable Long id
    ) {
        Integer average = noteService.getAverageSpends(id);
        return average;
    }

    @GetMapping("note/transactions/spends/total/day/{id}")
    public ResponseEntity<String> getTodaySpends(@PathVariable Long id) {
        int total = noteService.getTotalSpendToday(id);
        return ResponseEntity.ok().body("Сегодня потрачено: " + total);
    }

    @GetMapping("note/{id}")
    public ResponseEntity<Optional<NotesEntity>> getOneNote(@PathVariable Long id) {
        Optional<NotesEntity> note = noteService.getNote(id);
        return ResponseEntity.ok().body(note);
    }


    @GetMapping("note/all")
    public ResponseEntity<List<NotesEntity>> getAllNotes(
            @RequestHeader("Authorization") String header
    ) {

        try {
            String token = header.substring(7);
            Long userId = jwtService.getUserById(token);

            List<NotesEntity> note = noteService.getAllNotesUser(userId);
            return ResponseEntity.ok().body(note);
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
