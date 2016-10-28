package gasyidea.org.mobilereport.utils;

import android.database.Cursor;

import java.util.List;

public interface DBInterface<T> {

    void addEntity(T t);

    T createEntity(Cursor cursor);

    T getEntity(Object object);

    List<T> getAllEntity();

    int updateEntity(T t);

    void deleteEntity(Object object);
}
