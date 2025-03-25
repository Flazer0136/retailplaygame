//<!-- Script for filtering -->
document.addEventListener("DOMContentLoaded", function () {
  populateFilters();
});

function populateFilters() {
  const genres = new Set(["All"]);
  const consoles = new Set(["All"]);

  document.querySelectorAll("#gameTable tbody tr").forEach(row => {
      genres.add(row.querySelector(".genre").textContent.trim());
      consoles.add(row.querySelector(".console").textContent.trim());
  });

  const genreFilter = document.getElementById("genreFilter");
  genres.forEach(genre => {
      let option = document.createElement("option");
      option.value = genre;
      option.textContent = genre;
      genreFilter.appendChild(option);
  });

  const consoleFilter = document.getElementById("consoleFilter");
  consoles.forEach(console => {
      let option = document.createElement("option");
      option.value = console;
      option.textContent = console;
      consoleFilter.appendChild(option);
  });
}

function filterTable() {
        const selectedGenre = document.getElementById("genreFilter").value;
        const selectedConsole = document.getElementById("consoleFilter").value;
        const searchText = document.getElementById("searchInput").value.toLowerCase();

        document.querySelectorAll("#gameTable tbody tr").forEach(row => {
            const genre = row.querySelector(".genre").textContent.trim();
            const console = row.querySelector(".console").textContent.trim();
            const productName = row.querySelector(".product-name").textContent.toLowerCase();
            const productDesc = row.querySelector(".product-desc").textContent.toLowerCase();

            const genreMatch = selectedGenre === "All" || genre === selectedGenre;
            const consoleMatch = selectedConsole === "All" || console === selectedConsole;
            const searchMatch = productName.includes(searchText) || productDesc.includes(searchText);

            row.style.display = (genreMatch && consoleMatch && searchMatch) ? "" : "none";
        });
    }