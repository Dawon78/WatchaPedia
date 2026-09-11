package org.doit.watcha.member;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import java.util.Optional; // 🚩 이 줄이 꼭 있어야 합니다.
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;

    @Transactional
    public void toggleStorage(String memberId, Integer workId, StorageStatus status) {
        StorageId id = new StorageId(memberId, workId);
        Optional<Storage> existing = storageRepository.findById(id);

        if (existing.isPresent()) {
            Storage storage = existing.get();
            
            if (storage.getStorageStatus() == status) {
                storageRepository.delete(storage);
            } else {
                storage.setStorageStatus(status);
                storage.setStorageAddDate(LocalDate.now());
                storageRepository.save(storage);
            }
        } else {
            Storage storage = new Storage();
            storage.setMemberId(memberId);
            storage.setWorkId(workId);
            storage.setStorageStatus(status);
            storage.setStorageAddDate(LocalDate.now());
            storageRepository.save(storage);
        }
    }
}