const form = document.getElementById('simulation-form');
const runsInput = document.getElementById('runs');
const submitButton = document.getElementById('submit-button');
const errorMessage = document.getElementById('error-message');
const resultsTable = document.getElementById('results-table');
const resultsBody = document.getElementById('results-body');

form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const runs = Number(runsInput.value);
    if (!Number.isInteger(runs) || runs <= 0) {
        showError('Please enter a positive whole number of runs.');
        return;
    }

    hideError();
    hideResults();
    setLoading(true);

    try {
        const response = await fetch('/api/simulations', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({runs})
        });

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        const results = await response.json();
        renderResults(results);
    } catch (error) {
        showError(`Failed to run simulations: ${error.message}`);
    } finally {
        setLoading(false);
    }
});

function renderResults(results) {
    resultsBody.innerHTML = '';
    for (const result of results) {
        const winPercentage = result.totalRuns === 0
            ? 0
            : (100 * result.carCount / result.totalRuns);

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${result.strategy}</td>
            <td>${result.carCount}</td>
            <td>${result.goatCount}</td>
            <td>${winPercentage.toFixed(2)}%</td>
        `;
        resultsBody.appendChild(row);
    }
    resultsTable.hidden = false;
}

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.hidden = false;
}

function hideError() {
    errorMessage.hidden = true;
    errorMessage.textContent = '';
}

function hideResults() {
    resultsTable.hidden = true;
    resultsBody.innerHTML = '';
}

function setLoading(isLoading) {
    submitButton.disabled = isLoading;
    submitButton.textContent = isLoading ? 'Running…' : 'Run Simulations';
}
