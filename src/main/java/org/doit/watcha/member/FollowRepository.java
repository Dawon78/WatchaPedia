package org.doit.watcha.member;

import org.doit.watcha.member.Follow;
import org.doit.watcha.member.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    // 1. 나를 팔로우하는 사람들의 수 (팔로워 카운트)
    long countByFollowingId(String followingId);

    // 2. 내가 팔로우하는 사람들의 수 (팔로잉 카운트)
    long countByFollowerId(String followerId);

    // 3. 나를 팔로우하는 사람들 리스트 (팔로워 리스트 조회용)
    List<Follow> findByFollowingId(String followingId);

    // 4. 내가 팔로우하는 사람들 리스트 (팔로잉 리스트 조회용)
    List<Follow> findByFollowerId(String followerId);

    // 5. 이미 팔로우 중인지 확인
    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

    // 6. 언팔로우 (삭제)
    void deleteByFollowerIdAndFollowingId(String followerId, String followingId);
}