// cart.js

// Function to calculate before discount price
function updateBeforeDiscount() {
  var totalPrice = parseFloat(document.getElementById('afterDiscount').innerText);
  var discount = parseFloat(document.getElementById('couponDiscount').innerText) / 100;
  var beforeDiscount = totalPrice / (1 - discount);  // Calculate the original price before the discount

  // Update the 'beforeDiscount' span
  document.getElementById('beforeDiscount').innerText = beforeDiscount.toFixed(2);
}

// Call the function when the page loads
window.onload = function() {
  updateBeforeDiscount();
};
