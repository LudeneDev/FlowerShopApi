package com.ludenedev.flowershop.service;

public interface MySqlAdapterMapper<T, Y> {

    T atob(Y source);


    Y btoa(T source);
}
