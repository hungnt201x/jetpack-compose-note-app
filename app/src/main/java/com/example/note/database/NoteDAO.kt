package com.example.note.database

import com.example.note.models.entities.NoteEntity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteException
import android.content.ContentValues

// DAO: Database Access Object
class NoteDAO(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "notesDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTE = "notes"
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_COLOR = "color"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = (
                "CREATE TABLE $TABLE_NOTE ($KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$KEY_TITLE TEXT NOT NULL, $KEY_DESCRIPTION TEXT NOT NULL, $KEY_COLOR INTEGER NOT NULL)"
                )
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        onCreate(db)
    }

    private fun getDataBase(): SQLiteDatabase {
        return try {
            writableDatabase
        } catch (e: SQLiteException) {
            throw e
        }
    }

    fun closeDatabase() {
        val db = writableDatabase
        if (db.isOpen) {
            db.close()
        }
    }

    fun insertNote(noteModel: NoteEntity): Long {
        val db = getDataBase()
        val values = ContentValues()
        values.put(KEY_TITLE, noteModel.title)
        values.put(KEY_DESCRIPTION, noteModel.description)
        values.put(KEY_COLOR, noteModel.color)
        return db.insert(TABLE_NOTE, null, values)
    }

    fun getAllNotes(): List<NoteEntity> {
        val noteList = mutableListOf<NoteEntity>()
        val selectQuery = "SELECT * FROM $TABLE_NOTE"
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val note = NoteEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                    color = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COLOR))
                )
                noteList.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return noteList
    }

    fun getNote(noteId: Int): NoteEntity {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NOTE WHERE $KEY_ID = $noteId"
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        return if (cursor.moveToFirst()) {
            NoteEntity(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                color = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COLOR))
            )
        } else {
            NoteEntity()
        }
    }

    fun updateNote(noteEntity: NoteEntity): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, noteEntity.title)
        values.put(KEY_DESCRIPTION, noteEntity.description)
        return db.update(TABLE_NOTE, values, "$KEY_ID = ?", arrayOf(noteEntity.id.toString()))
    }

    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        db.delete(TABLE_NOTE, "$KEY_ID = ?", arrayOf(noteId.toString()))
    }

    fun searchNotes(query: String): List<NoteEntity> {
        val noteList = mutableListOf<NoteEntity>()
        val selectQuery =
            "SELECT * FROM $TABLE_NOTE WHERE $KEY_TITLE LIKE '%$query%' OR $KEY_DESCRIPTION LIKE '%$query%'"
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val note = NoteEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                    color = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_COLOR))
                )
                noteList.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return noteList
    }
}