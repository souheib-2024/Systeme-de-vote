// Stocker les fichiers image et leurs métadonnées
let images = [];

document.addEventListener("DOMContentLoaded", function () {
    const nextButton = document.querySelector(".nextButton");

    mettreAJourLien();

    document.getElementById("inputValeur").addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            ajouterValeur();
        }
    });

    nextButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (!nextButton.classList.contains("disabled")) {
            envoyerDonneesAuBackend();
        } else {
            alert("Veuillez ajouter au moins deux choix avant de continuer !");
        }
    });
});

// Met à jour l'état du bouton "Next"
function mettreAJourLien() {
    const tableBody = document.getElementById("tableBody");
    const nextButton = document.querySelector(".nextButton");

    if (tableBody.children.length < 2) {
        nextButton.classList.add("disabled");
        nextButton.removeAttribute("href");
    } else {
        nextButton.classList.remove("disabled");
        nextButton.setAttribute("href", "/liste_evenement");
    }
}

// Ajoute une valeur (texte et image) au tableau
function ajouterValeur() {
    const inputText = document.getElementById("inputValeur");
    const inputImage = document.getElementById("inputImage");
    const valeur = inputText.value.trim();
    const imageFile = inputImage.files[0];

	        console.log("Image sélectionnée : ", imageFile);
	        console.log("Nom du fichier : ", imageFile.name);
	        console.log("Type MIME : ", imageFile.type);
	        console.log("Taille : ", imageFile.size);
	   

    if (!valeur) {
        alert("Veuillez entrer une valeur pour le vote.");
        return;
    }

    if (imageFile && !imageFile.type.startsWith("image/")) {
        alert("Le fichier sélectionné n'est pas une image valide.");
        return;
    }

    const tableBody = document.getElementById("tableBody");
    const newRow = tableBody.insertRow();

    const cellNumero = newRow.insertCell(0);
    const cellNom = newRow.insertCell(1);
    const cellImage = newRow.insertCell(2);
    const cellAction = newRow.insertCell(3);

    cellNumero.textContent = tableBody.children.length;
    cellNom.textContent = valeur;

    if (imageFile) {
        images.push({
            file: imageFile,
            name: imageFile.name,
            mimeType: imageFile.type,
            size: imageFile.size
        });

        const img = document.createElement("img");
        img.src = URL.createObjectURL(imageFile);
        img.alt = imageFile.name;
        img.style.maxWidth = "100px";
        img.style.height = "auto";
        cellImage.appendChild(img);
    } else {
        images.push(null);
        cellImage.textContent = "—";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.innerHTML = "❌";
    deleteBtn.classList.add("delete-btn");
    deleteBtn.onclick = function () {
        const rowIndex = newRow.rowIndex - 1;
        images.splice(rowIndex, 1);
        newRow.remove();
        mettreAJourNumerotation();
        mettreAJourLien();
    };

    cellAction.appendChild(deleteBtn);
    inputText.value = "";
    inputImage.value = "";

    mettreAJourNumerotation();
    mettreAJourLien();
    inputText.focus();
}

// Met à jour la numérotation des lignes du tableau
function mettreAJourNumerotation() {
    const tableBody = document.getElementById("tableBody");
    const rows = tableBody.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
        rows[i].cells[0].textContent = i + 1;
    }
}

// Envoie les données au backend avec les métadonnées des images
async function envoyerDonneesAuBackend() {
    const tableBody = document.getElementById("tableBody");
    const rows = tableBody.getElementsByTagName("tr");
    const formData = new FormData();

    for (let i = 0; i < rows.length; i++) {
        const numeroVote = rows[i].cells[0].textContent;
        const nomVote = rows[i].cells[1].textContent;
        const imageData = images[i];

        formData.append("numeroVote", numeroVote);
        formData.append("nomVote", nomVote);

        if (imageData) {
            formData.append("image", imageData.file, imageData.name);
            formData.append(`imageName-${numeroVote}`, imageData.name);
            formData.append(`imageMimeType-${numeroVote}`, imageData.mimeType);
            formData.append(`imageSize-${numeroVote}`, imageData.size);
        } else {
            formData.append("image", null);
        }
    }

    try {
        const response = await fetch("/votes/save", {
            method: "POST",
            body: formData
        });

        if (response.ok) {
            alert("Votes sauvegardés avec succès !");
			window.location.href = "/liste_evenement"; // Redirection côté client
        } else {
            alert("Erreur lors de l'enregistrement des votes.");
        }
    } catch (error) {
        console.error("Erreur réseau :", error);
        alert("Erreur réseau, vérifiez votre connexion.");
    }
}