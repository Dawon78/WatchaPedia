$(document).ready(function(){
    $('.book-slider').each(function() {
        $(this).slick({
            infinite: false,
            slidesToShow: 5,
            slidesToScroll: 5,
            arrows: true,
            // 💡 버튼 안에 화살표 기호만 남깁니다.
            prevArrow: '<button type="button" class="slick-prev"> </button>',
            nextArrow: '<button type="button" class="slick-next"> </button>',
            responsive: [
                { breakpoint: 1200, settings: { slidesToShow: 4, slidesToScroll: 4 } },
                { breakpoint: 900, settings: { slidesToShow: 3, slidesToScroll: 3 } }
            ]
        });
    });
});