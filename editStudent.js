async function loadStudentForEdit() {
  const id = getQueryParam('id');
  if (!id) {
    showToast('No student selected');
    return;
  }
  try {
    const response = await fetch(`${apiUrl}/${id}`);
    if (!response.ok) throw new Error('Student not found');
    const student = await response.json();
    document.getElementById('studentId').textContent = `#${student.id}`;
    document.getElementById('name').value = student.name || '';
    document.getElementById('regno').value = student.registerNumber || '';
    document.getElementById('department').value = student.department || '';
    document.getElementById('course').value = student.course || '';
    document.getElementById('year').value = student.year || '';
    document.getElementById('email').value = student.email || '';
    document.getElementById('phone').value = student.phone || '';
    const genderValue = student.gender ? student.gender.toString().trim().toLowerCase() : '';
    const genderInput = document.querySelector(`input[name="gender"][value="${genderValue}"]`);
    if (genderInput) {
      genderInput.checked = true;
    }
    document.getElementById('address').value = student.address || '';
    document.getElementById('status').value = student.status || 'Active';
  } catch (error) {
    showToast(error.message);
  }
}

async function submitUpdatedStudent() {
  const id = getQueryParam('id');
  if (!id) {
    showToast('Missing student id');
    return;
  }
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
    const response = await fetch(`${apiUrl}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student),
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Unable to update student');
    }
    showToast('Student updated successfully');
    setTimeout(() => { window.location.href = 'students.html'; }, 1200);
  } catch (err) {
    showToast(err.message);
  }
}

window.addEventListener('DOMContentLoaded', () => {
  loadStudentForEdit();
  document.getElementById('updateButton').addEventListener('click', submitUpdatedStudent);
});
