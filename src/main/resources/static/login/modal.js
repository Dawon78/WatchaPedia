// 페이지의 모든 요소가 로드된 후 실행
window.addEventListener('load', () => {
    
    // 1. 로그인 모달 관련 요소
    const loginBtn = document.getElementById('loginBtn');
    const loginModal = document.getElementById('loginModal');

    // 2. 회원가입 모달 관련 요소
    const signUpBtn = document.getElementById('signUpBtn');
    const joinModal = document.getElementById('joinModal');

    // --- 로그인 모달 로직 ---
    if (loginBtn && loginModal) {
        loginBtn.onclick = () => {
            loginModal.style.display = 'flex'; // 중앙 정렬을 위해 flex 사용
            document.body.style.overflow = 'hidden'; // 스크롤 방지
        };
    }

    // --- 회원가입 모달 로직 ---
    if (signUpBtn && joinModal) {
        signUpBtn.onclick = () => {
            joinModal.style.display = 'flex'; // 강제 노출
            document.body.style.overflow = 'hidden';
        };
    }

    // --- 배경 클릭 시 닫기 통합 로직 ---
    window.onclick = (e) => {
        if (e.target === loginModal) {
            loginModal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
        if (e.target === joinModal) {
            joinModal.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
    };
});

// 외부 호출용 닫기 함수 (필요 시 HTML에서 사용)
window.closeModal = function(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = 'auto';
    }
}