package org.doit.watcha.common;

import org.doit.watcha.collection.Collection;
import org.doit.watcha.collection.CollectionRepository;
import org.doit.watcha.member.Member;
import org.doit.watcha.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionLikeService {

    private final CollectionLikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final CollectionRepository collectionRepository;

    @Transactional
    public boolean toggleLike(Integer collectionId, String memberId) {
        boolean exists = likeRepository.existsByMemberMemberIdAndCollectionCollectionId(memberId, collectionId);

        if (exists) {
            // 좋아요 취소
            likeRepository.deleteByMemberMemberIdAndCollectionCollectionId(memberId, collectionId);
            return false;
        } else {
            // 좋아요 등록
            Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("컬렉션을 찾을 수 없습니다."));

            CollectionLike like = CollectionLike.builder()
                .member(member)
                .collection(collection)
                .build();
            
            likeRepository.save(like);
            return true;
        }
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Integer collectionId, String memberId) {
        if (memberId == null) return false; // 로그인 안했으면 무조건 false
        return likeRepository.existsByMemberMemberIdAndCollectionCollectionId(memberId, collectionId);
    }
}