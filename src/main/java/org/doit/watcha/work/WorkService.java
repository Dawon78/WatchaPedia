package org.doit.watcha.work;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final PersonRepository personRepository;

    private final WorkRepository workRepository;

   

    // 공통 로직: 리스트를 받아서 각 작품에 장르를 채워줌
    private void fillGenres(List<Work> works) {
        for (Work w : works) {
            String genres = workRepository.findGenreNamesByWorkId(w.getWorkId());
            w.setGenre(genres); // @Transient 필드에 저장
        }
    }

    // 최신 웹툰
    public List<Work> findLatestWebtoon() {
        List<Work> list = workRepository.findTop10ByWorkTypeOrderByWorkPublishDateDesc(WorkType.WEBTOON);
        fillGenres(list); // 장르 채우기 호출
        return list;
    }

    // HOT 점수 높은 웹툰
    public List<Work> findHotWebtoon() {
        List<Work> list = workRepository.findTop10ByWorkTypeOrderByHotScoreDesc(WorkType.WEBTOON);
        fillGenres(list); // 장르 채우기 호출
        return list;
    }

    // 랭킹 높은 웹툰
    public List<Work> findTopRankWebtoon() {
        List<Work> list = workRepository.findTop10ByWorkTypeOrderByWorkRankAsc(WorkType.WEBTOON);
        fillGenres(list); // 장르 채우기 호출
        return list;
    }
    
    // 상세 조회 시에도 장르가 필요하다면 추가
    public Work findById(Integer id) {
        Work work = workRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 작품입니다."));
        work.setGenre(workRepository.findGenreNamesByWorkId(work.getWorkId()));
        return work;
    }
    
 // BL 목록 (장르 ID: 7번)
    public List<Work> findBlWebtoon() {
        List<Work> list = workRepository.findTop5ByGenreId(7); 
        fillGenres(list);
        return list;
    }

    // 로맨스 목록 (장르 ID: 6번)
    public List<Work> findRomanceWebtoon() {
        List<Work> list = workRepository.findTop5ByGenreId(6);
        fillGenres(list);
        return list;
    }
    
 // 상세 조회: 모든 연관 데이터(상세내용, 작가, 인물 등)를 포함
    public Work findWebtoonDetail(Integer id) {
        // 1. Repository에 새로 추가할 fetch join 메서드 호출 (id 타입 Integer 확인!)
        Work work = workRepository.findByIdWithAllDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 작품입니다. ID: " + id));
        
        // 2. 장르 채우기 (기존 로직 활용)
        work.setGenre(workRepository.findGenreNamesByWorkId(work.getWorkId()));
        
        return work;
    }
    public List<Work> findRandomWorksByType(WorkType type) {
        return workRepository.findRandomWorksByType(type);
    }
    

    // 🎬 왓챠피디아 HOT 랭킹 (MOVIE 타입, HOTSCORE 기준)
    public List<Work> findHotMovie() {
        List<Work> list =
                workRepository.findTop10ByWorkTypeOrderByHotScoreDesc(WorkType.MOVIE);
        fillGenres(list);
        return list;
    }
    
   

    public List<Person> getPersonRankingList() {
        return workRepository.findPersonRanking()
                .stream()
                .limit(12)
                .toList();
    }
    
    // 🔎 검색
    public List<Work> searchByTitleAndType(String keyword, WorkType type) {
        return workRepository
            .findByWorkTitleContainingIgnoreCaseAndWorkType(keyword, type);
    }
    
    public List<Work> searchByTitle(String keyword) {
        return workRepository.searchWithCast(keyword);
    }
    
    public List<Person> searchByName(String keyword) {
        return personRepository.searchRelatedPersons(keyword);
    }
    
}