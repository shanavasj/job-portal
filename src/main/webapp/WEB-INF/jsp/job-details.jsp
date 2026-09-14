<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${job.title} - Job Portal</title>
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
            <h3 class="card-title">${job.title}</h3>
            <h6 class="card-subtitle mb-3 text-muted">${job.employer.companyName} &middot; ${job.location}</h6>

            <p class="card-text">${job.description}</p>

            <ul class="list-group list-group-flush mb-3">
                <li class="list-group-item"><strong>Salary:</strong> &#8377;${job.salary}</li>
                <li class="list-group-item"><strong>Posted on:</strong> ${job.postedDate}</li>
                <c:if test="${not empty job.category}">
                    <li class="list-group-item"><strong>Category:</strong> ${job.category.categoryName}</li>
                </c:if>
            </ul>

            <!--
                In a real login-enabled version, applicantId would come from the logged-in
                session, not a manual input. For this project's current scope, we accept
                it as a simple form field to demonstrate the apply-to-job flow end-to-end.
            -->
            <form action="/api/applications" method="post" onsubmit="return applyToJob(event)">
                <div class="mb-3">
                    <label class="form-label">Your Applicant ID</label>
                    <input type="number" id="applicantId" class="form-control" style="max-width:200px" required>
                </div>
                <button type="submit" class="btn btn-success">Apply Now</button>
            </form>
            <div id="applyResult" class="mt-3"></div>
        </div>
    </div>
    <a href="/" class="btn btn-link mt-3">&larr; Back to all jobs</a>
</div>

<script>
    // Calls the REST API (POST /api/applications) directly from the JSP page via fetch -
    // this is the "frontend talks to the Spring Boot REST backend" integration point.
    function applyToJob(event) {
        event.preventDefault();
        const applicantId = document.getElementById('applicantId').value;
        const jobId = ${job.id};

fetch(`/api/applications?jobId=\${jobId}&applicantId=\${applicantId}`, { method: 'POST' })
            .then(res => {
                if (!res.ok) return res.json().then(err => { throw err; });
                return res.json();
            })
            .then(data => {
                document.getElementById('applyResult').innerHTML =
                    '<div class="alert alert-success">Application submitted successfully!</div>';
            })
            .catch(err => {
                document.getElementById('applyResult').innerHTML =
                    '<div class="alert alert-danger">' + (err.message || 'Could not submit application.') + '</div>';
            });
        return false;
    }
</script>
</body>
</html>
