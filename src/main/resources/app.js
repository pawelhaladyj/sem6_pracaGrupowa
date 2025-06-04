const API_URL = "http://localhost:8080/api";

const sections = {
    books: document.getElementById('books-section'),
    users: document.getElementById('users-section'),
    bookDetails: document.getElementById('book-details-section'),
    userDetails: document.getElementById('user-details-section'),
    login: document.getElementById('login-section'),
    register: document.getElementById('register-section'),
    loans: document.getElementById('loans-section'),
    reservations: document.getElementById('reservations-section'),
    reviews: document.getElementById('reviews-section'),
    newsletter: document.getElementById('newsletter-section'),
    reports: document.getElementById('reports-section'),
};
const logoutBtn = document.getElementById('logout');

function showSection(name) {
    Object.values(sections).forEach(s => s && (s.style.display = 'none'));
    sections[name] && (sections[name].style.display = '');
}

function getAuthHeaders() {
    const token = localStorage.getItem('token');
    return token ? { authToken: token } : {};
}

function updateAuth() {
    const loggedIn = !!localStorage.getItem('token');
    logoutBtn.style.display = loggedIn ? '' : 'none';
    document.getElementById('show-login').style.display = loggedIn ? 'none' : '';
    document.getElementById('show-register').style.display = loggedIn ? 'none' : '';
}

logoutBtn.onclick = () => {
    localStorage.removeItem('token');
    updateAuth();
    showSection('login');
};

// Logowanie
document.getElementById('login-form').onsubmit = async (e) => {
    e.preventDefault();
    const email = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    try {
        const res = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        if (res.ok) {
            const data = await res.json();
            localStorage.setItem('token', data.token);
            updateAuth();
            showSection('books');
            loadBooks();
        } else {
            document.getElementById('login-error').textContent = "Błędny login lub hasło!";
        }
    } catch {
        document.getElementById('login-error').textContent = "Błąd logowania!";
    }
};

// Rejestracja
document.getElementById('register-form').onsubmit = async (e) => {
    e.preventDefault();
    const firstName = document.getElementById('register-firstname').value;
    const lastName = document.getElementById('register-lastname').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;
    try {
        const res = await fetch(`${API_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ firstName, lastName, email, password })
        });
        if (res.ok) {
            showSection('login');
            document.getElementById('register-error').textContent = "Zarejestrowano, zaloguj się!";
        } else {
            document.getElementById('register-error').textContent = "Błąd rejestracji!";
        }
    } catch {
        document.getElementById('register-error').textContent = "Błąd rejestracji!";
    }
};

// Reset hasła
async function requestPasswordReset(email) {
    await fetch(`${API_URL}/auth/reset-password`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...getAuthHeaders() },
        body: JSON.stringify({ email })
    });
    alert('Sprawdź mail/logi serwera po link resetujący!');
}

async function confirmPasswordReset(token, newPassword) {
    await fetch(`${API_URL}/auth/reset-password/confirm`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...getAuthHeaders() },
        body: JSON.stringify({ token, newPassword })
    });
    alert('Hasło zmienione.');
}

// Książki
async function loadBooks(query = "") {
    const list = document.getElementById('books-list');
    list.innerHTML = "<li>Ładowanie...</li>";
    let url = `${API_URL}/books`;
    if (query) url += `?query=${encodeURIComponent(query)}`;
    try {
        const res = await fetch(url, { headers: getAuthHeaders() });
        const books = await res.json();
        list.innerHTML = "";
        books.forEach(book => {
            const li = document.createElement('li');
            li.textContent = `${book.title} (${book.author})`;
            li.onclick = () => showBookDetails(book.id);
            list.appendChild(li);
        });
    } catch {
        list.innerHTML = "<li>Błąd ładowania książek</li>";
    }
}
document.getElementById('search-btn').onclick = () => {
    loadBooks(document.getElementById('search').value);
};
async function showBookDetails(id) {
    showSection('bookDetails');
    const section = sections.bookDetails;
    section.innerHTML = "Ładowanie...";
    try {
        const res = await fetch(`${API_URL}/books/${id}`, { headers: getAuthHeaders() });
        const book = await res.json();
        section.innerHTML = `
          <h2>${book.title}</h2>
          <p><b>Autor:</b> ${book.author}</p>
          <p><b>Gatunek:</b> ${book.genre || ''}</p>
          <p><b>Rok:</b> ${book.publishedYear}</p>
          <p><b>Dostępnych egz.:</b> ${book.availableCopies}/${book.totalCopies}</p>
          <img src="${book.coverImageUrl || ''}" style="max-width:200px"/>
          <button id="back">Powrót</button>
          <div id="book-actions"></div>
        `;
        document.getElementById('back').onclick = () => showSection('books');
        renderBookActions(book);
        loadBookReviews(book.id);
    } catch {
        section.innerHTML = "Błąd ładowania szczegółów książki.";
    }
}

// Dodatkowe akcje na książce
function renderBookActions(book) {
    const actionsDiv = document.getElementById('book-actions');
    actionsDiv.innerHTML = `
        <button id="borrow-book">Wypożycz</button>
        <button id="reserve-book">Rezerwuj</button>
        <form id="review-form">
            <input id="review-rating" type="number" min="1" max="5" required placeholder="Ocena 1-5"/>
            <input id="review-text" type="text" required placeholder="Twoja opinia"/>
            <button type="submit">Dodaj recenzję</button>
        </form>
        <div id="reviews-section"></div>
    `;
    document.getElementById('borrow-book').onclick = () => borrowBook(book.id);
    document.getElementById('reserve-book').onclick = () => reserveBook(book.id);
    document.getElementById('review-form').onsubmit = (e) => {
        e.preventDefault();
        addReview(book.id);
    };
}

async function borrowBook(bookId) {
    const userId = prompt("Podaj swój ID użytkownika:");
    await fetch(`${API_URL}/loans`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...getAuthHeaders() },
        body: JSON.stringify({ bookId, userId })
    });
    alert('Wypożyczono!');
}

async function reserveBook(bookId) {
    const userId = prompt("Podaj swój ID użytkownika:");
    await fetch(`${API_URL}/reservations`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...getAuthHeaders() },
        body: JSON.stringify({ bookId, userId })
    });
    alert('Zarezerwowano!');
}

async function addReview(bookId) {
    const userId = prompt("Podaj swój ID użytkownika:");
    const rating = document.getElementById('review-rating').value;
    const reviewText = document.getElementById('review-text').value;
    await fetch(`${API_URL}/reviews/book/${bookId}/user/${userId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...getAuthHeaders() },
        body: JSON.stringify({ rating: Number(rating), reviewText })
    });
    alert('Recenzja dodana!');
    loadBookReviews(bookId);
}

async function loadBookReviews(bookId) {
    const section = sections.reviews;
    if (!section) return;
    section.innerHTML = "<b>Ładowanie opinii...</b>";
    try {
        const res = await fetch(`${API_URL}/reviews/book/${bookId}`, { headers: getAuthHeaders() });
        const reviews = await res.json();
        section.innerHTML = "<h4>Recenzje:</h4>";
        reviews.forEach(rev => {
            const div = document.createElement('div');
            div.innerHTML = `<b>${rev.userName}</b>: ${rev.rating}/5<br>${rev.reviewText} <hr>`;
            section.appendChild(div);
        });
    } catch {
        section.innerHTML = "<b>Błąd ładowania recenzji</b>";
    }
}

// Nawigacja
document.getElementById('show-books').onclick = () => { showSection('books'); loadBooks(); };
document.getElementById('show-users').onclick = () => { showSection('users'); loadUsers(); };
document.getElementById('show-loans').onclick = () => { showSection('loans'); loadLoans(); };
document.getElementById('show-reservations').onclick = () => { showSection('reservations'); loadReservations(); };
document.getElementById('show-newsletter').onclick = () => { showSection('newsletter'); loadNewsletter(); };
document.getElementById('show-reports').onclick = () => { showSection('reports'); loadStats(); };
document.getElementById('show-login').onclick = () => { showSection('login'); };
document.getElementById('show-register').onclick = () => { showSection('register'); };

// Inicjalizacja aplikacji
updateAuth();
showSection('books');
loadBooks();
