package com.example.note.repositories

import android.content.Context
import com.example.note.database.NoteDAO
import com.example.note.models.entities.NoteEntity

abstract class NoteRepository(context: Context) {
    private val noteDAO = NoteDAO(context)

    suspend fun getAllNotes(): List<NoteEntity> =
        noteDAO.getAllNotes()

    suspend fun getNoteById(id: Int): NoteEntity =
        noteDAO.getNote(id)

    suspend fun insertNote(note: NoteEntity): Long =
        noteDAO.insertNote(note)

    suspend fun searchNote(title: String): List<NoteEntity> =
        noteDAO.searchNotes(title)

    suspend fun deleteNoteById(id: Int) =
        noteDAO.deleteNote(id)

    suspend fun updateNoteById(note: NoteEntity) =
        noteDAO.updateNote(note)

    suspend fun closeDB() =
        noteDAO.closeDatabase()
}
