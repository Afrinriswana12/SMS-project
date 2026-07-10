async function loadStudents() {
  const searchInput = document.getElementById('searchInput');
  const deptFilter = document.getElementById('departmentFilter');
  let endpoint = apiUrl;
  const params = [];
  if (searchInput?.value.trim()) {
    params.push(`name=${encodeURIComponent(searchInput.value.trim())}`);
  }
  if (deptFilter?.value) {
    params.push(`department=${encodeURIComponent(deptFilter.value)}`);
  }
  if (params.length) {
    endpoint += `?${params.join('&')}`;
  }

  try {
    const response = await fetch(endpoint);
    if (!response.ok) throw new Error('Failed to fetch students');
    const students = await response.json();
    window.smsData = students;
    currentPage = 1;
    updateStats(students);
    renderStudents(students);
  } catch (error) {
    showToast('Unable to load students');
  }
}

function renderStudents(students) {
  const tbody = document.getElementById('studentsTableBody');
  tbody.innerHTML = '';
  const startIndex = (currentPage - 1) * pageSize;
  const pageStudents = students.slice(startIndex, startIndex + pageSize);

  pageStudents.forEach(student => {
    const statusClass = student.status?.toLowerCase() === 'active' ? 'status-active' : 'status-inactive';
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${student.id || ''}</td>
      <td>${student.name || ''}</td>
      <td>${student.registerNumber || ''}</td>
      <td>${student.department || ''}</td>
      <td><span class="status-badge ${statusClass}">${formatStatus(student.status)}</span></td>
      <td>
        <button class="action-btn" onclick="viewStudent(${student.id})" title="View"><i class="fa-regular fa-eye"></i></button>
        <button class="action-btn" onclick="editStudent(${student.id})" title="Edit"><i class="fa-regular fa-pen-to-square"></i></button>
        <button class="action-btn" onclick="deleteStudent(${student.id})" title="Delete"><i class="fa-regular fa-trash-can"></i></button>
      </td>
    `;
    tbody.appendChild(row);
  });
  renderPagination(students.length);
}

function renderPagination(totalItems) {
  const pagination = document.getElementById('pagination');
  pagination.innerHTML = '';
  const pages = Math.max(1, Math.ceil(totalItems / pageSize));
  for (let i = 1; i <= pages; i += 1) {
    const button = document.createElement('button');
    button.className = `page-btn${i === currentPage ? ' active' : ''}`;
    button.textContent = i;
    button.onclick = () => {
      currentPage = i;
      renderStudents(window.smsData);
    };
    pagination.appendChild(button);
  }
}

function updateStats(students) {
  document.getElementById('totalStudents').textContent = students.length;
  const departments = new Set(students.map((student) => student.department?.toUpperCase()).filter(Boolean));
  document.getElementById('departmentCount').textContent = departments.size;
  document.getElementById('activeStudents').textContent = students.filter((student) => student.status?.toLowerCase() === 'active').length;
  document.getElementById('recentStudents').textContent = students.slice(-5).length;
}

function viewStudent(id) {
  window.location.href = `studentDetails.html?id=${id}`;
}

function editStudent(id) {
  window.location.href = `edit.html?id=${id}`;
}

async function deleteStudent(id) {
  if (!confirm('Delete this student?')) return;
  try {
    const response = await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
    if (!response.ok) throw new Error('Delete failed');
    await loadStudents();
    showToast('Student deleted successfully');
  } catch (error) {
    showToast('Could not delete student');
  }
}

const pageSize = 8;
let currentPage = 1;

window.addEventListener('DOMContentLoaded', () => {
  document.getElementById('searchInput')?.addEventListener('input', loadStudents);
  document.getElementById('departmentFilter')?.addEventListener('change', loadStudents);
  loadStudents();
});
