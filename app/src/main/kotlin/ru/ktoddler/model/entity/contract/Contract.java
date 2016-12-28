package ru.ktoddler.model.entity.contract;

import android.provider.BaseColumns;

public interface Contract {

    String ID = BaseColumns._ID;

    Class[] ENTITIES = {
            ru.ktoddler.model.entity.KToddlerEntity.class
    };

    interface KToddlerEntity {
        String NAME = "name";
        String AGE = "age";
    }
}
