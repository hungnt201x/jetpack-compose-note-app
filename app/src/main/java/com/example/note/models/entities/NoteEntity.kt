package com.example.note.models.entities

data class NoteEntity(
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var color: Int? = null
) {
    companion object {
        fun fromMap(json: Map<String, Any?>): NoteEntity {
            return NoteEntity(
                id = json["id"] as Int?,
                title = json["title"] as String?,
                description = json["description"] as String?,
                color = json["color"] as Int?
            )
        }
    }

    private fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "description" to description,
            "color" to color
        )
    }

    override fun toString(): String {
        return toMap().toString()
    }
}