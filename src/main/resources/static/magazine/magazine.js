document.addEventListener("DOMContentLoaded", function () {

    const cards = document.querySelectorAll("._component_82rb0_1");

    cards.forEach(card => {
        card.classList.add("_sunrise_82rb0_17");
    });

});

document.addEventListener("DOMContentLoaded", function () {

    const buttons = document.querySelectorAll("._ul_1eeva_1 button");
    const cards = document.querySelectorAll("._li_1dfi5_17");

    buttons.forEach(button => {

        button.addEventListener("click", function () {

            const filter = this.dataset.filter;

            // 버튼 선택 스타일 변경
            buttons.forEach(btn =>
                btn.classList.remove("_chipSelected_faef4_23")
            );
            this.classList.add("_chipSelected_faef4_23");

            // 카드 필터링
            cards.forEach(card => {

                if (filter === "전체") {
                    card.style.display = "block";
                } else {
                    if (card.dataset.category === filter) {
                        card.style.display = "block";
                    } else {
                        card.style.display = "none";
                    }
                }

            });

        });

    });

});