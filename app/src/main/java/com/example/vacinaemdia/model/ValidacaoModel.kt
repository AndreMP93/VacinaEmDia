package com.example.vacinaemdia.model

class ValidacaoModel(val messagem: String = "") {
    private var status: Boolean = true
    private var errorMessage: String = ""

    init {
        if (messagem != "") {
            errorMessage = messagem
            status = false
        }
    }

    fun status() = status
    fun errorMessage() = errorMessage
}