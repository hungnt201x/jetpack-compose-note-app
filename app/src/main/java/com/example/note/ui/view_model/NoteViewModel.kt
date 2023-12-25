package com.example.note.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.note.models.entities.NoteEntity
import com.example.note.repositories.NoteRepository

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableLiveData<List<NoteEntity>>()
    val notes: LiveData<List<NoteEntity>> get() = _notes

    suspend fun getAllNotes() {
        _notes.value = repository.getAllNotes()
    }

    suspend fun insertNote(note: NoteEntity) {
        repository.insertNote(note)
        getAllNotes()
    }

    suspend fun updateNote(note: NoteEntity) {
        repository.updateNoteById(note)
    }

    suspend fun searchNote(title: String) {
        repository.searchNote(title)
    }

     suspend fun deleteNote(id: Int) {
        repository.deleteNoteById(id)
        getAllNotes()
    }
}