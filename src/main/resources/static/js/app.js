// EmployeeHub — app.js

document.addEventListener('DOMContentLoaded', () => {

  // Auto-dismiss flash messages after 4 seconds
  const alerts = document.querySelectorAll('.alert');
  alerts.forEach(alert => {
    setTimeout(() => {
      alert.style.transition = 'opacity .5s ease';
      alert.style.opacity = '0';
      setTimeout(() => alert.remove(), 500);
    }, 4000);
  });

  // Debounced live search on the list page
  const searchInput = document.getElementById('search');
  if (searchInput) {
    let debounceTimer;
    searchInput.addEventListener('input', () => {
      clearTimeout(debounceTimer);
      debounceTimer = setTimeout(() => {
        document.getElementById('filterForm').submit();
      }, 500);
    });
  }

  // Confirm delete dialogs are handled inline via onsubmit
});
