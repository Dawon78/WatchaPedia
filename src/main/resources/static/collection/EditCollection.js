function removeWork(btn) {
            if(confirm("이 작품을 컬렉션에서 제외하시겠습니까?")) {
                btn.closest('.work-item').remove();
                updateCount();
            }
        }

        function updateCount() {
            const count = document.querySelectorAll('.work-item').length;
            document.querySelector('.work-count').innerText = count;
        }