package org.doit.watcha.admin;

import org.doit.watcha.member.Member;
import org.doit.watcha.member.MemberRepository;
import org.doit.watcha.work.Person;
import org.doit.watcha.work.PersonRepository;
import org.doit.watcha.work.Work;
import org.doit.watcha.work.WorkRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberRepository memberRepo;
    private final PersonRepository personRepo;
    private final WorkRepository workRepo;
    private final PasswordEncoder passwordEncoder;

    /* ===== 메인 ===== */
    @GetMapping
    public String admin(Model model) {
        model.addAttribute("members", memberRepo.findAll());
        model.addAttribute("persons", personRepo.findAll());
        model.addAttribute("works", workRepo.findAll());
        return "admin/admin";
    }

    /* ================= 회원 ================= */

    @PostMapping("/member/add")
    public String addMember(Member m) {
        m.setMemberPw(passwordEncoder.encode(m.getMemberPw()));
        memberRepo.save(m);
        return "redirect:/admin";
    }

    @PostMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable("id") String id) {
        memberRepo.deleteById(id);
        return "redirect:/admin";
    }

    /* ================= 인물 ================= */

    @PostMapping("/person/add")
    public String addPerson(Person p) {
        personRepo.save(p);
        return "redirect:/admin?tab=person";
    }

    @PostMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") Integer id) {
        personRepo.deleteById(id);
        return "redirect:/admin?tab=person";
    }

    /* ================= 작품 ================= */

    @PostMapping("/work/add")
    public String addWork(Work w) {
        workRepo.save(w);
        return "redirect:/admin?tab=work";
    }

    @PostMapping("/work/delete/{id}")
    public String deleteWork(@PathVariable("id") Integer id) {
        workRepo.deleteById(id);
        return "redirect:/admin?tab=work";
    }
  
}
