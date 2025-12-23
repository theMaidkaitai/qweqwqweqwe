package com.stack.app.repository.notes;


import com.stack.app.models.notes.NoteTranscationHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotesTransactionRepository extends CrudRepository<NoteTranscationHistoryEntity, Long> {
    NoteTranscationHistoryEntity findByNoteId(Long noteId);

    int countById(Long id);

    List<NoteTranscationHistoryEntity> findByNoteIdAndOperation(Long noteId, Boolean operation);

    @Query("SELECT SUM(t.transtication) FROM NoteTranscationHistoryEntity t " +
            "WHERE t.note.id = :noteId AND t.operation = false AND DATE(t.created_at) = :date")
    Integer findDailyExpenseTotal(@Param("noteId") Long noteId, @Param("date") LocalDate date);


}
