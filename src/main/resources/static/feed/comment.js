document.addEventListener("DOMContentLoaded", function () {

  const tabButtons = document.querySelectorAll("._tabs_1arqc_1 button");
  const commentList = document.querySelector("._ul_1szmi_1");
  console.log("tabs:", tabButtons.length);
  console.log("list:", commentList);
  
  if (!tabButtons.length || !commentList) return;

  tabButtons.forEach(button => {
    button.addEventListener("click", () => {

      // 선택 스타일 변경
      tabButtons.forEach(btn =>
        btn.classList.remove("_chipSelected_faef4_23")
      );
      button.classList.add("_chipSelected_faef4_23");

      // 필터값
      const filter = button.dataset.filter;

      sortComments(filter);
    });
  });

  function sortComments(filter) {

    const items = Array.from(commentList.querySelectorAll("li"));

    items.sort((a, b) => {

      if (filter === "latest") {
        return new Date(b.dataset.created) - new Date(a.dataset.created);
      }

      if (filter === "most_liked") {
        return Number(b.dataset.likes) - Number(a.dataset.likes);
      }

      return 0;
    });

    items.forEach(item => commentList.appendChild(item));
  }

});

window.addEventListener("load", function () {

  const tabButtons = document.querySelectorAll("._tabs_1arqc_1 button");
  const commentList = document.querySelector("._ul_1szmi_1");

  // 🔥 임시 데이터
  const mockData = {
    popular: [
      { user: "수진", content: "이 작품 미쳤다 🔥", likes: 120 },
      { user: "민수", content: "인생 영화임", likes: 95 }
    ],
    latest: [
      { user: "지은", content: "오늘 처음 봤는데 좋네", likes: 3 },
      { user: "태훈", content: "방금 보고 옴", likes: 1 }
    ]
  };

  tabButtons.forEach(button => {
    button.addEventListener("click", function () {

      // 버튼 스타일 변경
      tabButtons.forEach(btn =>
        btn.classList.remove("_chipSelected_faef4_23")
      );
      this.classList.add("_chipSelected_faef4_23");

      const type = this.dataset.filter;

      renderComments(mockData[type]);
    });
  });

  function renderComments(data) {

    commentList.innerHTML = "";

    data.forEach(comment => {

      const li = document.createElement("li");

      li.innerHTML = `
        <article class="_container_lke2u_1">
          <div><strong>${comment.user}</strong></div>
          <div style="margin-top:8px;">${comment.content}</div>
          <div style="margin-top:8px; color:gray;">좋아요 ${comment.likes}</div>
        </article>
      `;

      commentList.appendChild(li);
    });
  }

});