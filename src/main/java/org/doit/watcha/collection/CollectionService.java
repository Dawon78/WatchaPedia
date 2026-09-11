package org.doit.watcha.collection;

import java.util.List;
import org.doit.watcha.work.Work;
import org.doit.watcha.work.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CollectionService {
    private final CollectionRepository collectionRepository;
    private final WorkRepository workRepository;

    @Transactional
    public Collection save(CollectionRequest request, String memberId) {
        Collection collection = Collection.builder()
                .collectionTitle(request.getCollectionTitle())
                .collectionDesc(request.getCollectionDesc())
                .memberId(memberId)
                .build();
        
        if (request.getWorkIds() != null && !request.getWorkIds().isEmpty()) {
            // 💡 Integer 그대로 사용 (Long 변환 삭제)
            List<Work> works = workRepository.findAllById(request.getWorkIds());
            collection.setWorks(works);
        }

        return collectionRepository.save(collection);
    }

    public List<Collection> findAll() {
        return collectionRepository.findAllWithWorks();
    }

    public Collection findById(Integer id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬렉션입니다. ID: " + id));
    }
    
    @Transactional
    public void deleteById(Integer id) {
        if (!collectionRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제하려는 컬렉션이 존재하지 않습니다. ID: " + id);
        }
        collectionRepository.deleteById(id);
    }
    
    @Transactional
    public void update(Integer id, CollectionRequest request) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid collection Id:" + id));
        
        collection.setCollectionTitle(request.getCollectionTitle());
        collection.setCollectionDesc(request.getCollectionDesc());
        
        if (request.getWorkIds() != null) {
            // 💡 Integer 그대로 사용 (Long 변환 삭제)
            List<Work> works = workRepository.findAllById(request.getWorkIds());
            collection.setWorks(works);
        }
    }
    
    public List<Collection> findByMemberId(String memberId) {
        return collectionRepository.findByMemberId(memberId);
    }
    
    public long countByMemberId(String memberId) {
        return collectionRepository.countByMemberId(memberId);
    }
}