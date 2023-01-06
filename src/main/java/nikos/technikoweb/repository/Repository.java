/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, K> {
    //CR(U)D
    T create(T t);
    T read(K id);
    List<T> read();
    boolean delete(K id);
}