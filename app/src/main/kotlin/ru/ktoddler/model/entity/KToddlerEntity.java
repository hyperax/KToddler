package ru.ktoddler.model.entity;

import android.support.annotation.NonNull;

import nl.qbusict.cupboard.annotation.Column;
import ru.ktoddler.model.entity.contract.Contract;

public class KToddlerEntity {

    @Column(Contract.ID)
    public long id;

    @NonNull
    @Column(Contract.KToddlerEntity.NAME)
    public String name = "";

    @Column(Contract.KToddlerEntity.AGE)
    public int age = 1;
}
