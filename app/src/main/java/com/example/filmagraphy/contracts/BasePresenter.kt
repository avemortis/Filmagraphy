package com.example.filmagraphy.contracts

interface BasePresenter <in V: BaseView> {
    fun subscribe(view: V)

    fun unsubscribe()
}