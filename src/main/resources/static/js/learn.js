(function () {

    function readCardsFromDom() {
        const nodes = document.querySelectorAll('#cardsData .card-data');
        return Array.from(nodes).map(n => ({
            id: Number(n.dataset.id),
            front: n.dataset.front || '',
            back: n.dataset.back || '',
            level: (n.dataset.level || '').trim(),
            example: n.dataset.example || ''
        }));
    }

    const cards = readCardsFromDom();
    if (!cards.length) return; // brak danych — nic nie rób


    const cardEl = document.getElementById('card');
    const frontEl = document.getElementById('face-front');
    const backEl  = document.getElementById('face-back');
    const counterEl = document.getElementById('counter');
    const exampleEl = document.getElementById('example-sentence');

    const flipBtn = document.getElementById('flipBtn');
    const nextBtn = document.getElementById('nextBtn');
    const prevBtn = document.getElementById('prevBtn');
    const restartBtn = document.getElementById('restartBtn');
    const shuffleToggle = document.getElementById('shuffleToggle');


    const levelBadgeEl = document.getElementById('levelBadge');


    let order = cards.map((_, i) => i);
    let idx = 0;
    let flipped = false;


    const levelColorMap = {
        BEGINNER: 'bg-success',
        INTERMEDIATE: 'bg-warning',
        ADVANCED: 'bg-danger'
    };

    function setBadge(levelRaw) {
        const level = (levelRaw || '').trim();
        const colorClass = levelColorMap[level] || 'bg-secondary';
        if (levelBadgeEl) {
            levelBadgeEl.textContent = level || '';
            levelBadgeEl.className = 'level-badge text-white ' + colorClass;
        }
    }


    function render() {
        const c = cards[order[idx]];
        if (!c) return;

        frontEl.textContent = c.front;
        backEl.textContent  = c.back;
        exampleEl.textContent = c.example;


        setBadge(c.level);

        counterEl.textContent = `Flashcard ${idx + 1} / ${order.length}`;
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

    render();
})();