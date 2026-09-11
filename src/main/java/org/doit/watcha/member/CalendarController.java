package org.doit.watcha.member;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String calendarPage(@RequestParam(value = "year", required = false) Integer year, 
            @RequestParam(value = "month", required = false) Integer month, Model model) {
        
        // 현재 날짜 기준 또는 파라미터 기준 설정
        LocalDate today = LocalDate.now();
        int targetYear = (year == null) ? today.getYear() : year;
        int targetMonth = (month == null) ? today.getMonthValue() : month;
        
        LocalDate firstDay = LocalDate.of(targetYear, targetMonth, 1);
        int lastDay = firstDay.lengthOfMonth();
        
        // 시작 요일 계산 (일:0, 월:1, ... 토:6)
        int startDayOfWeek = firstDay.getDayOfWeek().getValue() % 7; 
        
        model.addAttribute("currentYear", targetYear);
        model.addAttribute("currentMonth", targetMonth);
        model.addAttribute("startDayOfWeek", startDayOfWeek); 
        model.addAttribute("lastDay", lastDay); 
        
        return "member/calendar";
    }
    
    @GetMapping("/calendar/search")
    public String calendarSearchPage(Model model) {
        // 검색 초기 페이지에 필요한 데이터가 있다면 추가
        return "member/calendarSearch"; 
    }
}