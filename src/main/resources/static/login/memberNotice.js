// ===============================
// 고객센터 모달 제어 (추가)
// ===============================
function toggleMypageNotice(show) {
    const noticeModal = document.getElementById('uModNoticeLayer');
    if (noticeModal) {
        noticeModal.style.display = show ? 'flex' : 'none';
    }
}

window.onclick = function(event) {

    const noticeModal = document.getElementById('uModNoticeLayer'); // ⭐ 추가

    if (event.target === noticeModal) toggleMypageNotice(false); // ⭐ 추가
};

function toggleNoticeItem(element) {
    element.classList.toggle("active");
}