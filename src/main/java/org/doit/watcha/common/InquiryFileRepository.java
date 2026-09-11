package org.doit.watcha.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryFileRepository extends JpaRepository<InquiryFile, Integer> {
}