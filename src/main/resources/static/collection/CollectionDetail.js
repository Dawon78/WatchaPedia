// 더보기 메뉴 토글
function toggleMoreMenu(e) {
    e.stopPropagation();
    const menu = document.getElementById('more-dropdown');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
}

// 외부 클릭 시 메뉴 닫기
document.addEventListener('click', () => {
    document.getElementById('more-dropdown').style.display = 'none';
});

// 삭제 기능 연동
function deleteCollection(id) {
    if (confirm("이 컬렉션을 정말 삭제하시겠습니까?")) {
        fetch(`/collection/delete?id=${id}`, { method: 'DELETE' })
            .then(res => {
                if (res.ok) {
                    alert("삭제되었습니다.");
                    location.href = "/collection/list";
                } else {
                    alert("삭제 권한이 없거나 오류가 발생했습니다.");
                }
            });
    }
}
// 모든 작품 보여주기 함수
function showAllWorks() {
    // 1. 숨겨진 모든 작품 요소를 찾음
    const hiddenWorks = document.querySelectorAll('.coll-work-item.is-hidden');
    
    // 2. 각 요소에서 is-hidden 클래스 제거 (화면에 나타남)
    hiddenWorks.forEach(work => {
        work.classList.remove('is-hidden');
    });
    
    // 3. 더보기 버튼은 이제 필요 없으므로 숨김
    const loadMoreBtn = document.getElementById('btn-load-more');
    if (loadMoreBtn) {
        loadMoreBtn.style.display = 'none';
    }
}

/**
 * 컬렉션 좋아요 토글 기능
 */
function toggleLike(collectionId) {
    fetch(`/api/collection/${collectionId}/like`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    })
    .then(res => {
        if (res.status === 401) {
            alert("로그인이 필요한 서비스입니다.");
            return;
        }
        return res.json();
    })
    .then(isLiked => {
        if (isLiked !== undefined) {
            const likeBtn = document.getElementById(`detail-like-btn-${collectionId}`);
            const countDisplay = document.getElementById('like-count-display'); // 버튼 내 숫자
            const statsCountDisplay = document.getElementById('stats-like-count'); // 상단 통계 숫자
            
            let currentCount = parseInt(countDisplay.innerText);

            if (isLiked) {
                // 💡 좋아요 등록
                likeBtn.classList.add('active');
                const newCount = currentCount + 1;
                countDisplay.innerText = newCount;
                if(statsCountDisplay) statsCountDisplay.innerText = newCount;
            } else {
                // 💡 좋아요 취소
                likeBtn.classList.remove('active');
                const newCount = Math.max(0, currentCount - 1);
                countDisplay.innerText = newCount;
                if(statsCountDisplay) statsCountDisplay.innerText = newCount;
            }
        }
    })
    .catch(err => console.error("Error:", err));
}