document.addEventListener("DOMContentLoaded", function () {
    const eventForm = document.getElementById("eventForm");
    const createButton = document.getElementById("createButtonID");

    eventForm.addEventListener("submit", function (event) {
        event.preventDefault();  // ✅ Empêche l'envoi standard pour traiter les données en JS

        startLoading();
        createButton.disabled = true;

        // ✅ Récupération des valeurs du formulaire
        const eventData = {
            nom: document.getElementById("nomID").value.trim(),
            description: document.getElementById("descriptionID").value.trim(),
            date_debut: document.getElementById("dateStartID").value,
            date_fin: document.getElementById("dateEndID").value,
        };

        // ✅ Vérification des champs avant envoi
        if (!validateForm(eventData)) {
            finishLoading();
            createButton.disabled = false;
            return;
        }

        sendDataToBackend(eventData);
    });

    // ✅ Vérifier les champs avant envoi
    function validateForm(data) {
        let isValid = true;
        Object.keys(data).forEach(key => {
            if (data[key] === "" || (key === "utilisateur" && !data.utilisateur.id)) {
                isValid = false;
                alert(`⚠️ Le champ "${key}" est obligatoire.`);
            }
        });
        return isValid;
    }

	function sendDataToBackend(data) {
	    console.log("🔍 Données envoyées :", data); // ✅ Vérification avant envoi

	    fetch("/evenements/creerEvenement", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify(data),
	    })
	    .then(response => {
	        if (!response.ok) { 
	            throw new Error(`❌ Erreur HTTP : ${response.status}`); // Capture l'erreur si statut ≠ 200-299
	        }
	        return response.json(); // ✅ Convertit la réponse en JSON
	    })
	    .then(data => {
	        console.log("✅ Réponse du serveur :", data); // 🔥 Vérifie ce que renvoie le backend
	        finishLoading();
	        createButton.disabled = false;

	        if (data.nom) {
	            showConfirmationMessage(`🎉 L'événement "${data.nom}" a bien été créé !`);

	            // 🔹 Ajout de la redirection après validation
	            setTimeout(() => {
	                window.location.href = "/choixVote"; // Redirige vers la liste des événements
	            }, 2000); // ⏳ Attente de 2 secondes avant redirection
	            
	            eventForm.reset();
	        } else {
	            alert("❌ Erreur lors de la création de l'événement.");
	        }
	    })
	    .catch(error => {
	        console.error("⚠️ Échec de connexion au serveur :", error);
	        alert("⚠️ Impossible de se connecter au backend.");
	        finishLoading();
	        createButton.disabled = false;
	    });
	}

    // ✅ Affichage dynamique du message de confirmation
    function showConfirmationMessage(message) {
        const confirmationMessage = document.createElement("div");
        confirmationMessage.className = "confirmation";
        confirmationMessage.innerHTML = `<p>${message}</p>`;
        eventForm.parentElement.appendChild(confirmationMessage);
        setTimeout(() => confirmationMessage.remove(), 3000);
    }

    // ✅ Amélioration de la barre de progression
    function startLoading() {
        const progressBar = document.getElementById("progressBar");
        if (progressBar) {
            progressBar.style.width = "100%";
            progressBar.style.opacity = "1";
        }
    }

    function finishLoading() {
        setTimeout(() => {
            const progressBar = document.getElementById("progressBar");
            if (progressBar) {
                progressBar.style.width = "0%";
                progressBar.style.opacity = "0";
            }
        }, 1500); // ✅ Attente avant disparition pour éviter qu'elle soit trop rapide
    }
});