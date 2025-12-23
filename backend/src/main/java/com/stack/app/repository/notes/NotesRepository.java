package com.stack.app.repository.notes;

import com.stack.app.models.notes.NoteTranscationHistoryEntity;
import com.stack.app.models.notes.NotesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesRepository extends CrudRepository<NotesEntity, Long> {


    List<NotesEntity> findNotesEntitiesByUserId(Long userId);
}
