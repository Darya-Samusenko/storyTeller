package com.example.storyteller.controllers;

import com.example.storyteller.models.User;
import com.example.storyteller.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        User founduser = userService.getUserByPrincipal(principal);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "login";
    }

    @PostMapping("/login")
    public String login_user(Principal principal, Model model) {
        User founduser = userService.getUserByPrincipal(principal);
        if(founduser == null){
            model.addAttribute("user", userService.getUserByPrincipal(principal));
            model.addAttribute("incorrect_log", "incorrect login or password");
            return "login";
        }
        return "redirect:/";
    }

    /**
     * Возвращает ссылку на страницу профиля, добавляя в шаблон страницы нужные данные
     *
     *  @param model модель страницы HTML
     *  @param principal пользователь
     *
     *  @return ссылка на следующую страницу*/
    @GetMapping("/profile")
    public String profile(Principal principal,
                          Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("owner", user);
        return "profile";
    }
    /**
     * Возвращает ссылку на форму регистрации, добавляя в шаблон страницы нужные данные
     *
     *  @param model модель страницы HTML
     *  @param principal пользователь
     *
     *  @return ссылка на форму регистрации*/
    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";
    }

    /**
     * Вызывается при отправке формы регистрации
     *
     *  @param model модель страницы HTML
     *  @param user пользователь, который хочет зарегистрироваться
     *
     *  @return ссылка на форму регистрации или ссылка на страницу авторизации*/
    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "User with email: " + user.getEmail() + " already exists");
            return "registration";
        }
        return "redirect:/login";
    }

    /**
     * Переходит на страницу информации об аккаунте пользователя
     *
     *  @param model модель страницы HTML
     *  @param principal пользователь
     *  @param user пользователь, чтью информацию хотим просмотреть
     *
     *  @return ссылка на страницу профиля пользователя*/
    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getWorks());//проверь, то ли это
        return "user-info";
    }
}
