/**
 * CreateCollection.js
 * 컬렉션 생성 페이지의 동적 UI 및 삭제 모드 제어
 */
document.addEventListener('DOMContentLoaded', function () {
    const colTitle = document.getElementById('colTitle');
    const submitBtn = document.getElementById('submitBtn');
    let deleteList = new Set(); // 삭제 대기 리스트

    // 1. 제목 입력 시 버튼 활성화 로직
    if (colTitle && submitBtn) {
        colTitle.addEventListener('input', function () {
            if (this.value.trim().length > 0) {
                submitBtn.classList.add('active');
                submitBtn.disabled = false;
            } else {
                submitBtn.classList.remove('active');
                submitBtn.disabled = true;
            }
        });
    }

    // 2. 작품 추가 로직 (모달에서 호출)
    window.appendWorksToForm = function (works) {
        const wrapper = document.querySelector('.work-list-wrapper');
        const hiddenContainer = document.getElementById('hidden-inputs');

        works.forEach(work => {
            if (document.getElementById('work-' + work.workId)) return; // 중복 방지

            // 카드 생성
            const card = document.createElement('div');
            card.className = 'work-card-sm';
            card.id = 'work-' + work.workId;
            card.innerHTML = `
                <div class="card-img-container" style="position: relative;">
                    <div class="delete-badge" onclick="markForDelete(${work.workId}, this)"
                         style="display: none; position: absolute; top: -8px; left: -8px; width: 24px; height: 24px; background: #ff0558; border-radius: 50%; color: #fff; text-align: center; line-height: 20px; font-weight: bold; cursor: pointer; border: 2px solid #fff; z-index: 10;">
                        -
                    </div>
                    <img src="/images/${work.workThumbnail || 'default.jpg'}" style="width: 100%; border-radius: 4px;">
                </div>
                <span class="work-card-title">${work.workTitle}</span>
            `;
            wrapper.appendChild(card);

            // Hidden Input 생성 (서버 전송용)
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'workIds';
            input.value = work.workId;
            input.id = 'input-' + work.workId;
            hiddenContainer.appendChild(input);
        });
        updateTotalCount();
    };

    // 3. 수정 모드 토글
    window.toggleEditMode = function (isActive) {
        const defHeader = document.getElementById('edit-mode-default');
        const actHeader = document.getElementById('edit-mode-active');
        const badges = document.querySelectorAll('.delete-badge');

        if (isActive) {
            defHeader.style.display = 'none';
            actHeader.style.display = 'block';
            badges.forEach(b => b.style.display = 'block');
        } else {
            defHeader.style.display = 'block';
            actHeader.style.display = 'none';
            badges.forEach(b => {
                b.style.display = 'none';
                b.style.background = '#ff0558';
            });
            deleteList.clear();
            updateRemoveBtnStyle();
        }
    };

    // 4. 삭제 대상 마킹
    window.markForDelete = function (workId, element) {
        if (deleteList.has(workId)) {
            deleteList.delete(workId);
            element.style.background = '#ff0558';
        } else {
            deleteList.add(workId);
            element.style.background = '#333';
        }
        updateRemoveBtnStyle();
    };

    // 5. 제거 버튼 상태 업데이트
    function updateRemoveBtnStyle() {
        const btn = document.getElementById('remove-btn');
        const count = deleteList.size;
        btn.innerText = `${count}개 제거`;
        if (count > 0) {
            btn.style.color = '#ff0558';
            btn.style.cursor = 'pointer';
            btn.style.fontWeight = 'bold';
        } else {
            btn.style.color = '#ccc';
            btn.style.cursor = 'default';
            btn.style.fontWeight = 'normal';
        }
    }

    // 6. 실제 제거 수행
    window.removeSelectedWorks = function () {
        if (deleteList.size === 0) return;

        deleteList.forEach(id => {
            const card = document.getElementById('work-' + id);
            const input = document.getElementById('input-' + id);
            if (card) card.remove();
            if (input) input.remove();
        });

        updateTotalCount();
        window.toggleEditMode(false);
    };

    // 7. 총 개수 갱신
    function updateTotalCount() {
        const currentCount = document.querySelectorAll('.work-card-sm').length;
        const totalCountSpan = document.getElementById('total-work-count');
        if (totalCountSpan) {
            totalCountSpan.innerText = `(${currentCount}/1000)`;
        }
    }
});