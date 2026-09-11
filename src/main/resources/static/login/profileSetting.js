/** 💡 설정 모달 열기/닫기 */
function toggleProfileSettings(show) {
    const layer = document.getElementById('uModSettingsLayer');
    if (layer) {
        layer.style.display = show ? 'flex' : 'none';
    }
}

/** 💡 토글 스위치 활성화/비활성화 이벤트 */
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('u-toggle')) {
        e.target.classList.toggle('active');
    }
});