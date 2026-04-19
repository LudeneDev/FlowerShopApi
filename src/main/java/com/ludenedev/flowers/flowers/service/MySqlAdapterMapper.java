package com.ludenedev.flowers.flowers.service;

public interface MySqlAdapterMapper<T, Y> {

    T atob(Y source);


    Y btoa(T source);
}
