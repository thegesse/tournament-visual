
async function displayBracket() {
    try {
        const response = await fetch(`/bracket`, {method: "GET"})
        if(!response.ok) throw  new Error("Aucune page trouver")

        const data = await response.json()
        renderAthletes(data)
    }catch(error) {
        console.error("Erreur Json" + error)
    }
}

async function runNextRound(roundName) {
    try {
        const response = await fetch(`/${roundName}`, { method: "POST" });

        if (!response.ok) {
            throw new Error(`Erreur lors du round: ${response.statusText}`);
        }

        const winners = await response.json();
        renderAthletes(winners);

        if (winners.length === 1) {
            alert("Le gagnant est : " + winners[0].name);
        }

        return winners;
    } catch (error) {
        console.error("Erreur:", error);
        alert("Impossible de traiter le round.");
    }
}

function renderAthletes(athletes, roundId = "round-1") {
    const container = document.querySelector(`#${roundId} .matches`);
    if (!container) return;

    container.innerHTML = "";

    athletes.forEach(athlete => {
        const div = document.createElement("div");
        div.className = "athlete-card";

        const score = athlete.athleticism !== undefined ? athlete.athleticism : athlete.Athleticism;

        div.innerHTML = `
            <strong>${athlete.name}</strong> 
            <span>Athlétisme: ${score}</span>
        `;
        container.appendChild(div);
    });
}

async function displayBracket() {
    try {
        const response = await fetch(`/bracket`, {method: "GET"});
        if(!response.ok) throw new Error("Erreur de chargement");
        const data = await response.json();

        // On force l'affichage dans le Round 1 au départ
        renderAthletes(data, "round-1");
    } catch(error) {
        console.error("Erreur:", error);
    }
}

async function runNextRound(roundName) {
    const response = await fetch(`/${roundName}`, { method: "POST" });
    if (!response.ok) throw new Error(`Erreur: ${response.statusText}`);
    return await response.json();
}

const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));

async function runFullTournament() {
    const btn = document.getElementById("tournament-btn");
    btn.disabled = true;

    try {
        await displayBracket();
        await sleep(1000);

        const r1Winners = await runNextRound('firstRound');
        renderAthletes(r1Winners, "round-2");
        await sleep(1000);

        const r2Winners = await runNextRound('secondRound');
        renderAthletes(r2Winners, "round-3");
        await sleep(1000);

        const r3Winners = await runNextRound('thirdRound');
        renderAthletes(r3Winners, "round-4");
        await sleep(1000);

        const champion = await runNextRound('fourthRound');
        if (champion.length > 0) {
            alert("Le champion est : " + champion[0].name);
        }
    } catch (error) {
        console.error("Erreur durant le tournoi:", error);
    } finally {
        btn.disabled = false;
    }
}

async function resetBracket() {
    const response = await fetch(`/reset`, { method: "POST" });
    const data = await response.json();

    document.querySelectorAll('.matches').forEach(m => m.innerHTML = "");

    renderAthletes(data, "round-1");
    document.getElementById("tournament-btn").disabled = false;
}