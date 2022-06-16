package com.makeev.springmvc.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.lang.Integer.*;

@Controller
//@RequestMapping("/first")  // URL .../first/hello
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value="name", required = false) String name,
                            @RequestParam(value="surname", required = false) String surname) {
        // @RequestParam - get only one parameter
        System.out.println("Hello " + name + " " + surname);
        return "/first/helloPage";
    }

    @GetMapping("/bye")
    public String goodByePage(HttpServletRequest request) {
        // HttpServletRequest - all request
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        System.out.println("Bye " + name + " " + surname);
        System.out.println(request.toString());

        return "/first/goodByPage";
    }

    // Model will be sent to .html by Thymeleaf
    @GetMapping("/model")
    public String changeModel(HttpServletRequest request, Model model) {
        String modelMessage = "From model: " + request.getParameter("name")
                + " " + request.getParameter("surname");
        model.addAttribute("message", modelMessage);
        return "/first/modelPage";

    }

    @GetMapping("/calc")
    public String calculator(HttpServletRequest request, Model model) {
        int left = parseInt(Optional.ofNullable(request.getParameter("a")).orElse("0"));
        int right = parseInt(Optional.ofNullable(request.getParameter("b")).orElse("0"));
        String operand = Optional.ofNullable(request.getParameter("o")).orElse("+");
        String modelMessage = switch (operand) {
            case "+" ->  Integer.toString(left + right);
            case "-" ->  Integer.toString(left - right);
            case "/" ->  Integer.toString(left / right);
            case "*" ->  Integer.toString(left * right);
            default -> "0";
        };
        //model.addAttribute("left", Integer.toString(left));
        model.addAttribute("left", left);
        model.addAttribute("right", Integer.toString(right));
        model.addAttribute("operand", operand);
        model.addAttribute("result", modelMessage);
        return "/first/calcPage";
    }
}
