var addDialogModal = document.getElementById("addDialogModal");

// Get the button that opens the modal
var btn = document.getElementById("addButton");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
  addDialogModal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  addDialogModal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == addDialogModal) {
    addDialogModal.style.display = "none";
  }
}