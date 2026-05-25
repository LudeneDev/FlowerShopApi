package com.ludenedev.flowershop.prod.service;

public interface MySqlAdapterMapper<T, Y> {

    T atob(Y source);


    Y btoa(T source);
}
