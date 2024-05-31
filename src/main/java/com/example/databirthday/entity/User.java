package com.example.databirthday.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data//создать в этом классе геттеры сеттеры ту стринг еквалс и хеш код
@Entity//спринг ищет таблицу в этой сущности
@Table(name = "users")//настройка таблицы (имя например)
@NoArgsConstructor//пустой конструктор для спринга
@AllArgsConstructor//полный конструктор
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//стратегия генерации id (+1)
    private long id;

    @Column(name = "name_user")
    private String name;

    private LocalDate birthday;


}





