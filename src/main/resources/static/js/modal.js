// modal.js
window.addEventListener('load', () => { // load 시점이 DOMContentLoaded보다 안정적
    const loginBtn = document.getElementById('loginBtn');
    const modal = document.getElementById('loginModal');

    if (!loginBtn) console.error('loginBtn not found!');
    if (!modal) console.error('loginModal not found!');

    if (loginBtn && modal) {
        loginBtn.addEventListener('click', () => {
            modal.style.display = 'block';
        });
    }
});

window.closeLoginModal = function() {
    const modal = document.getElementById('loginModal');
    if (modal) modal.style.display = 'none';
}