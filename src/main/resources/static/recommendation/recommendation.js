document.addEventListener("DOMContentLoaded", function () {

  /* =========================
     0. 스크롤 등장 애니메이션
  ========================== */

  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add("_sunrise_82rb0_17");
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.1 });

  document.querySelectorAll("._component_82rb0_1")
    .forEach(el => observer.observe(el));


  /* =========================
     1. 상단 탭 활성화 + 바 이동 + 섹션 전환
  ========================== */

  const buttons = document.querySelectorAll("._button_10e2m_1");
  const bar = document.querySelector("._bar_1kgw0_6");

  // 🔥 영화 카드 섹션만 잡는다 (emptySection 제외)
  const movieSections = document.querySelectorAll(
    "section._row_163fv_36._component_82rb0_1"
  );

  const emptySection = document.getElementById("emptySection");

  if (buttons.length && bar) {

    function moveBar(btn) {
      const rect = btn.getBoundingClientRect();
      const parentRect = btn.closest("._container_1kgw0_1").getBoundingClientRect();

      bar.style.width = rect.width + "px";
      bar.style.left = (rect.left - parentRect.left) + "px";
      bar.style.opacity = "1";
    }

    function showByType(type) {

      if (type === "movie") {
        movieSections.forEach(sec => {
          if (!emptySection.contains(sec)) {
            sec.style.display = "block";
          }
        });
        if (emptySection) emptySection.style.display = "none";

      } else {
        movieSections.forEach(sec => {
          if (!emptySection.contains(sec)) {
            sec.style.display = "none";
          }
        });
        if (emptySection) emptySection.style.display = "block";
      }
    }

    buttons.forEach(btn => {

      btn.addEventListener("click", function () {

        buttons.forEach(b => b.classList.remove("_active_10e2m_9"));
        this.classList.add("_active_10e2m_9");

        moveBar(this);

        const type = this.dataset.type;
        showByType(type);

      });

    });

    // 🔥 최초 로딩 시 movie 기준으로 세팅
    const activeBtn = document.querySelector("._active_10e2m_9") || buttons[0];
    moveBar(activeBtn);
    showByType(activeBtn.dataset.type || "movie");
  }


  /* =========================
     2. 나의 구독 / 태그 버튼 선택
  ========================== */

  const tagButtons = document.querySelectorAll("._tagSetting_163fv_103");

  tagButtons.forEach(btn => {
    btn.addEventListener("click", function () {
      tagButtons.forEach(b => b.classList.remove("active"));
      this.classList.add("active");
    });
  });


  /* =========================
     3. 토글 ON/OFF
  ========================== */

  const toggleBtn = document.querySelector("._button_1882y_1");

  if (toggleBtn) {
    toggleBtn.addEventListener("click", function () {
      this.classList.toggle("active");
    });
  }


  /* =========================
     4. 가로 스크롤 드래그
  ========================== */

  const scrollContainers = document.querySelectorAll("._horizontalScroll_163fv_46");

  scrollContainers.forEach(container => {

    let isDown = false;
    let startX;
    let scrollLeft;

    container.addEventListener("mousedown", (e) => {
      isDown = true;
      startX = e.pageX - container.offsetLeft;
      scrollLeft = container.scrollLeft;
    });

    container.addEventListener("mouseleave", () => {
      isDown = false;
    });

    container.addEventListener("mouseup", () => {
      isDown = false;
    });

    container.addEventListener("mousemove", (e) => {
      if (!isDown) return;
      e.preventDefault();
      const x = e.pageX - container.offsetLeft;
      const walk = (x - startX) * 1.2;
      container.scrollLeft = scrollLeft - walk;
    });

  });


  /* =========================
     5. 좌우 화살표 버튼 스크롤
  ========================== */

  const scrollWrapper = document.querySelector("._listWrapper_mz3qo_13");
  const rightBtn = document.querySelector("._right_mz3qo_68");
  const leftBtn = document.querySelector("._left_mz3qo_72");

  if (scrollWrapper && rightBtn && leftBtn) {

    const scrollAmount = scrollWrapper.offsetWidth;

    function updateButtons() {
      if (scrollWrapper.scrollLeft <= 0) {
        leftBtn.classList.add("_disabled_mz3qo_64");
      } else {
        leftBtn.classList.remove("_disabled_mz3qo_64");
      }

      if (scrollWrapper.scrollLeft + scrollWrapper.clientWidth >= scrollWrapper.scrollWidth - 5) {
        rightBtn.classList.add("_disabled_mz3qo_64");
      } else {
        rightBtn.classList.remove("_disabled_mz3qo_64");
      }
    }

    rightBtn.addEventListener("click", function () {
      scrollWrapper.scrollBy({ left: scrollAmount, behavior: "smooth" });
    });

    leftBtn.addEventListener("click", function () {
      scrollWrapper.scrollBy({ left: -scrollAmount, behavior: "smooth" });
    });

    scrollWrapper.addEventListener("scroll", updateButtons);

    updateButtons();
  }

});