(function () {
    // Zbierz dane z ukrytego kontenera .card-data
    function readCardsFromDom() {
        const nodes = document.querySelectorAll('#cardsData .card-data');
        return Array.from(nodes).map(n => ({
            id: Number(n.dataset.id),
            front: n.dataset.front || '',
            back: n.dataset.back || ''
        }));
    }

    const cards = readCardsFromDom();

    // Jeśli brak fiszek, przerwij (np. sekcja warning jest już w HTML)
    if (!cards.length) return;

    // DOM referencje
    const cardEl = document.getElementById('card');
    const frontEl = document.getElementById('face-front');
    const backEl = document.getElementById('face-back');
    const counterEl = document.getElementById('counter');

    const flipBtn = document.getElementById('flipBtn');
    const nextBtn = document.getElementById('nextBtn');
    const prevBtn = document.getElementById('prevBtn');
    const restartBtn = document.getElementById('restartBtn');
    const shuffleToggle = document.getElementById('shuffleToggle');

    // Stan
    let order = cards.map((_, i) => i);
    let idx = 0;
    let flipped = false;

    // Funkcje pomocnicze
    function render() {
        const c = cards[order[idx]];
        if (!c) return;

        frontEl.textContent = c.front;
        backEl.textContent = c.back;

        counterEl.textContent = `Fiszka ${idx + 1} / ${order.length}`;
        cardEl.classList.toggle('is-flipped', flipped);
    }

    function flip() {
        flipped = !flipped;
        cardEl.classList.toggle('is-flipped', flipped);
    }

    function next() {
        idx = (idx + 1) % order.length;
        flipped = false;
        render();
    }

    function prev() {
        idx = (idx - 1 + order.length) % order.length;
        flipped = false;
        render();
    }

    function shuffleArray(a) {
        for (let i = a.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [a[i], a[j]] = [a[j], a[i]];
        }
    }

    function restart() {
        idx = 0;
        flipped = false;
        render();
    }

    // Zdarzenia
    flipBtn?.addEventListener('click', flip);
    nextBtn?.addEventListener('click', next);
    prevBtn?.addEventListener('click', prev);
    restartBtn?.addEventListener('click', restart);

    shuffleToggle?.addEventListener('change', (e) => {
        order = cards.map((_, i) => i);
        if (e.target.checked) shuffleArray(order);
        restart();
    });

    window.addEventListener('keydown', (e) => {
        if (e.code === 'Space' || e.code === 'Enter') {
            e.preventDefault();
            flip();
        } else if (e.key === 'ArrowRight') {
            next();
        } else if (e.key === 'ArrowLeft') {
            prev();
        }
    });

    // Start
    render();
})();
