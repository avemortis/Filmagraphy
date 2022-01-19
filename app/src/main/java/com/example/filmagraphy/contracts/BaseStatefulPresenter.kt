package com.example.filmagraphy.contracts

interface BaseStatefulPresenter<in V: BaseView, S:BaseState>: BasePresenter<V> {
    fun subscribe(view: V, state: S?)
    fun getState(): S
}