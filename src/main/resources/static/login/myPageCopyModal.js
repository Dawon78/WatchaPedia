// 수정 모달 제어
function toggleUniqueEditModal(show) {
    document.getElementById('uModEditLayer').style.display = show ? 'flex' : 'none';
}

// 링크 복사 및 알림 제어
function executeUniqueCopyLink() {
    const url = window.location.href;
    navigator.clipboard.writeText(url).then(() => {
        toggleUniqueAlertModal(true);
    });
}

function toggleUniqueAlertModal(show) {
    document.getElementById('uModAlertLayer').style.display = show ? 'flex' : 'none';
}

// 배경 클릭 시 닫기
window.onclick = function(e) {
    if (e.target.classList.contains('u-mod-overlay')) {
        toggleUniqueEditModal(false);
        toggleUniqueAlertModal(false);
    }
}