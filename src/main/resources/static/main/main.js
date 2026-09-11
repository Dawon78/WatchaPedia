/*버튼*/
document.addEventListener("DOMContentLoaded", function() {

  const scrollDiv = document.querySelector("._scrollDiv_15cav_5");
  const rightBtn = document.querySelector("._rightButton_15cav_33 button");
  const leftBtn = document.querySelector("._leftButton_15cav_39 button");
  const card = document.querySelector(".rolling-card");

  if (!scrollDiv || !rightBtn || !leftBtn || !card) return;

  const gap = 16;
  const unit = card.offsetWidth + gap;

  function updateButtons() {
    const leftWrapper = document.querySelector("._leftButton_15cav_39");
    const rightWrapper = document.querySelector("._rightButton_15cav_33");

    if (scrollDiv.scrollLeft <= 0) {
      leftWrapper.style.display = "none";
    } else {
      leftWrapper.style.display = "flex";
    }

    if (scrollDiv.scrollLeft + scrollDiv.clientWidth >= scrollDiv.scrollWidth - 5) {
      rightWrapper.style.display = "none";
    } else {
      rightWrapper.style.display = "flex";
    }
  }

  updateButtons();

  rightBtn.addEventListener("click", function() {
    scrollDiv.scrollBy({ left: unit, behavior: "smooth" });
  });

  leftBtn.addEventListener("click", function() {
    scrollDiv.scrollBy({ left: -unit, behavior: "smooth" });
  });

  scrollDiv.addEventListener("scroll", updateButtons);

});
/*퀙 메뉴 글씨 애니메이션*/
document.addEventListener("DOMContentLoaded", function () {
    const wrapper = document.querySelector("._textWrapper_1tmkw_32");
    if (!wrapper) return;

    const texts = wrapper.querySelectorAll("._text_1tmkw_32:not(._hiddenText_1tmkw_51)");
    let current = 0;

    // 초기 상태 정리
    texts.forEach((el, i) => {
        if (i === 0) {
            el.classList.add("_currentText_1tmkw_70");
            el.classList.remove("_nextText_1tmkw_79");
        } else {
            el.classList.add("_nextText_1tmkw_79");
            el.classList.remove("_currentText_1tmkw_70");
        }
    });

    setInterval(() => {
        const currentEl = texts[current];
        const next = (current + 1) % texts.length;
        const nextEl = texts[next];

        // 1️⃣ 다음 텍스트를 확실히 숨긴 상태로 먼저 세팅
        nextEl.classList.remove("_currentText_1tmkw_70");
        nextEl.classList.add("_nextText_1tmkw_79");

        // 👉 reflow 강제 (이게 핵심)
        void nextEl.offsetHeight;

        // 2️⃣ 애니메이션 시작
        currentEl.classList.add("_slideUp_1tmkw_75");
        nextEl.classList.add("_slideIn_1tmkw_84");

        setTimeout(() => {
            // 3️⃣ 애니메이션 종료 후 상태 확정
            currentEl.classList.remove("_slideUp_1tmkw_75");
            currentEl.classList.remove("_currentText_1tmkw_70");
            currentEl.classList.add("_nextText_1tmkw_79");

            nextEl.classList.remove("_slideIn_1tmkw_84");
            nextEl.classList.remove("_nextText_1tmkw_79");
            nextEl.classList.add("_currentText_1tmkw_70");

            current = next;
        }, 700);

    }, 2500);
});

/*인물 랭킹 버튼*/

document.addEventListener("DOMContentLoaded", function () {
  // ====== 셀렉터 (네 HTML 기준) ======
  const scroller = document.querySelector("._listWrapper_mz3qo_13.listWrapper");
  const list = document.querySelector("._peopleRankingList_fruph_29._list_mz3qo_13");
  const leftWrap = document.querySelector("._buttonContainer_mz3qo_56.arrowButton._left_mz3qo_72");
  const rightWrap = document.querySelector("._buttonContainer_mz3qo_56.arrowButton._right_mz3qo_68");

  if (!scroller || !list || !leftWrap || !rightWrap) return;

  const leftBtn = leftWrap.querySelector("button");
  const rightBtn = rightWrap.querySelector("button");

  // 왼쪽 버튼이 비활성일 때 붙는 클래스(네 HTML에 이미 있음)
  const DISABLED_CLASS = "_disabled_mz3qo_64";

  // 한 번에 넘길 카드 개수
  const STEP_COUNT = 6;

  // li 1개 너비(+간격) 계산
  function getItemStepWidth() {
    const firstItem = list.querySelector("li");
    if (!firstItem) return 0;

    const itemWidth = firstItem.getBoundingClientRect().width;

    // gap 계산(ul에 column-gap/gap이 있으면 반영)
    const listStyle = window.getComputedStyle(list);
    let gap = 0;

    // gap이 "10px 20px"처럼 2개 올 수도 있어서 안전 처리
    const gapStr = listStyle.columnGap || listStyle.gap || "0px";
    const gapValue = parseFloat(gapStr.split(" ")[0]) || 0;
    gap = gapValue;

    // li 자체 margin-right가 있는 경우도 반영
    const itemStyle = window.getComputedStyle(firstItem);
    const marginRight = parseFloat(itemStyle.marginRight) || 0;

    return itemWidth + gap + marginRight;
  }

  // 현재 스크롤 상태에 따라 버튼 disabled 처리
  function updateArrows() {
    const maxScrollLeft = scroller.scrollWidth - scroller.clientWidth;
    const x = scroller.scrollLeft;

    // 왼쪽 끝이면 left disabled
    if (x <= 0) leftWrap.classList.add(DISABLED_CLASS);
    else leftWrap.classList.remove(DISABLED_CLASS);

    // 오른쪽 끝이면 right disabled
    if (x >= maxScrollLeft - 1) rightWrap.classList.add(DISABLED_CLASS);
    else rightWrap.classList.remove(DISABLED_CLASS);
  }

  // 스크롤 이동 함수
  function scrollByCards(direction) {
    const one = getItemStepWidth();
    if (!one) return;

    const delta = one * STEP_COUNT * direction;
    scroller.scrollBy({
      left: delta,
      behavior: "smooth"
    });
  }

  // 클릭 이벤트
  rightBtn.addEventListener("click", function () {
    // disabled 상태면 무시
    if (rightWrap.classList.contains(DISABLED_CLASS)) return;
    scrollByCards(1);
  });

  leftBtn.addEventListener("click", function () {
    if (leftWrap.classList.contains(DISABLED_CLASS)) return;
    scrollByCards(-1);
  });

  // 스크롤 중에도 버튼 상태 갱신
  scroller.addEventListener("scroll", function () {
    updateArrows();
  });

  // 리사이즈 시(카드 너비 달라질 수 있음)
  window.addEventListener("resize", function () {
    updateArrows();
  });

  // 최초 상태
  updateArrows();
});


/*매거진 */
document.addEventListener("DOMContentLoaded", function () {
  const root = document.querySelector("._magazineList_fruph_22._list_mz3qo_13")?.closest("._container_60mdw_1");
  if (!root) return;

  const scroller = root.querySelector("._listWrapper_mz3qo_13.listWrapper");
  const list = root.querySelector("ul._magazineList_fruph_22._list_mz3qo_13");
  const leftWrap = root.querySelector("._buttonContainer_mz3qo_56.arrowButton._left_mz3qo_72");
  const rightWrap = root.querySelector("._buttonContainer_mz3qo_56.arrowButton._right_mz3qo_68");

  if (!scroller || !list || !leftWrap || !rightWrap) return;

  const leftBtn = leftWrap.querySelector("button");
  const rightBtn = rightWrap.querySelector("button");

  const DISABLED_CLASS = "_disabled_mz3qo_64";
  const STEP_COUNT = 3;

  // li 하나의 "실제 step(px)"를 정확히 구함: offsetLeft 차이로 계산 (가장 정확)
  function getStepPx() {
    const items = Array.from(list.querySelectorAll("li.w_exposed_cell"));
    if (items.length < 2) return 0;

    const a = items[0].offsetLeft;
    const b = items[1].offsetLeft;
    return Math.abs(b - a);
  }

  function clamp(n, min, max) {
    return Math.max(min, Math.min(max, n));
  }

  function updateArrows() {
    const maxScrollLeft = scroller.scrollWidth - scroller.clientWidth;
    const x = scroller.scrollLeft;

    if (x <= 0) leftWrap.classList.add(DISABLED_CLASS);
    else leftWrap.classList.remove(DISABLED_CLASS);

    if (x >= maxScrollLeft - 1) rightWrap.classList.add(DISABLED_CLASS);
    else rightWrap.classList.remove(DISABLED_CLASS);
  }

  // ✅ "딱 맞춰" 이동: step 단위로 반올림해서 목표 위치로 scrollTo
  function snapMove(direction) {
    const step = getStepPx();
    if (!step) return;

    const maxScrollLeft = scroller.scrollWidth - scroller.clientWidth;

    const current = scroller.scrollLeft;
    const target = current + (step * STEP_COUNT * direction);

    // 0, step, 2*step ... 이런 자리로 스냅
    const snapped = Math.round(target / step) * step;
    const finalX = clamp(snapped, 0, maxScrollLeft);

    scroller.scrollTo({ left: finalX, behavior: "smooth" });
  }

  rightBtn.addEventListener("click", function () {
    if (rightWrap.classList.contains(DISABLED_CLASS)) return;
    snapMove(1);
  });

  leftBtn.addEventListener("click", function () {
    if (leftWrap.classList.contains(DISABLED_CLASS)) return;
    snapMove(-1);
  });

  scroller.addEventListener("scroll", updateArrows);
  window.addEventListener("resize", function () {
    updateArrows();
  });

  // 이미지 로딩 때문에 step이 바뀌는 경우가 있어서, 로딩 후 한 번 더 갱신
  window.addEventListener("load", function () {
    updateArrows();
  });

  updateArrows();
});

document.addEventListener("DOMContentLoaded", function () {

  // ===== 지금 뜨는 코멘트 전용 =====
  const section = document.querySelector("._horizontalScroll_18d5u_1");
  if (!section) return;

  const scroller = section.querySelector("._listWrapper_mz3qo_13");
  const rightWrap = section.querySelector("._rightButton_15cav_33");
  const leftWrap = section.querySelector("._leftButton_15cav_39");

  if (!scroller || !rightWrap || !leftWrap) return;

  const rightBtn = rightWrap.querySelector("button");
  const leftBtn = leftWrap.querySelector("button");

  // 카드 하나 너비 계산
  function getCardWidth() {
    const firstCard = scroller.querySelector("li");
    if (!firstCard) return 0;

    const style = window.getComputedStyle(firstCard);
    const marginRight = parseFloat(style.marginRight) || 0;

    return firstCard.getBoundingClientRect().width + marginRight;
  }

  function updateButtons() {
    const maxScroll = scroller.scrollWidth - scroller.clientWidth;

    if (scroller.scrollLeft <= 0) {
      leftWrap.style.display = "none";
    } else {
      leftWrap.style.display = "flex";
    }

    if (scroller.scrollLeft >= maxScroll - 1) {
      rightWrap.style.display = "none";
    } else {
      rightWrap.style.display = "flex";
    }
  }

  function move(direction) {
    const step = getCardWidth();
    if (!step) return;

    scroller.scrollBy({
      left: step * direction,
      behavior: "smooth"
    });
  }

  rightBtn.addEventListener("click", function () {
    move(1);
  });

  leftBtn.addEventListener("click", function () {
    move(-1);
  });

  scroller.addEventListener("scroll", updateButtons);
  window.addEventListener("resize", updateButtons);

  updateButtons();

});

document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll("._posterDiv_8397x_21").forEach(div => {
        const workId = div.dataset.workid;
        const img = div.querySelector("img");

        if (workId) {
            img.src = "/images/movie" + workId + ".jpg";
        }
    });
});