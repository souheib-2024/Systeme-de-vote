document.addEventListener("DOMContentLoaded", function () {
    // ✅ Fonction qui affiche/masque le menu déroulant
    function toggleDropdown() {
        const dropdownMenu = document.getElementById("dropdownMenu");
        dropdownMenu.classList.toggle("show");
    }

    const adminIcon = document.querySelector(".admin-icon");
    const dropdownMenu = document.querySelector(".dropdown-menu");
    const logoutButton = document.querySelector(".logout-button");  // ✅ Bouton de déconnexion

    if (adminIcon && dropdownMenu) {
        adminIcon.addEventListener("click", function (e) {
            e.stopPropagation();
            toggleDropdown();
        });

        document.addEventListener("click", function (e) {
            if (!adminIcon.contains(e.target) && !dropdownMenu.contains(e.target)) {
                dropdownMenu.classList.remove("show");
            }
        });
    }

    // ✅ Gestion de la déconnexion
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            fetch("/deconnexion", { method: "POST" })
            .then(response => {
                if (response.ok) {
                    window.location.href = "/login";  // ✅ Redirection après déconnexion
                }
            })
            .catch(error => console.error("Erreur lors de la déconnexion :", error));
        });
    }
});