document.addEventListener("DOMContentLoaded", function () {

    if (window.searchInitialized) return;
    window.searchInitialized = true;

    const input = document.getElementById("searchInput");
    const dropdown = document.getElementById("searchDropdown");

    if (!input || !dropdown) return;

    // 🔥 자동 추천 (타이핑 시)
    input.addEventListener("input", loadPopup);

    // 🔥 포커스 시 열기
    input.addEventListener("focus", loadPopup);

    function loadPopup() {

        fetch("/search/popup")
            .then(res => res.json())
            .then(data => {

                let html = "";

                // ===============================
                // 최근 검색어
                // ===============================
                if (data.recent.length > 0) {

                    html += `
                        <div style="padding:15px;">
                            <div style="display:flex; justify-content:space-between; font-size:13px; font-weight:600; margin-bottom:8px; color:#ff0558;">
                                <span>최근 검색어</span>
                                <span id="clearRecentBtn"
                                      style="font-size:12px; color:#999; cursor:pointer;">
                                      모두 삭제
                                </span>
                            </div>
                    `;

                    data.recent.forEach(item => {
                        html += `
                            <div style="display:flex; justify-content:space-between; padding:6px 0; cursor:pointer;"
                                 data-keyword="${item.keywordText}">
                                 <span>${item.keywordText}</span>
                                 <span class="delete-one"
                                       data-delete="${item.keywordText}"
                                       style="color:#999; cursor:pointer;">✕</span>
                            </div>
                        `;
                    });

                    html += `</div>
                             <div style="height:1px; background:#eee;"></div>`;
                }

                // ===============================
                // 인기 검색어
                // ===============================
                if (data.popular.length > 0) {

                    html += `
                        <div style="padding:15px;">
                            <div style="font-size:13px; font-weight:600; margin-bottom:8px; color:#ff0558;">
                                인기 검색어
                            </div>
                    `;

                    data.popular.forEach(item => {
                        html += `
                            <div style="padding:6px 0; cursor:pointer;"
                                 data-keyword="${item.keywordText}">
                                 ${item.keywordText}
                            </div>
                        `;
                    });

                    html += `</div>`;
                }

                dropdown.innerHTML = html;
                dropdown.style.display = "block";

                // 🔍 검색어 클릭 → input 채우기
                dropdown.querySelectorAll("[data-keyword]").forEach(el => {
                    el.addEventListener("click", function (e) {

                        if (e.target.classList.contains("delete-one")) return;

                        const keyword = this.dataset.keyword;
                        input.value = keyword;
                        window.location.href =
                            "/search?keyword=" + encodeURIComponent(keyword);
                    });
                });

                // ❌ 개별 삭제
                dropdown.querySelectorAll(".delete-one").forEach(el => {
                    el.addEventListener("click", function (e) {

                        e.stopPropagation();

                        fetch("/search/delete-one", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded"
                            },
                            body: "keyword=" + encodeURIComponent(this.dataset.delete)
                        })
                        .then(() => loadPopup());
                    });
                });

                // 🗑 모두 삭제
                const clearBtn = document.getElementById("clearRecentBtn");

                if (clearBtn) {
                    clearBtn.addEventListener("click", function () {

                        fetch("/search/clear-recent", {
                            method: "POST"
                        })
                        .then(() => {
                            loadPopup();
                            input.value = "";
                        });
                    });
                }

            });
    }

    // 바깥 클릭 시 닫기
    document.addEventListener("click", function (e) {
        if (!input.contains(e.target) &&
            !dropdown.contains(e.target)) {
            dropdown.style.display = "none";
        }
    });

});