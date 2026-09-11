/**
 * 친구 찾기 모달 및 팔로우 비동기 처리
 */

function toggleUniqueFriendModal(show) {
    const friendModal = document.getElementById('uModFriendLayer');
    if (friendModal) {
        friendModal.style.display = show ? 'flex' : 'none';
    }
}

function toggleUniqueFollow(followingId, btn) {
    if (btn.disabled) return;
    btn.disabled = true; // 중복 클릭 방지

    fetch(`/api/follow/${followingId}`, {
        method: 'POST'
    })
    .then(res => {
        if (!res.ok) throw new Error('status: ' + res.status);
        return res.text();
    })
    .then(data => {
        if (data === 'followed') {
            btn.innerText = '팔로잉';
            btn.classList.add('following');
        } else if (data === 'unfollowed') {
            btn.innerText = '팔로우';
            btn.classList.remove('following');
        } else if (data === 'auth_required') {
            alert('로그인이 필요한 서비스입니다.');
        }
    })
    .catch(error => {
        console.error('Follow Error:', error);
        alert('처리 중 오류가 발생했습니다.');
    })
    .finally(() => {
        btn.disabled = false;
    });
}

// 모달 바깥 영역 클릭 시 닫기 (기존 코드 통합)
document.addEventListener('click', function(event) {
    const friendModal = document.getElementById('uModFriendLayer');
    if (event.target === friendModal) {
        toggleUniqueFriendModal(false);
    }
});