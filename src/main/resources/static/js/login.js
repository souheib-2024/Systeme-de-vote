document.addEventListener("DOMContentLoaded", function () {
    const progressBar = document.getElementById("progressBar");
    const loginButton = document.getElementById("loginButton");
    const prenom = document.getElementById("prenom");
    const password = document.getElementById("password");
    const jsErrorMessage = document.getElementById("jsErrorMessage");
    const togglePassword = document.getElementById("togglePassword");

    // 🔁 Toggle visibilité du mot de passe
    togglePassword.addEventListener("click", function () {
        password.type = (password.type === "password") ? "text" : "password";
        togglePassword.classList.toggle("bx-toggle-right");
        togglePassword.classList.toggle("bx-toggle-left");
    });

    // 🚀 Animation de la barre de progression
    function startLoading() {
        progressBar.style.width = "0%";
        progressBar.style.opacity = "1";
        let width = 0;

        function animateProgress() {
            if (width < 85) {
                width += Math.random() * 10;
                progressBar.style.width = width + "%";
                setTimeout(animateProgress, 150);
            }
        }
        animateProgress();
    }

    // 🛑 Gestion des erreurs pour les champs vides
    function showError(message, input) {
        jsErrorMessage.textContent = message;
        jsErrorMessage.classList.add("active");
        input.classList.add("input-error");
        loginButton.classList.remove("loading");
		
		// 🔄 Réinitialisation des champs après l'affichage du message d'erreur
		   document.getElementById("prenom").value = "";
		   document.getElementById("password").value = "";

    }

    // ✅ Suppression des erreurs quand l’utilisateur commence à taper
    function clearError(input) {
        jsErrorMessage.classList.remove("active");
        input.classList.remove("input-error");
    }

    prenom.addEventListener("input", () => clearError(prenom));
    password.addEventListener("input", () => clearError(password));

    // 📤 Envoi de l'authentification
    loginButton.addEventListener("click", function (e) {
        e.preventDefault();
        loginButton.classList.add("loading");
        startLoading();

        // 🛑 Vérification des champs vides
        if (prenom.value.trim() === "" && password.value.trim() === "") {
            showError("Les champs prénom et mot de passe sont obligatoires.", prenom);
            showError("Les champs prénom et mot de passe sont obligatoires.", password);
            return;
        } else if (prenom.value.trim() === "") {
            showError("Le champ prénom est obligatoire.", prenom);
            return;
        } else if (password.value.trim() === "") {
            showError("Le champ mot de passe est obligatoire.", password);
            return;
        }

        // 🔐 Envoi sécurisé des données sans attendre une réponse JSON
        fetch("/utilisateurs/authentifier", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `prenom=${encodeURIComponent(prenom.value)}&motDePasse=${encodeURIComponent(password.value)}`
        })
        .then(response => {
            if (response.ok) {  
                window.location.href = "/accueil"; // ✅ Redirection immédiate si succès
            } else {
				const errorType = response.headers.get("X-Error-Type"); // 🔥 Récupérer l'erreur exacte
				       showError(errorType || "Erreur d'authentification", password);
            }
        })
        .catch(error => {
            console.error("Erreur:", error);
            showError("Une erreur est survenue, veuillez réessayer.", password);
        });
    });
});