//<!-- Script for sorting -->
let sortDirection = {};

function sortTable(columnIndex) {
  const table = document.getElementById("gameTable");
  const tbody = table.querySelector("tbody");
  const rows = Array.from(tbody.querySelectorAll("tr"));

  // Initialize sort order for this column if not set
  if (sortDirection[columnIndex] === undefined) {
      sortDirection[columnIndex] = true; // Ascending by default
  } else {
      sortDirection[columnIndex] = !sortDirection[columnIndex]; // Toggle sort order
  }

  // Determine sorting function
  rows.sort((rowA, rowB) => {
      let cellA = rowA.cells[columnIndex].textContent.trim();
      let cellB = rowB.cells[columnIndex].textContent.trim();

      // Convert numbers for proper sorting
      if (!isNaN(cellA) && !isNaN(cellB)) {
          return sortDirection[columnIndex] ? cellA - cellB : cellB - cellA;
      }

      // Default string sorting
      return sortDirection[columnIndex] ? cellA.localeCompare(cellB) : cellB.localeCompare(cellA);
  });

  // Rebuild table with sorted rows
  tbody.innerHTML = "";
  rows.forEach(row => tbody.appendChild(row));
}