package com.example.filmagraphy.contracts

import com.example.filmagraphy.data.model.Film

interface Contracts {
    interface View: BaseView {
        fun allFilmsToShow(films: List<Film>)
        fun setScrollPosition(position: Int)
    }

    interface State: BaseState {
        fun getAllFilms(): List<Film>?
        fun getCurrentFilms(): List<Film>?
        fun getScrollPosition(): Int?
    }

    interface Model: BaseModel {
        fun getFilms(): List<Film>
    }

    interface Presenter: BaseStatefulPresenter<View, State> {
        fun onGenreClick(genre: String)
        fun onFilmClick(id: Int)
    }
}