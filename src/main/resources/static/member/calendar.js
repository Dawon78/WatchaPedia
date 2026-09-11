function toggleSearchModal(show) {
    const layer = document.getElementById('uModSearchLayer');
    if (layer) layer.style.display = show ? 'flex' : 'none';
    document.body.style.overflow = show ? 'hidden' : 'auto';
}

