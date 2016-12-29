package ru.ktoddler.view

import ru.ktoddler.view.model.KToddlerVM

interface KToddlerView : View {

    fun showToddler(toddlerVM: KToddlerVM)

}
