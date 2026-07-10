async function submitNewStudent() {
  const student = {
    name: document.getElementById('name').value.trim(),
    registerNumber: parseInt(document.getElementById('regno').value.trim(), 10),
    department: document.getElementById('department').value,
    course: document.getElementById('course').value,
    year: document.getElementById('year').value,
    email: document.getElementById('email').value.trim(),
    phone: document.getElementById('phone').value.trim(),
    gender: document.querySelector('input[name="gender"]:checked')?.value,
    address: document.getElementById('address').value.trim(),
    status: document.getElementById('status').value,
  };

  if (!student.name || !student.registerNumber || !student.department || !student.course || !student.year || !student.email || !student.phone || !student.gender || !student.address) {
    showToast('Please complete all required fields');
    return;
  }

  try {
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Unable to save student');
    }
    showToast('Student added successfully');
    setTimeout(() => { window.location.href = 'students.html'; }, 1200);
  } catch (err) {
    showToast(err.message);
  }
}

window.addEventListener('DOMContentLoaded', () => {
  document.getElementById('saveButton').addEventListener('click', submitNewStudent);
  loadStudentSummary();
});

async function loadStudentSummary() {
  try {
    const response = await fetch(apiUrl);
    if (!response.ok) return;
    const students = await response.json();
    document.getElementById('totalStudents').textContent = students.length;
  } catch (err) {
    console.warn('Unable to load student summary', err);
  }
}

