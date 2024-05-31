package com.example.databirthday.controller;


import com.example.databirthday.entity.User;
import com.example.databirthday.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private UserRepository userRepository;//обьект для работы с базой данных

    public UserController(UserRepository userRepository) {//конструктор юзер репрозитори
        this.userRepository = userRepository;
    }
    //когда пользователь делает запрос на /search то ему возвращаеться search_user.html
    @GetMapping("/search")
    public String result() {
        return "search_user";
    }


    //когда пользователь что то отправляет на /search то запускаеться данный метод
    @PostMapping("/search")

    //получаем имя и дату рождения которые ввел пользователь на странице html
    public String postResult(@RequestParam String name, Model model) {

        //получаем юзера по имени из базы данных
        User user = userRepository.findByName(name);

        //передаем дату рождения того юзера которого получили из базы данных и передаем в html страницу
        model.addAttribute("birthday", user.getBirthday().toString());
        //возвращаем данную страницу html
        return "search_user";
    }


    //когда пользователь делает запрос get по адресу /spisok то возвращаем html страницу spisok
    @GetMapping("/spisok")
    public String spisok(Model model) {
     List<User> usersList = userRepository.findAll();
     model.addAttribute("users", usersList);
        return "spisok";
    }



    //когда пользователь делает запрос get по адресу /add_user то возвращаем html страницу addUser и передаем
    //на эту страницу пустого обьекта юзера

    @GetMapping("/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/add_user")
    public String addUserPost( @ModelAttribute User user){
        userRepository.save(user);
        return "resultAdd";
    }

    @GetMapping("/info")
    public String info() {
        return "info";
    }
    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam long id_user) {
        userRepository.deleteById(id_user);
        return "redirect:/spisok";
    }

    @GetMapping("/time")
    public String time(Model model) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("дата: yyyy-MM-dd, время: HH:mm:ss");

        String dateTimeString = formatter.format(localDateTime);
        dateTimeString = "msk: " + dateTimeString;

        model.addAttribute("time", dateTimeString);
        return "time";
    }
    @GetMapping("/test_time")
    public String testTime(Model model) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        model.addAttribute("time", formattedNow);

        return "testTime";
    }


    @PostMapping("/edit_user")
    public String editUser(@RequestParam long id_user, Model model ) {
     Optional<User> optionalUser =  userRepository.findById(id_user);
     User user = optionalUser.orElse(null);

     if (user == null){
         return "redirect:/spisok";
     }

     model.addAttribute("user", user);
     return "editUser";

    }
    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute User user) {

        userRepository.save(user);
        return "redirect:/spisok";
    }

}
