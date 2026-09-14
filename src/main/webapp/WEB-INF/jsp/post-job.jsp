<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Post a Job - Job Portal</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="/">Job Portal</a>
    </div>
</nav>

<div class="container">
    <div class="card shadow-sm">
        <div class="card-body">
            <h3 class="card-title mb-4">Post a New Job</h3>

            <!--
                Note: employerId is entered manually here for demo simplicity, since there's
                no login/session system in this project's scope. Register an employer first
                via POST /api/employers to get a valid ID.
            -->
            <form id="jobForm">
                <div class="mb-3">
                    <label class="form-label">Employer ID</label>
                    <input type="number" id="employerId" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Job Title</label>
                    <input type="text" id="title" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Description</label>
                    <textarea id="description" class="form-control" rows="4" required></textarea>
                </div>
                <div class="mb-3">
                    <label class="form-label">Location</label>
                    <input type="text" id="location" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Salary (LPA)</label>
                    <input type="number" id="salary" class="form-control" step="0.1">
                </div>
                <button type="submit" class="btn btn-primary">Post Job</button>
            </form>
            <div id="postResult" class="mt-3"></div>
        </div>
    </div>
    <a href="/" class="btn btn-link mt-3">&larr; Back to all jobs</a>
</div>

<script>
    document.getElementById('jobForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const payload = {
            title: document.getElementById('title').value,
            description: document.getElementById('description').value,
            location: document.getElementById('location').value,
            salary: parseFloat(document.getElementById('salary').value) || null,
            employer: { id: parseInt(document.getElementById('employerId').value) }
        };

        fetch('/api/jobs', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
            .then(res => {
                if (!res.ok) return res.json().then(err => { throw err; });
                return res.json();
            })
            .then(data => {
                document.getElementById('postResult').innerHTML =
                    '<div class="alert alert-success">Job posted successfully! ' +
                    '<a href="/jobs/' + data.id + '">View it</a></div>';
                document.getElementById('jobForm').reset();
            })
            .catch(err => {
                document.getElementById('postResult').innerHTML =
                    '<div class="alert alert-danger">' + (err.message || 'Could not post job.') + '</div>';
            });
    });
</script>
</body>
</html>
