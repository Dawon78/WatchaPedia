document.addEventListener("DOMContentLoaded", function () {

    const container = document.getElementById("wordCloud");
    if (!container) return;

    const words = [
        { text: "게임", size: 17, x: 126, y: 105, bold: true },
        { text: "일본 배경", size: 17, x: 79, y: 76, bold: true },
        { text: "영상미", size: 17, x: 61, y: 108, bold: true },

        { text: "따뜻한", size: 15, x: 184, y: 79 },
        { text: "가족", size: 15, x: 194, y: 54 },
        { text: "감동", size: 15, x: 30, y: 40 },
        { text: "OST", size: 14, x: 150, y: 25 },
        { text: "성장", size: 14, x: 20, y: 130 },
        { text: "힐링", size: 14, x: 200, y: 140 }
    ];

    words.forEach(word => {

        const span = document.createElement("span");
        span.textContent = word.text;

        span.style.position = "absolute";
        span.style.fontSize = word.size + "px";
        span.style.lineHeight = word.size >= 17 ? "23px" : "19px";
        span.style.color = "#ff2f6e";
        span.style.transform = `translate(${word.x}px, ${word.y}px)`;
        span.style.fontFamily = "'Noto Sans KR', sans-serif";
        span.style.textAlign = "center";
        span.style.transformOrigin = "center bottom";
        span.style.whiteSpace = "nowrap";

        if (word.bold) {
            span.style.fontWeight = "bold";
        }

        container.appendChild(span);
    });

});