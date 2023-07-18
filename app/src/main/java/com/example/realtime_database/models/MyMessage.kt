package com.example.realtime_database.models

import java.text.SimpleDateFormat
import java.util.Date

class MyMessage {

    var id:String? = null
    var text:String? = null

    constructor(id: String?, text: String?  ) {
        this.id = id
        this.text = text
    }

    constructor()

    var date = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date())

}