package org.doit.watcha.member;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    
    @Transactional(readOnly = true)
    public long getFollowerCount(String memberId) {
        return followRepository.countByFollowingId(memberId);
    }

    @Transactional(readOnly = true)
    public long getFollowingCount(String memberId) {
        return followRepository.countByFollowerId(memberId);
    }
    
 // 나를 팔로우하는 사람들의 정보를 리스트로 반환
    @Transactional(readOnly = true)
    public List<Member> getFollowerList(String memberId) {
        // 1. 나를 팔로우하는 Follow 엔티티 리스트 조회
        List<Follow> followers = followRepository.findByFollowingId(memberId);
        
        // 2. 팔로워들의 ID를 추출하여 Member 정보들로 변환
        return followers.stream()
                .map(follow -> memberRepository.findById(follow.getFollowerId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public String toggleFollow(String followerId, String followingId) {
        if (followerId.equals(followingId)) return "self"; // 본인 팔로우 방지

        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
            return "unfollowed";
        } else {
            followRepository.save(new Follow(followerId, followingId));
            return "followed";
        }
    }
    
 // 내가 팔로우하는 사람들의 정보를 리스트로 반환 (Following)
    @Transactional(readOnly = true)
    public List<Member> getFollowingList(String memberId) {
        // 1. 내가 팔로워(followerId)인 데이터 리스트 조회
        List<Follow> followings = followRepository.findByFollowerId(memberId);
        
        // 2. 내가 팔로우하는 대상(followingId)의 ID를 추출하여 Member 정보로 변환
        return followings.stream()
                .map(follow -> memberRepository.findById(follow.getFollowingId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
 // FollowService.java 에 추가
    @Transactional(readOnly = true)
    public List<String> getFollowingIds(String memberId) {
        return followRepository.findByFollowerId(memberId).stream()
                .map(Follow::getFollowingId)
                .collect(Collectors.toList());
    }
    
    
}