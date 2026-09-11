package org.doit.watcha.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordService {

    // 🔥 메모리 저장 (서버 재시작 전까지 유지)
    private final List<String> recentList = new ArrayList<>();

    // ===============================
    // 🔎 검색어 저장
    // ===============================
    public void saveKeyword(String keyword, String memberId) {

        if (keyword == null || keyword.trim().isEmpty()) return;

        keyword = keyword.trim();

        // 중복 제거
        recentList.remove(keyword);

        // 맨 위에 추가
        recentList.add(0, keyword);

        // 최대 5개 유지
        if (recentList.size() > 5) {
            recentList.remove(recentList.size() - 1);
        }
    }

    // ===============================
    // 🔥 최근 검색어 반환
    // ===============================
    public List<Map<String, String>> getRecentKeywords(String memberId) {

        return recentList.stream()
                .map(k -> Map.of("keywordText", k))
                .collect(Collectors.toList());
    }

    // ===============================
    // 🔥 인기 검색어 (고정)
    // ===============================
    public List<Map<String, String>> getPopularKeywords() {

        return List.of(
                Map.of("keywordText", "매드댄스오피스"),
                Map.of("keywordText", "브라이드!"),
                Map.of("keywordText", "왕과사는남자"),
                Map.of("keywordText", "후민트"),
                Map.of("keywordText", "호퍼스")
        );
    }

    // ===============================
    // 🗑 모두 삭제
    // ===============================
    public void deleteRecentByMember(String memberId) {
        recentList.clear();
    }

    // ===============================
    // ❌ 개별 삭제 추가
    // ===============================
    public void deleteOne(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) return;

        recentList.remove(keyword.trim());
    }
}