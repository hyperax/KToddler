package ru.ktoddler.model.entity

import nl.qbusict.cupboard.annotation.Column
import ru.ktoddler.model.entity.contract.Contract

class KToddlerEntity {

    @Column(Contract.ID)
    var id: Long = 0

    @Column(Contract.KToddlerEntity.NAME)
    var name = ""

    @Column(Contract.KToddlerEntity.AGE)
    var age = 1

}
