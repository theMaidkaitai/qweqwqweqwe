package com.stack.app.service.notes;


import com.stack.app.generalDTO.NoteResponse;
import com.stack.app.models.notes.DTO.NotesDTO;
import com.stack.app.models.notes.DTO.TransactionDTO;
import com.stack.app.models.notes.NoteTranscationHistoryEntity;
import com.stack.app.models.notes.NotesEntity;
import com.stack.app.models.user.UserEntity;
import com.stack.app.repository.notes.NotesRepository;
import com.stack.app.repository.notes.NotesTransactionRepository;
import com.stack.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotesTransactionRepository notesTransactionRepository;


    public NotesDTO createNote (NotesDTO notesDTO, Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));


        NotesEntity noteInstanse = new NotesEntity(notesDTO.title(), user, notesDTO.budget(), notesDTO.currency(),notesDTO.limit_on_day(), notesDTO.totalSpends());
        NotesEntity savedNote = notesRepository.save(noteInstanse);

        return new NotesDTO(
                noteInstanse.getTitle(),
                user.getId(),
                notesDTO.currency(),
                notesDTO.budget(),
                noteInstanse.getLimit_on_day(),
                noteInstanse.getTotalSpends()
        );
    }

    public TransactionDTO incrementTransaction (Long note_id, int num) {
       NotesEntity note = notesRepository.findById(note_id)
               .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + note_id));
       NoteTranscationHistoryEntity transaction = new NoteTranscationHistoryEntity(note, num);
       note.setBudget(note.getBudget() + num);
       transaction.setOperation(true);
       notesTransactionRepository.save(transaction);

        return new TransactionDTO(
               note.getId(),
               num
       );
    }

    public TransactionDTO decrementTransaction (Long note_id, int num) {
        NotesEntity note = notesRepository.findById(note_id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + note_id));

        NoteTranscationHistoryEntity transaction = new NoteTranscationHistoryEntity(note, num);
        note.setBudget(note.getBudget() - num);
        transaction.setOperation(false);

        if (!(num > note.getBudget())) {
            note.setTotalSpends(note.getTotalSpends() + num);
        }
        notesTransactionRepository.save(transaction);

        return new TransactionDTO(
                note.getId(),
                num
        );
    }

//    public int getTotalSpend (Long id) {
//
//        return 5;
//    }

    public int getTotalSpendToday (Long noteId) {
        NotesEntity note = notesRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + noteId));
        LocalDate today = LocalDate.now();

        Integer dailyTotal = notesTransactionRepository.findDailyExpenseTotal(noteId, today);


        note.setSpends_in_day(dailyTotal != null ? dailyTotal : 0);
        notesRepository.save(note);


        return dailyTotal != null ? dailyTotal : 0;
    }




    public int getAverageSpends(Long noteId) {
        NotesEntity note = notesRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + noteId));

        List<NoteTranscationHistoryEntity> transactions =  notesTransactionRepository
                .findByNoteIdAndOperation(noteId, false);

        if (transactions.isEmpty()) {
            note.setAverage_spends(0);
            notesRepository.save(note);
            return 0;
        }

        int total = 0;
        for (NoteTranscationHistoryEntity transaction : transactions) {
            total += transaction.getTranstication();
        }

        int average = total / transactions.size();
        note.setAverage_spends(average);
        notesRepository.save(note);

        return average;
    }

    public Optional<NotesEntity> getNote (Long id) {
        Optional<NotesEntity> note = Optional.ofNullable(notesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + id)));
        return note;
    }
//
//    public List <NotesEntity> getAll () {
//        return (List<NotesEntity>) notesRepository.findAll();
//    }

    public List <NotesEntity> getAllNotesUser (Long id) {
        return (List<NotesEntity>) notesRepository.findNotesEntitiesByUserId(id);
    }

}
