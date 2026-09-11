package org.doit.watcha.megazine;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MagazineService {

    private final MagazineRepository magazineRepository;

    // 카테고리별 10개 가져오기
    public List<Magazine> getByCategory(Integer categoryId) {
        return magazineRepository
                .findByMagazineCategoryIdOrderByMagazineCreateDateDesc(categoryId);
    }
    // 최신순 6개
    public List<Magazine> findTop6() {

        List<Magazine> list =
            magazineRepository.findTop6ByOrderByMagazineCreateDateDesc();

        for (Magazine m : list) {
            String categoryName =
                magazineRepository
                    .findNameById(m.getMagazineCategoryId());

            m.setMagazineCategoryName(categoryName);
        }

        return list;
    }
}