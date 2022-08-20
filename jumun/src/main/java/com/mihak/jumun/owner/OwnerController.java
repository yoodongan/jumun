package com.mihak.jumun.owner;

import com.mihak.jumun.owner.dto.form.SignupFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/")
    @ResponseBody
    private String root() {
        return "login success";
    }

    @GetMapping("/login")
    private String login() {
        return "login_form";
    }

    @GetMapping("/new")
    private String signupForm(Model model) {
        model.addAttribute("signupForm", new SignupFormDto());
        return "signup_form";
    }

    @PostMapping("/new")
    private String signup(@Validated @ModelAttribute SignupFormDto signupFormDto,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "signup_form";
        }

        if (!signupFormDto.getPassword1().equals(signupFormDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            ownerService.signup(signupFormDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("duplicatedOwnerId", "이미 등록된 아이디 입니다");
            return "signup_form";
        } catch (Exception e) {
            return "signup_form";
        }

        return "redirect:/admin/login";
    }
}