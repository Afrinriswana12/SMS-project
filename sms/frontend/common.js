const apiUrl = 'http://localhost:8083/api/students';

function showToast(message) {
  const toast = document.getElementById('toast');
  if (!toast) return;
  toast.textContent = message;
  toast.style.display = 'block';
  setTimeout(() => {
    toast.style.display = 'none';
  }, 2800);
}

function getQueryParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function formatStatus(value) {
  return value ? value : 'Inactive';
}
